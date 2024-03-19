package com.heyqing.disk.core.utils;

import cn.hutool.core.date.DateUtil;
import com.heyqing.disk.core.constants.HeyDiskConstants;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.List;
import java.util.Objects;

/**
 * ClassName:FileUtil
 * Package:com.heyqing.disk.core.utils
 * Description:
 * 文件相关工具类
 *
 * @Date:2024/2/28
 * @Author:Heyqing
 */
public class FileUtil {
    /**
     * 获取文件后缀
     *
     * @param filename
     * @return
     */
    public static String getFileSuffix(String filename) {
        if (StringUtils.isBlank(filename) || filename.indexOf(HeyDiskConstants.POINT_STR) == HeyDiskConstants.MINUS_ONE_INT) {
            return StringUtils.EMPTY;
        }
        return filename.substring(filename.lastIndexOf(HeyDiskConstants.POINT_STR));
    }

    /**
     * 通过文件大小转化文件大小的展示名称
     *
     * @param totalSize
     * @return
     */
    public static String byteCountToDisplaySize(Long totalSize) {
        if (Objects.isNull(totalSize)) {
            return HeyDiskConstants.EMPTY_STR;
        }
        return FileUtils.byteCountToDisplaySize(totalSize);
    }

    /**
     * 批量删除物理文件
     *
     * @param realFilePathList
     * @throws IOException
     */
    public static void deleteFiles(List<String> realFilePathList) throws IOException {
        if (CollectionUtils.isEmpty(realFilePathList)) {
            return;
        }
        for (String realFilePath : realFilePathList) {
            FileUtils.forceDelete(new File(realFilePath));
        }
    }

    /**
     * 生成文件物理存储路径
     * <p>
     * 生成规则：基础路径 + 年 + 月 + 日 + 随机文件名称
     *
     * @param basePath
     * @param filename
     * @return
     */
    public static String generateStoreFileRealPath(String basePath, String filename) {
        return new StringBuilder(basePath)
                .append(File.separator)
                .append(DateUtil.thisYear())
                .append(File.separator)
                .append(DateUtil.thisMonth() + 1)
                .append(File.separator)
                .append(DateUtil.thisDayOfMonth())
                .append(File.separator)
                .append(UUIDUtil.getUUID())
                .append(getFileSuffix(filename))
                .toString();
    }

    /**
     * 将文件的输入流写入文件当中
     * <p>
     * 使用底层sendfile零拷贝技术来提高传输效率
     *
     * @param inputStream
     * @param targetFile
     * @param totalSize
     */
    public static void writeStream2File(InputStream inputStream, File targetFile, Long totalSize) throws IOException {
        createFile(targetFile);
        RandomAccessFile randomAccessFile = new RandomAccessFile(targetFile, "rw");
        FileChannel outputChannel = randomAccessFile.getChannel();
        ReadableByteChannel inputChannel = Channels.newChannel(inputStream);
        outputChannel.transferFrom(inputChannel, 0L, totalSize);
        inputChannel.close();
        outputChannel.close();
        randomAccessFile.close();
        inputStream.close();
    }

    /**
     * 创建文件
     * 包含父文件一起去创建
     *
     * @param targetFile
     */
    public static void createFile(File targetFile) throws IOException {
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }
        targetFile.createNewFile();
    }

    /**
     * 生成默认文件存储路径
     * <p>
     * 生成规则：当前登录用户的文件目录 + heydiak
     *
     * @return
     */
    public static String generateDefaultStoreFileRealPath() {
        return new  StringBuffer(System.getProperty("user.home"))
                .append(File.separator)
                .append("heydisk")
                .toString();
    }
}
