package com.heyqing.disk.server.modules.file.service;

import com.heyqing.disk.server.modules.file.context.*;
import com.heyqing.disk.server.modules.file.entity.HeyDiskFile;
import com.heyqing.disk.server.modules.file.entity.HeyDiskUserFile;
import com.baomidou.mybatisplus.extension.service.IService;
import com.heyqing.disk.server.modules.file.vo.FileChunkUploadVO;
import com.heyqing.disk.server.modules.file.vo.HeyDiskUserFileVO;
import com.heyqing.disk.server.modules.file.vo.UploadedChunksVO;

import java.util.List;

/**
 *
 */
public interface IUserFileService extends IService<HeyDiskUserFile> {

    /**
     * 创建文件夹信息服务
     *
     * @param createFolderContext
     * @return
     */
    Long createFolder(CreateFolderContext createFolderContext);

    /**
     * 查询用户的根文件夹信息服务
     *
     * @param userId
     * @return
     */
    HeyDiskUserFile getUserRootFile(Long userId);

    /**
     * 根据条件查询用户的实际文件列表
     *
     * @param context
     * @return
     */
    List<HeyDiskUserFileVO> getFileList(QueryFileListContext context);

    /**
     * 更新文件名称服务
     *
     * @param updateFilenameContext
     */
    void updateFilename(UpdateFilenameContext updateFilenameContext);

    /**
     * 批量删除文件服务
     *
     * @param context
     */
    void deleteFile(DeleteFileContext context);

    /**
     * 文件秒传服务
     *
     * @param context
     * @return
     */
    boolean secUpload(SecUploadFileContext context);

    /**
     * 单文件上传服务
     *
     * @param context
     */
    void upload(FileUploadContext context);

    /**
     * 文件分片上传服务
     *
     * @param context
     * @return
     */
    FileChunkUploadVO chunkUpload(FileChunkUploadContext context);

    /**
     * 查询用户已上传的分片列表服务
     *
     * @param context
     * @return
     */
    UploadedChunksVO getUploadedChunks(QueryUploadedChunksContext context);
}
