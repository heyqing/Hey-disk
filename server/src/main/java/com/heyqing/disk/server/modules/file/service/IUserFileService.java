package com.heyqing.disk.server.modules.file.service;

import com.heyqing.disk.server.modules.file.context.CreateFolderContext;
import com.heyqing.disk.server.modules.file.entity.HeyDiskUserFile;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface IUserFileService extends IService<HeyDiskUserFile> {

    /**
     * 创建文件夹信息
     *
     * @param createFolderContext
     * @return
     */
    Long createFolder(CreateFolderContext createFolderContext);

    /**
     * 查询用户的根文件夹信息
     *
     * @param userId
     * @return
     */
    HeyDiskUserFile getUserRootFile(Long userId);
}
