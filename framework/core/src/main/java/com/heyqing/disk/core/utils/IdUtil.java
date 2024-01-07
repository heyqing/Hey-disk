package com.heyqing.disk.core.utils;


import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.ArrayUtil;
import com.google.common.base.Splitter;
import com.heyqing.disk.core.constants.HeyDiskConstants;
import com.heyqing.disk.core.exception.HeyDiskBusinessException;
import io.jsonwebtoken.lang.Collections;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * ClassName:IdUtil
 * Package:com.heyqing.disk.core.utils
 * Description:
 *          雪花算法id生成器
 * @Date:2024/1/6
 * @Author:Heyqing
 */

public class IdUtil {

    /**
     * 工作id也就是机器id
     */
    private static long workerId;
    /**
     * 数据中心id
     */
    private static long dataCenterId;
    /**
     * 序列号
     */
    private static long sequence;
    /**
     * 初始时间戳
     */
    private static long startTimestamp = 1288834974567l;
    /**
     * 工作id长度为5位
     */
    private static long workerIdBits = 5l;
    /**
     * 数据中心id长度为5位
     */
    private static long dataCenterIdBits = 5l;
    /**
     * 工作id最大值
     */
    private static long maxWorkerId = -1l ^ (-1l << workerIdBits);
    /**
     * 数据中心id最大值
     */
    private static long maxDataCenterId = -1l ^ (-1 << dataCenterIdBits);
    /**
     * 序列号长度
     */
    private static long sequenceBits = 12l           ;
    /**
     * 序列号最大值
     */
    private static long sequenceMask = -1l ^ (-1l << sequenceBits);
    /**
     * 工作id需要左移的位数，12位
     */
    private static long workerIdShift = sequenceBits;
    /**
     * 数据中心id需要左移的位数，12+5=17位
     */
    private static long dataCenterIdShift = sequenceBits + workerIdBits;
    /**
     * 时间戳需要左移的位数，12+5+5=22位
     */
    private static long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;
    /**
     * 上次时间戳，初始值为负数
     */
    private static long lastTimestamp = -1l;

    static {
        workerId = getMachineNum() & maxWorkerId;
        dataCenterId = getMachineNum() & maxDataCenterId;
        sequence = 0l;
    }

    /**
     * 获取机器编号
     * @return
     */
    private static long getMachineNum(){
        long machinePiece;
        StringBuilder  sb = new StringBuilder();
        Enumeration<NetworkInterface> e = null;
        try {
            e = NetworkInterface.getNetworkInterfaces();
        }catch (SocketException e1){
            e1.printStackTrace();
        }
        while (e.hasMoreElements()){
            NetworkInterface ni = e.nextElement();
            sb.append(ni.toString());
        }
        machinePiece = sb.toString().hashCode();
        return machinePiece;
    }

    /**
     * 获取时间戳，并于上次时间戳比较
     * @param lastTimestamp
     * @return
     */
    private static long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp){
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 获取系统时间戳
     * @return
     */
    private static long timeGen(){
        return System.currentTimeMillis();
    }

    /**
     * 生成id
     * @return
     */
    public synchronized static Long get(){
        long timestamp = timeGen();
        //获取当前时间戳 如果小于上次时间戳，则表示时间戳获取出现异常
        if (timestamp < lastTimestamp){
            System.err.printf("clock is moving backwards. Rejecting requests until %d.",lastTimestamp);
            throw new RuntimeException(String.format("Clock moved backwards. Refusing to generate id for %d milliseconds",lastTimestamp-timestamp));
        }
        //获取当前时间戳 如果等于上次时间戳 说明还处于同一毫秒内
        //则在序列号加1/否则序列号赋值为0
        //从0开始   0-4095
        if (lastTimestamp == timestamp){
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0){
                timestamp = tilNextMillis(lastTimestamp);
            }
        }else {
            sequence = 0;
        }
        //将上次时间戳刷新
        lastTimestamp = timestamp;
        /**
         * 返回结果
         * （timestamp - startTimestamp）<< timestampLeftShift  时间戳-初始时间戳，再左移相应位数
         * dataCenterId << dataCenterIdShift 数据中心id左移相应位数
         * workerId << workerIdShift 工作id左移相应位数
         * | 按位或 x | y x，y都为0才为0
         * 因为个部分只有相应位上的值有意义，其他位上都是0 ，所以将各部分的值进行| 运算 就能够得到拼接好的id
         */
        return  ((timestamp - startTimestamp) << timestampLeftShift) |
                (dataCenterId << dataCenterIdShift) |
                (workerId << workerIdShift) |
                sequence;
    }

    /**
     * 加密id
     * @param id
     * @return
     */
    public static String encrypt(Long id) {
        if (Objects.nonNull(id)){
            ByteBuffer byteBuffer = ByteBuffer.allocate(8);
            byteBuffer.putLong(0,id);
            byte[] content = byteBuffer.array();
            byte[] encrypt = AES128Util.aesEncrypt(content);
            return Base64.encode(encrypt);
        }
        return StringUtils.EMPTY;
    }

    /**
     * 解密id
     * @param decryptId
     * @return
     */
    public static Long decrypt(String decryptId){
        if (StringUtils.isNotBlank(decryptId)){
            byte[] encrypt = Base64.decode(decryptId);
            byte[] content  = AES128Util.aesDecode(encrypt);
            if(ArrayUtil.isNotEmpty(content)){
                ByteBuffer byteBuffer = ByteBuffer.wrap(content);
                return byteBuffer.getLong();
            }
            throw new HeyDiskBusinessException("AES128Util.aesDecode fail");
        }
        throw new HeyDiskBusinessException("the decryptId can not be empty");
    }

    public static List<Long> decryptIdList(String decryptIdStr){
        if (StringUtils.isBlank(decryptIdStr)){
            return Lists.newArrayList();
        }
        List<String > decryptIdList = Splitter.on(HeyDiskConstants.COMMON_SEPARATOR).splitToList(decryptIdStr);
        if (Collections.isEmpty(decryptIdList)){
            return Lists.newArrayList();
        }
        List<Long> result = decryptIdList.stream().map(IdUtil::decrypt).collect(Collectors.toList());
        return result;
    }









}
