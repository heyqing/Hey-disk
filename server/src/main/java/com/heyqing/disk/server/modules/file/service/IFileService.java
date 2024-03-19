package com.heyqing.disk.server.modules.file.service;

import com.heyqing.disk.server.modules.file.context.FileSaveContext;
import com.heyqing.disk.server.modules.file.context.QueryFileListContext;
import com.heyqing.disk.server.modules.file.entity.HeyDiskFile;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface IFileService extends IService<HeyDiskFile> {

    /**
     * 根据条件查询用户的实际文件列表
     * @param context
     * @return
     */
//    List<HeyDiskFile> getFileList(QueryRealFileListContent context);

    /**
     * 上传单文件并保存实体记录
     *
     * @param context
     */
    void saveFile(FileSaveContext context);

}
