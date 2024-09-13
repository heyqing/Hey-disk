package com.heyqing.disk.server.modules.file.service;

import com.heyqing.disk.server.modules.file.context.*;
import com.heyqing.disk.server.modules.file.entity.HeyDiskFile;
import com.heyqing.disk.server.modules.file.entity.HeyDiskUserFile;
import com.baomidou.mybatisplus.extension.service.IService;
import com.heyqing.disk.server.modules.file.vo.*;

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

    /**
     * 文件分片合并服务
     *
     * @param context
     */
    void mergeFile(FileChunkMergeContext context);

    /**
     * 文件下载服务
     *
     * @param context
     */
    void download(FileDownloadContext context);

    /**
     * 文件预览服务
     *
     * @param context
     */
    void preview(FilePreviewContext context);

    /**
     * 查询文件夹树服务
     *
     * @param context
     * @return
     */
    List<FolderTreeNodeVO> getFolderTree(QueryFolderTreeContext context);

    /**
     * 文件转移服务
     *
     * @param context
     */
    void transfer(TransferFileContext context);

    /**
     * 文件复制服务
     *
     * @param context
     */
    void copy(CopyFileContext context);

    /**
     * 文件搜索服务
     *
     * @param context
     * @return
     */
    List<FileSearchResultVO> search(FileSearchContext context);

    /**
     * 获取面包屑列表服务
     *
     * @param context
     * @return
     */
    List<BreadcrumbVO> getBreadcrumbs(QueryBreadcrumbsContext context);
}
