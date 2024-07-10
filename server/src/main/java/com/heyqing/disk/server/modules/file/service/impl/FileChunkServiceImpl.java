package com.heyqing.disk.server.modules.file.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heyqing.disk.core.exception.HeyDiskBusinessException;
import com.heyqing.disk.core.utils.IdUtil;
import com.heyqing.disk.server.common.config.DiskServerConfig;
import com.heyqing.disk.server.modules.file.context.FileChunkSaveContext;
import com.heyqing.disk.server.modules.file.converter.FileConverter;
import com.heyqing.disk.server.modules.file.entity.HeyDiskFileChunk;
import com.heyqing.disk.server.modules.file.enums.MergeFlagEnum;
import com.heyqing.disk.server.modules.file.service.IFileChunkService;
import com.heyqing.disk.server.modules.file.mapper.HeyDiskFileChunkMapper;
import com.heyqing.disk.storage.engine.core.StorageEngine;
import com.heyqing.disk.storage.engine.core.context.StoreFileChunkContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

/**
 *
 */
@Service
@RequiredArgsConstructor
public class FileChunkServiceImpl extends ServiceImpl<HeyDiskFileChunkMapper, HeyDiskFileChunk> implements IFileChunkService {

    private final DiskServerConfig diskServerConfig;
    private final FileConverter fileConverter;
    private final StorageEngine storageEngine;

    /**
     * 文件分片保存
     * <p>
     * 1、保存文件分片和记录
     * 2、判断文件分类是否全部上传完成
     *
     * @param context
     */
    @Override
    public synchronized void saveChunkFile(FileChunkSaveContext context) {
        doSaveChunkFile(context);
        doJudgeMergeFile(context);
    }


    /********************************************************************private*******************************************************/

    /**
     * 判断是否所有的分片均没上传完成
     *
     * @param context
     */
    private void doJudgeMergeFile(FileChunkSaveContext context) {
        QueryWrapper queryWrapper = Wrappers.query();
        queryWrapper.eq("identifier",context.getIdentifier());
        queryWrapper.eq("create_user",context.getUserId());
        int count = count(queryWrapper);
        if (count == context.getTotalChunks().intValue()){
            context.setMergeFlagEnum(MergeFlagEnum.READY);
        }
    }

    /**
     * 执行文件分片上传保存的操作
     * <p>
     * 1、委托文件存储引擎存储文件分片
     * 2、保存文件分片记录
     *
     * @param context
     */
    private void doSaveChunkFile(FileChunkSaveContext context) {
        doStoreFileChunk(context);
        doSaveRecord(context);
    }

    /**
     * 保存文件分片记录
     *
     * @param context
     */
    private void doSaveRecord(FileChunkSaveContext context) {
        HeyDiskFileChunk record = new HeyDiskFileChunk();
        record.setId(IdUtil.get());
        record.setIdentifier(context.getIdentifier());
        record.setRealPath(context.getRealPath());
        record.setChunkNumber(context.getChunkNumber());
        record.setExpirationTime(DateUtil.offsetDay(new Date(),diskServerConfig.getChunkFileExpirationDays()));
        record.setCreateUser(context.getUserId());
        record.setCreateTime(new Date());
        if (!save(record)){
            throw new HeyDiskBusinessException("文件分片上传失败");
        }
    }

    /**
     * 委托文件存储引擎保存文件分片
     *
     * @param context
     */
    private void doStoreFileChunk(FileChunkSaveContext context) {
        try {
            StoreFileChunkContext storeFileChunkContext = fileConverter.fileChunkSaveContext2StoreFileChunkContext(context);
            storeFileChunkContext.setInputStream(context.getFile().getInputStream());
            storageEngine.storeChunk(storeFileChunkContext);
            context.setRealPath(storeFileChunkContext.getRealPath());
        }catch (IOException e){
            e.printStackTrace();
            throw new HeyDiskBusinessException("文件分片上传失败");
        }
    }

}




