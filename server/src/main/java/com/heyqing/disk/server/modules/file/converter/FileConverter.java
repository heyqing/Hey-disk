package com.heyqing.disk.server.modules.file.converter;

import com.heyqing.disk.server.modules.file.context.CreateFolderContext;
import com.heyqing.disk.server.modules.file.context.DeleteFileContext;
import com.heyqing.disk.server.modules.file.context.SecUploadFileContext;
import com.heyqing.disk.server.modules.file.context.UpdateFilenameContext;
import com.heyqing.disk.server.modules.file.po.CreateFolderPO;
import com.heyqing.disk.server.modules.file.po.DeleteFilePO;
import com.heyqing.disk.server.modules.file.po.SecUploadFilePO;
import com.heyqing.disk.server.modules.file.po.UpdateFilenamePO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * ClassName:FileConverter
 * Package:com.heyqing.disk.server.modules.file.converter
 * Description:
 * 文件模块实体转化工具类
 *
 * @Date:2024/1/17
 * @Author:Heyqing
 */
@Mapper(componentModel = "spring")
public interface FileConverter {

    /**
     * CreateFolderPO转化CreateFolderContext
     *
     * @param createFolderPO
     * @return
     */
    @Mapping(target = "parentId", expression = "java(com.heyqing.disk.core.utils.IdUtil.decrypt(createFolderPO.getParentId()))")
    @Mapping(target = "userId", expression = "java(com.heyqing.disk.server.common.utils.UserIdUtil.get())")
    CreateFolderContext createFolderPO2CreateFolderContext(CreateFolderPO createFolderPO);

    /**
     * UpdateFilenamePO转化UpdateFilenameContext
     *
     * @param updateFilenamePO
     * @return
     */
    @Mapping(target = "fileId", expression = "java(com.heyqing.disk.core.utils.IdUtil.decrypt(updateFilenamePO.getFileId()))")
    @Mapping(target = "userId", expression = "java(com.heyqing.disk.server.common.utils.UserIdUtil.get())")
    UpdateFilenameContext updateFilenamePO2UpdateFilenameContext(UpdateFilenamePO updateFilenamePO);

    /**
     * DeleteFilePO转化DeleteFileContext
     *
     * @param deleteFilePO
     * @return
     */
    @Mapping(target = "userId", expression = "java(com.heyqing.disk.server.common.utils.UserIdUtil.get())")
    DeleteFileContext deleteFilePO2DeleteFileContext(DeleteFilePO deleteFilePO);

    /**
     * SecUploadFilePO转化SecUploadFileContext
     *
     * @param secUploadFilePO
     * @return
     */
    @Mapping(target = "parentId", expression = "java(com.heyqing.disk.core.utils.IdUtil.decrypt(secUploadFilePO.getParentId()))")
    @Mapping(target = "userId", expression = "java(com.heyqing.disk.server.common.utils.UserIdUtil.get())")
    SecUploadFileContext secUploadFilePO2SecUploadFileContext(SecUploadFilePO secUploadFilePO);
}
