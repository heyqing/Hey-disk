package com.heyqing.disk.server.modules.file.converter;

import com.heyqing.disk.server.modules.file.context.*;
import com.heyqing.disk.server.modules.file.po.*;
import com.heyqing.disk.storage.engine.core.context.StoreFileChunkContext;
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

    /**
     * FileUploadPO转化FileUploadContext
     *
     * @param fileUploadPO
     * @return
     */
    @Mapping(target = "parentId", expression = "java(com.heyqing.disk.core.utils.IdUtil.decrypt(fileUploadPO.getParentId()))")
    @Mapping(target = "userId", expression = "java(com.heyqing.disk.server.common.utils.UserIdUtil.get())")
    FileUploadContext fileUploadPO2FileUploadContext(FileUploadPO fileUploadPO);

    /**
     * FileUploadContext转化FileSaveContext
     *
     * @param context
     * @return
     */
    @Mapping(target = "record", ignore = true)
    FileSaveContext fileUploadContext2FileSaveContext(FileUploadContext context);

    /**
     * FileChunkUploadPO转化FileChunkUploadContext
     *
     * @param fileChunkUploadPO
     * @return
     */
    @Mapping(target = "userId", expression = "java(com.heyqing.disk.server.common.utils.UserIdUtil.get())")
    FileChunkUploadContext fileChunkUploadPO2FileChunkUploadContext(FileChunkUploadPO fileChunkUploadPO);

    /**
     * FileChunkUploadContext转化FileChunkSaveContext
     *
     * @param context
     * @return
     */
    FileChunkSaveContext fileChunkUploadContext2FileChunkSaveContext(FileChunkUploadContext context);

    /**
     * FileChunkSaveContext转化StoreFileChunkContext
     *
     * @param context
     * @return
     */
    @Mapping(target = "realPath", ignore = true)
    StoreFileChunkContext fileChunkSaveContext2StoreFileChunkContext(FileChunkSaveContext context);
}
