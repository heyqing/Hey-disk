package com.heyqing.disk.server.modules.file.converter;

import com.heyqing.disk.server.modules.file.context.CreateFolderContext;
import com.heyqing.disk.server.modules.file.context.UpdateFilenameContext;
import com.heyqing.disk.server.modules.file.po.CreateFolderPO;
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
    @Mapping(target = "parentId",expression = "java(com.heyqing.disk.core.utils.IdUtil.decrypt(createFolderPO.getParentId()))")
    @Mapping(target = "userId",expression = "java(com.heyqing.disk.server.common.utils.UserIdUtil.get())")
    CreateFolderContext createFolderPO2CreateFolderContext(CreateFolderPO createFolderPO);

    /**
     * UpdateFilenamePO转化UpdateFilenameContext
     * @param updateFilenamePO
     * @return
     */
    @Mapping(target = "fileId",expression = "java(com.heyqing.disk.core.utils.IdUtil.decrypt(updateFilenamePO.getFileId()))")
    @Mapping(target = "userId",expression = "java(com.heyqing.disk.server.common.utils.UserIdUtil.get())")
    UpdateFilenameContext updateFilenamePO2UpdateFilenameContext(UpdateFilenamePO updateFilenamePO);
}
