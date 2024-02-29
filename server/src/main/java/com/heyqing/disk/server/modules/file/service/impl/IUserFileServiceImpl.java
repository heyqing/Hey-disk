package com.heyqing.disk.server.modules.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heyqing.disk.core.constants.HeyDiskConstants;
import com.heyqing.disk.core.exception.HeyDiskBusinessException;
import com.heyqing.disk.core.utils.FileUtil;
import com.heyqing.disk.core.utils.IdUtil;
import com.heyqing.disk.server.common.event.file.DeleteFileEvent;
import com.heyqing.disk.server.modules.file.constants.FileConstants;
import com.heyqing.disk.server.modules.file.context.*;
import com.heyqing.disk.server.modules.file.entity.HeyDiskFile;
import com.heyqing.disk.server.modules.file.entity.HeyDiskUserFile;
import com.heyqing.disk.server.modules.file.enums.DelFlagEnum;
import com.heyqing.disk.server.modules.file.enums.FileTypeEnum;
import com.heyqing.disk.server.modules.file.enums.FolderFlagEnum;
import com.heyqing.disk.server.modules.file.service.IFileService;
import com.heyqing.disk.server.modules.file.service.IUserFileService;
import com.heyqing.disk.server.modules.file.mapper.HeyDiskUserFileMapper;
import com.heyqing.disk.server.modules.file.vo.HeyDiskUserFileVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 */
@Service(value = "userFileService")
public class IUserFileServiceImpl extends ServiceImpl<HeyDiskUserFileMapper, HeyDiskUserFile> implements IUserFileService, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    private IFileService iFileService;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 创建文件夹信息
     *
     * @param createFolderContext
     * @return
     */
    @Override
    public Long createFolder(CreateFolderContext createFolderContext) {
        return saveUserFile(createFolderContext.getParentId(),
                createFolderContext.getFolderName(),
                FolderFlagEnum.YES,
                null,
                null,
                createFolderContext.getUserId(),
                null);
    }

    /**
     * 查询用户的根文件夹信息
     *
     * @param userId
     * @return
     */
    @Override
    public HeyDiskUserFile getUserRootFile(Long userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("parent_id", FileConstants.TOP_PARENT_ID);
        queryWrapper.eq("del_flag", DelFlagEnum.NO.getCode());
        queryWrapper.eq("folder_flag", FolderFlagEnum.YES.getCode());
        return getOne(queryWrapper);
    }

    /**
     * 查询用户的文件信息列表
     *
     * @param queryFileListContext
     * @return
     */
    @Override
    public List<HeyDiskUserFileVO> getFileList(QueryFileListContext queryFileListContext) {
        return baseMapper.selectFileList(queryFileListContext);
    }

    /**
     * 更新文件名称
     * 1、校验更新文件名称的条件
     * 2、执行更新文件名称的操作
     *
     * @param updateFilenameContext
     */
    @Override
    public void updateFilename(UpdateFilenameContext updateFilenameContext) {
        checkUpdateFilenameCondition(updateFilenameContext);
        doUpdateFilename(updateFilenameContext);
    }

    /**
     * 批量删除用户文件
     * 1、校验删除文件
     * 2、执行批量删除动作
     * 3、发布批量删除文件的事件，给其他模块订阅使用
     *
     * @param context
     */
    @Override
    public void deleteFile(DeleteFileContext context) {
        checkFileDeleteCondition(context);
        doDeleteFile(context);
        afterFileDelete(context);
    }

    /**
     * 文件秒传
     * <p>
     * 1、通过文件唯一标识查找对应的实体文件记录
     * 2、没有查到-直接返回
     * 3、查到-直接挂载关联关系，返回秒传成功
     *
     * @param context
     * @return
     */
    @Override
    public boolean secUpload(SecUploadFileContext context) {
        HeyDiskFile record = getFileByUserIdAndIdentifier(context.getUserId(), context.getIdentifier());
        if (Objects.isNull(record)) {
            return false;
        }
        saveUserFile(context.getParentId(),
                context.getFilename(),
                FolderFlagEnum.NO,
                FileTypeEnum.getFileTypeCode(FileUtil.getFileSuffix(context.getFilename())),
                record.getFileId(),
                context.getUserId(),
                record.getFileSizeDesc());
        return true;
    }


    /***************************************************private***************************************************/

    /**
     *
     * @param userId
     * @param identifier
     * @return
     */
    private HeyDiskFile getFileByUserIdAndIdentifier(Long userId, String identifier) {
        QueryWrapper queryWrapper = Wrappers.query();
        queryWrapper.eq("create_user",userId);
        queryWrapper.eq("identifier",identifier);
        List<HeyDiskFile> records = iFileService.list(queryWrapper);
        if (CollectionUtils.isEmpty(records)){
            return null;
        }
        return records.get(HeyDiskConstants.ZERO_INT);
    }

    /**
     * 执行文件删除的操作
     *
     * @param context
     */
    private void doDeleteFile(DeleteFileContext context) {
        List<Long> fileIdList = context.getFileIdList();
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.in("file_id", fileIdList);
        updateWrapper.set("del_flag", DelFlagEnum.YES.getCode());
        updateWrapper.set("update_time", new Date());
        if (!update(updateWrapper)) {
            throw new HeyDiskBusinessException("文件删除失败");
        }
    }

    /**
     * 文件删除的后置操作
     * 1、对外发布文件删除事件
     *
     * @param context
     */
    private void afterFileDelete(DeleteFileContext context) {
        DeleteFileEvent deleteFileEvent = new DeleteFileEvent(this, context.getFileIdList());
        applicationContext.publishEvent(deleteFileEvent);
    }

    /**
     * 删除文件之前的前置校验
     * 1、文件id合法效益
     * 2、用户拥有删除文件的权限
     *
     * @param context
     */
    private void checkFileDeleteCondition(DeleteFileContext context) {

        List<Long> fileIdList = context.getFileIdList();
        List<HeyDiskUserFile> heyDiskUserFiles = listByIds(fileIdList);
        if (heyDiskUserFiles.size() != fileIdList.size()) {
            throw new HeyDiskBusinessException("存在不合法的文件记录");
        }
        Set<Long> fileIdSet = heyDiskUserFiles.stream().map(HeyDiskUserFile::getFileId).collect(Collectors.toSet());
        int oldSize = fileIdSet.size();
        fileIdSet.addAll(fileIdList);
        int newSize = fileIdSet.size();
        if (oldSize != newSize) {
            throw new HeyDiskBusinessException("存在不合法的文件记录");
        }
        Set<Long> userIdSet = heyDiskUserFiles.stream().map(HeyDiskUserFile::getUserId).collect(Collectors.toSet());
        if (userIdSet.size() != 1) {
            throw new HeyDiskBusinessException("存在不合法的文件记录");
        }
        Long dbUserId = userIdSet.stream().findFirst().get();
        if (!Objects.equals(dbUserId, context.getUserId())) {
            throw new HeyDiskBusinessException("该用户无权限");
        }
    }


    /**
     * 执行文件文件重命名操作
     *
     * @param updateFilenameContext
     */
    private void doUpdateFilename(UpdateFilenameContext updateFilenameContext) {
        HeyDiskUserFile entity = updateFilenameContext.getEntity();

        entity.setFilename(updateFilenameContext.getNewFilename());
        entity.setUpdateUser(updateFilenameContext.getUserId());
        entity.setUpdateTime(new Date());

        if (!updateById(entity)) {
            throw new HeyDiskBusinessException("更新文件夹名称失败");
        }
    }

    /**
     * 更新文件名称的条件检验
     * 1、文件id是有效的
     * 2、用户是有权限的
     * 3、新旧名称不能一样
     * 4、不能与当前文件的其他子文件夹（即兄弟文件夹）名称重复
     *
     * @param updateFilenameContext
     */
    private void checkUpdateFilenameCondition(UpdateFilenameContext updateFilenameContext) {
        Long fileId = updateFilenameContext.getFileId();
        HeyDiskUserFile entity = getById(fileId);

        if (Objects.isNull(entity)) {
            throw new HeyDiskBusinessException("该文件id无效");
        }
        if (!Objects.equals(entity.getUserId(), updateFilenameContext.getUserId())) {
            throw new HeyDiskBusinessException("当前用户无修改该文件的权限");
        }
        if (Objects.equals(entity.getFilename(), updateFilenameContext.getNewFilename())) {
            throw new HeyDiskBusinessException("新旧文件名称不能一致");
        }

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("parent_id", entity.getParentId());
        queryWrapper.eq("filename", updateFilenameContext.getNewFilename());
        int count = count(queryWrapper);
        if (count > 0) {
            throw new HeyDiskBusinessException("该名称已存在");
        }

        updateFilenameContext.setEntity(entity);
    }


    /**
     * 保存用户文件的映射记录
     *
     * @param parentId
     * @param filename
     * @param folderFlagEnum
     * @param fileType
     * @param realFileId
     * @param userId
     * @param fileSizeDes
     * @return
     */
    private Long saveUserFile(Long parentId, String filename, FolderFlagEnum folderFlagEnum, Integer fileType, Long realFileId, Long userId, String fileSizeDes) {
        HeyDiskUserFile entity = assmaleHeyDiskUserFile(parentId, userId, filename, folderFlagEnum, fileType, realFileId, fileSizeDes);
        if (!save(entity)) {
            throw new HeyDiskBusinessException("保存文件信息失败");
        }
        return entity.getFileId();
    }

    /**
     * 用户文件映射关系实体转化
     * 1、构建并填充实体
     * 2、处理文件命名一致的问题
     *
     * @param parentId
     * @param userId
     * @param filename
     * @param folderFlagEnum
     * @param fileType
     * @param realFileId
     * @param fileSizeDes
     * @return
     */
    private HeyDiskUserFile assmaleHeyDiskUserFile(Long parentId, Long userId, String filename, FolderFlagEnum folderFlagEnum, Integer fileType, Long realFileId, String fileSizeDes) {
        HeyDiskUserFile entity = new HeyDiskUserFile();
        entity.setFileId(IdUtil.get());
        entity.setUserId(userId);
        entity.setParentId(parentId);
        entity.setRealFileId(realFileId);
        entity.setFilename(filename);
        entity.setFolderFlag(folderFlagEnum.getCode());
        entity.setFileSizeDesc(fileSizeDes);
        entity.setFileType(fileType);
        entity.setDelFlag(DelFlagEnum.NO.getCode());
        entity.setCreateUser(userId);
        entity.setCreateTime(new Date());
        entity.setUpdateUser(userId);
        entity.setUpdateTime(new Date());

        handleDuplicateFilename(entity);

        return entity;
    }

    /**
     * 处理用户重复名称
     * 如果同一文件夹下面由文件名称重复
     * 按照系统级规则重命名文件
     *
     * @param entity
     */
    private void handleDuplicateFilename(HeyDiskUserFile entity) {

        String filename = entity.getFilename(),
                newFilenameWithoutSuffix,
                newFilenameSuffix;
        int newFilenamePointPosition = filename.lastIndexOf(HeyDiskConstants.POINT_STR);
        if (newFilenamePointPosition == HeyDiskConstants.MINUS_ONE_INT) {
            newFilenameWithoutSuffix = filename;
            newFilenameSuffix = StringUtils.EMPTY;
        } else {
            newFilenameWithoutSuffix = filename.substring(HeyDiskConstants.ZERO_INT, newFilenamePointPosition);
            newFilenameSuffix = filename.replace(newFilenameWithoutSuffix, StringUtils.EMPTY);
        }

        int count = getDuplicateFilename(entity, newFilenameWithoutSuffix);
        if (count == 0) {
            return;
        }
        String newFilename = assembleNewFilename(newFilenameWithoutSuffix, count, newFilenameSuffix);

        entity.setFilename(newFilename);
    }

    /**
     * 拼装新文件名称
     * 拼装规则参考操作系统重复文件名称的重命名规范
     *
     * @param newFilenameWithoutSuffix
     * @param count
     * @param newFilenameSuffix
     * @return
     */
    private String assembleNewFilename(String newFilenameWithoutSuffix, int count, String newFilenameSuffix) {
        String newFilename = new StringBuilder(newFilenameWithoutSuffix)
                .append(FileConstants.CN_LEFT_PARENTHESES_STR)
                .append(count)
                .append(FileConstants.CN_RIGHT_PARENTHESES_STR)
                .append(newFilenameSuffix)
                .toString();
        return newFilename;
    }

    /**
     * 查找同一父文件夹下的同名文件数量
     *
     * @param entity
     * @param newFilenameWithoutSuffix
     * @return
     */
    private int getDuplicateFilename(HeyDiskUserFile entity, String newFilenameWithoutSuffix) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("parent_id", entity.getParentId());
        queryWrapper.eq("folder_flag", entity.getFolderFlag());
        queryWrapper.eq("user_id", entity.getUserId());
        queryWrapper.eq("del_flag", DelFlagEnum.NO.getCode());
        queryWrapper.likeLeft("filename", newFilenameWithoutSuffix);

        return count(queryWrapper);
    }

}




