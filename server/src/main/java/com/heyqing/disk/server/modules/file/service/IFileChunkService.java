package com.heyqing.disk.server.modules.file.service;

import com.heyqing.disk.server.modules.file.context.FileChunkSaveContext;
import com.heyqing.disk.server.modules.file.entity.HeyDiskFileChunk;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface IFileChunkService extends IService<HeyDiskFileChunk> {

    /**
     * 文件分片保存服务
     *
     * @param fileChunkSaveContext
     */
    void saveChunkFile(FileChunkSaveContext fileChunkSaveContext);
}
