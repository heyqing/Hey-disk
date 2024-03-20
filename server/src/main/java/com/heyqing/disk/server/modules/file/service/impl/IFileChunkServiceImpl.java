package com.heyqing.disk.server.modules.file.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heyqing.disk.server.modules.file.context.FileChunkSaveContext;
import com.heyqing.disk.server.modules.file.entity.HeyDiskFileChunk;
import com.heyqing.disk.server.modules.file.service.IFileChunkService;
import com.heyqing.disk.server.modules.file.mapper.HeyDiskFileChunkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class IFileChunkServiceImpl extends ServiceImpl<HeyDiskFileChunkMapper, HeyDiskFileChunk> implements IFileChunkService {


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
    }

    /**
     * 委托文件存储引擎保存文件分片
     *
     * @param context
     */
    private void doStoreFileChunk(FileChunkSaveContext context) {

    }

}




