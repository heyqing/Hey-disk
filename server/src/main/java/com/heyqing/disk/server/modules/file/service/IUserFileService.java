package com.heyqing.disk.server.modules.file.service;

import com.heyqing.disk.server.modules.file.context.CreateFolderContext;
import com.heyqing.disk.server.modules.file.context.DeleteFileContext;
import com.heyqing.disk.server.modules.file.context.QueryFileListContext;
import com.heyqing.disk.server.modules.file.context.UpdateFilenameContext;
import com.heyqing.disk.server.modules.file.entity.HeyDiskUserFile;
import com.baomidou.mybatisplus.extension.service.IService;
import com.heyqing.disk.server.modules.file.vo.HeyDiskUserFileVO;

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
     * 查询用户的文件信息列表服务
     *
     * @param queryFileListContext
     * @return
     */
    List<HeyDiskUserFileVO> getFileList(QueryFileListContext queryFileListContext);

    /**
     * 更新文件名称服务
     *
     * @param updateFilenameContext
     */
    void updateFilename(UpdateFilenameContext updateFilenameContext);

    /**
     * 批量删除文件
     *
     * @param context
     */
    void deleteFile(DeleteFileContext context);
}
