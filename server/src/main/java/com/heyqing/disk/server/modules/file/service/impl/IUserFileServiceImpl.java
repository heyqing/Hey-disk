package com.heyqing.disk.server.modules.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.heyqing.disk.core.constants.HeyDiskConstants;
import com.heyqing.disk.core.exception.HeyDiskBusinessException;
import com.heyqing.disk.core.utils.FileUtil;
import com.heyqing.disk.core.utils.IdUtil;
import com.heyqing.disk.server.common.event.file.DeleteFileEvent;
import com.heyqing.disk.server.common.utils.HttpUtil;
import com.heyqing.disk.server.modules.file.constants.FileConstants;
import com.heyqing.disk.server.modules.file.context.*;
import com.heyqing.disk.server.modules.file.converter.FileConverter;
import com.heyqing.disk.server.modules.file.entity.HeyDiskFile;
import com.heyqing.disk.server.modules.file.entity.HeyDiskFileChunk;
import com.heyqing.disk.server.modules.file.entity.HeyDiskUserFile;
import com.heyqing.disk.server.modules.file.enums.DelFlagEnum;
import com.heyqing.disk.server.modules.file.enums.FileTypeEnum;
import com.heyqing.disk.server.modules.file.enums.FolderFlagEnum;
import com.heyqing.disk.server.modules.file.service.IFileChunkService;
import com.heyqing.disk.server.modules.file.service.IFileService;
import com.heyqing.disk.server.modules.file.service.IUserFileService;
import com.heyqing.disk.server.modules.file.mapper.HeyDiskUserFileMapper;
import com.heyqing.disk.server.modules.file.vo.FileChunkUploadVO;
import com.heyqing.disk.server.modules.file.vo.FolderTreeNodeVO;
import com.heyqing.disk.server.modules.file.vo.HeyDiskUserFileVO;
import com.heyqing.disk.server.modules.file.vo.UploadedChunksVO;
import com.heyqing.disk.storage.engine.core.StorageEngine;
import com.heyqing.disk.storage.engine.core.context.ReadFileContext;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 */
@Service(value = "userFileService")
public class IUserFileServiceImpl extends ServiceImpl<HeyDiskUserFileMapper, HeyDiskUserFile> implements IUserFileService, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    private IFileService iFileService;
    @Autowired
    private IFileChunkService iFileChunkService;
    @Autowired
    private FileConverter fileConverter;
    @Autowired
    private StorageEngine storageEngine;

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

    /**
     * 单文件上传
     * <p>
     * 1、上传文件并保存实体文件记录
     * 2、保存用户文件的关系记录
     * Transactional(rollbackFor = Exception.class)事物
     *
     * @param context
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void upload(FileUploadContext context) {
        saveFile(context);
        saveUserFile(context.getParentId(),
                context.getFilename(),
                FolderFlagEnum.NO,
                FileTypeEnum.getFileTypeCode(FileUtil.getFileSuffix(context.getFilename())),
                context.getRecord().getFileId(),
                context.getUserId(),
                context.getRecord().getFileSizeDesc());
    }

    /**
     * 文件分片上传
     * <p>
     * 1、上传实体文件
     * 2、保存分片文件记录
     * 3、校验是否全部分片上传完成
     *
     * @param context
     * @return
     */
    @Override
    public FileChunkUploadVO chunkUpload(FileChunkUploadContext context) {
        FileChunkSaveContext fileChunkSaveContext = fileConverter.fileChunkUploadContext2FileChunkSaveContext(context);
        iFileChunkService.saveChunkFile(fileChunkSaveContext);
        FileChunkUploadVO vo = new FileChunkUploadVO();
        vo.setMergeFlag(fileChunkSaveContext.getMergeFlagEnum().getCode());
        return vo;
    }

    /**
     * 查询用户已上传的分片列表
     * <p>
     * 1、查询已上传的分片列表
     * 2、封装返回实体
     *
     * @param context
     * @return
     */
    @Override
    public UploadedChunksVO getUploadedChunks(QueryUploadedChunksContext context) {
        QueryWrapper queryWrapper = Wrappers.query();
        queryWrapper.select("chunk_number");
        queryWrapper.eq("identifier", context.getIdentifier());
        queryWrapper.eq("create_user", context.getUserId());
        queryWrapper.gt("expiration_time", new Date());

        List<Integer> uploadedChunks = iFileChunkService.listObjs(queryWrapper, value -> (Integer) value);
        UploadedChunksVO vo = new UploadedChunksVO();
        vo.setUploadedChunks(uploadedChunks);
        return vo;
    }

    /**
     * 文件分片合并
     * <p>
     * 1、文件分片物理合并
     * 2、保存文件实体记录
     * 3、保存文件用户关系映射
     *
     * @param context
     */
    @Override
    public void mergeFile(FileChunkMergeContext context) {
        mergeFileChunksAndSaveFile(context);
        saveUserFile(context.getParentId(),
                context.getFilename(),
                FolderFlagEnum.NO,
                FileTypeEnum.getFileTypeCode(FileUtil.getFileSuffix(context.getFilename())),
                context.getRecord().getFileId(),
                context.getUserId(),
                context.getRecord().getFileSizeDesc());
    }

    /**
     * 文件下载
     * <p>
     * 1、参数校验：文件是否存在；文件是否属于该用户
     * 2、校验该文件是否为文件夹
     * 3、执行下载动作
     *
     * @param context
     */
    @Override
    public void download(FileDownloadContext context) {
        HeyDiskUserFile record = getById(context.getFileId());
        checkOperatePermission(record, context.getUserId());
        if (checkIsFolder(record)) {
            throw new HeyDiskBusinessException("文件夹暂不支持下载");
        }
        doDownload(record, context.getResponse());
    }

    /**
     * 文件预览
     * <p>
     * 1、参数校验：文件是否存在；文件是否属于该用户
     * 2、校验该文件是否为文件夹
     * 3、文件预览操作
     *
     * @param context
     */
    @Override
    public void preview(FilePreviewContext context) {
        HeyDiskUserFile record = getById(context.getFileId());
        checkOperatePermission(record, context.getUserId());
        if (checkIsFolder(record)) {
            throw new HeyDiskBusinessException("文件夹暂不支持下载");
        }
        daPreview(record, context.getResponse());
    }

    /**
     * 查询文件夹树
     * <p>
     * 1、查询出该用户的所有文件夹列表
     * 2、在内存中拼接文件夹树
     *
     * @param context
     * @return
     */
    @Override
    public List<FolderTreeNodeVO> getFolderTree(QueryFolderTreeContext context) {
        List<HeyDiskUserFile> folderRecords = queryFolderRecords(context.getUserId());
        List<FolderTreeNodeVO> result = assembleFolderTreeNodeVOList(folderRecords);
        return result;
    }


    /***************************************************private***************************************************/

    /**
     * 查询出该用户的所有文件夹列表
     *
     * @param userId
     * @return
     */
    private List<HeyDiskUserFile> queryFolderRecords(Long userId) {
        QueryWrapper queryWrapper = Wrappers.query();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("folder_flag", FolderFlagEnum.YES.getCode());
        queryWrapper.eq("del_flag", DelFlagEnum.NO.getCode());
        return list(queryWrapper);
    }

    /**
     * 在内存中拼接文件夹树
     *
     * @param folderRecords
     * @return
     */
    private List<FolderTreeNodeVO> assembleFolderTreeNodeVOList(List<HeyDiskUserFile> folderRecords) {
        if (CollectionUtils.isEmpty(folderRecords)) {
            return Lists.newArrayList();
        }
        List<FolderTreeNodeVO> mappedFolderTreeNodeVOList = folderRecords.stream().map(fileConverter::heyDiskUserFile2FolderTreeNodeVO).collect(Collectors.toList());
        Map<Long, List<FolderTreeNodeVO>> mappedFolderTreeNodeVOMap = mappedFolderTreeNodeVOList.stream().collect(Collectors.groupingBy(FolderTreeNodeVO::getParentId));
        for (FolderTreeNodeVO node : mappedFolderTreeNodeVOList) {
            List<FolderTreeNodeVO> children = mappedFolderTreeNodeVOMap.get(node.getId());
            if (CollectionUtils.isNotEmpty(children)) {
                node.getChildren().addAll(children);
            }
        }
        return mappedFolderTreeNodeVOList.stream().filter(node -> Objects.equals(node.getParentId(), FileConstants.TOP_PARENT_ID)).collect(Collectors.toList());
    }

    /**
     * 文件预览操作
     *
     * @param record
     * @param response
     */
    private void daPreview(HeyDiskUserFile record, HttpServletResponse response) {
        HeyDiskFile realFileRecord = iFileService.getById(record.getRealFileId());
        if (Objects.isNull(realFileRecord)) {
            throw new HeyDiskBusinessException("当前的文件记录不存在");
        }
        addCommonResponseHeader(response, realFileRecord.getFilePreviewContentType());
        realFile2OutputStream(realFileRecord.getRealPath(), response);
    }

    /**
     * 校验用户的操作权限
     * <p>
     * 1、文件记录是否存在
     * 2、文件记录的创建者是否为该用户
     *
     * @param record
     * @param userId
     */
    private void checkOperatePermission(HeyDiskUserFile record, Long userId) {
        if (Objects.isNull(record)) {
            throw new HeyDiskBusinessException("当前文件记录不存在");
        }
        if (!record.getUserId().equals(userId)) {
            throw new HeyDiskBusinessException("你没有该文件的操作权限");
        }
    }

    /**
     * 检查当前文件是否为文件夹
     *
     * @param record
     * @return
     */
    private boolean checkIsFolder(HeyDiskUserFile record) {
        if (Objects.isNull(record)) {
            throw new HeyDiskBusinessException("当前文件记录不存在");
        }
        return FolderFlagEnum.YES.getCode().equals(record.getFolderFlag());
    }

    /**
     * 文件下载
     * <p>
     * 1、查询文件的真实存储路径
     * 2、添加跨域的公共响应头
     * 3、拼接下载文件的名称、长度等相应信息
     * 4、委托文件存储引擎读取文件内容到相应的输出流中
     *
     * @param record
     * @param response
     */
    private void doDownload(HeyDiskUserFile record, HttpServletResponse response) {
        HeyDiskFile realFileRecord = iFileService.getById(record.getRealFileId());
        if (Objects.isNull(realFileRecord)) {
            throw new HeyDiskBusinessException("当前的文件记录不存在");
        }
        addCommonResponseHeader(response, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        addDownloadAttribute(response, record, realFileRecord);
        realFile2OutputStream(realFileRecord.getRealPath(), response);
    }

    /**
     * 委托文件存储引擎读取文件内容到相应的输出流中
     *
     * @param realPath
     * @param response
     */
    private void realFile2OutputStream(String realPath, HttpServletResponse response) {
        try {
            ReadFileContext context = new ReadFileContext();
            context.setRealPath(realPath);
            context.setOutputStream(response.getOutputStream());
            storageEngine.readFile(context);
        } catch (IOException e) {
            e.printStackTrace();
            throw new HeyDiskBusinessException("文件下载失败");
        }
    }

    /**
     * 添加文件下载的属性信息
     *
     * @param response
     * @param record
     * @param realFileRecord
     */
    private void addDownloadAttribute(HttpServletResponse response, HeyDiskUserFile record, HeyDiskFile realFileRecord) {
        try {
            response.addHeader(FileConstants.CONTENT_DISPOSITION_STR,
                    FileConstants.CONTENT_DISPOSITION_VALUE_PREFIX_STR + new String(record.getFilename().getBytes(FileConstants.GB2312_STR), FileConstants.ISO_8859_1_STR));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new HeyDiskBusinessException("文件下载失败");
        }
        response.setContentLengthLong(Long.valueOf(realFileRecord.getFileSize()));
    }

    /**
     * 添加跨域的公共响应头
     *
     * @param response
     * @param contentTypeValue
     */
    private void addCommonResponseHeader(HttpServletResponse response, String contentTypeValue) {
        response.reset();
        HttpUtil.addCorsResponseHeader(response);
        response.addHeader(FileConstants.CONTENT_TYPE_STR, contentTypeValue);
        response.setContentType(contentTypeValue);
    }

    /**
     * 合并文件分片并保存物理文件记录
     *
     * @param context
     */
    private void mergeFileChunksAndSaveFile(FileChunkMergeContext context) {
        FileChunkMergeAndSaveContext fileChunkMergeAndSaveContext = fileConverter.fileChunkMergeContext2FileChunkMergeAndSaveContext(context);
        iFileService.mergeFileChunksAndSaveFile(fileChunkMergeAndSaveContext);
        context.setRecord(fileChunkMergeAndSaveContext.getRecord());
    }

    /**
     * 上传文件并保存实体文件记录
     * 委托给实体文件的service去完成该操作
     *
     * @param context
     */
    private void saveFile(FileUploadContext context) {
        FileSaveContext fileSaveContext = fileConverter.fileUploadContext2FileSaveContext(context);
        iFileService.saveFile(fileSaveContext);
        context.setRecord(fileSaveContext.getRecord());
    }

    /**
     * @param userId
     * @param identifier
     * @return
     */
    private HeyDiskFile getFileByUserIdAndIdentifier(Long userId, String identifier) {
        QueryWrapper queryWrapper = Wrappers.query();
        queryWrapper.eq("create_user", userId);
        queryWrapper.eq("identifier", identifier);
        List<HeyDiskFile> records = iFileService.list(queryWrapper);
        if (CollectionUtils.isEmpty(records)) {
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




