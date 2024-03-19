package com.heyqing.disk.server.modules.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heyqing.disk.core.exception.HeyDiskBusinessException;
import com.heyqing.disk.core.utils.FileUtil;
import com.heyqing.disk.core.utils.IdUtil;
import com.heyqing.disk.server.common.event.log.ErrorLogEvent;
import com.heyqing.disk.server.modules.file.context.FileSaveContext;
import com.heyqing.disk.server.modules.file.context.QueryFileListContext;
import com.heyqing.disk.server.modules.file.entity.HeyDiskFile;
import com.heyqing.disk.server.modules.file.service.IFileService;
import com.heyqing.disk.server.modules.file.mapper.HeyDiskFileMapper;
import com.heyqing.disk.server.modules.file.vo.HeyDiskUserFileVO;
import com.heyqing.disk.storage.engine.core.StorageEngine;
import com.heyqing.disk.storage.engine.core.context.DeleteFileContext;
import com.heyqing.disk.storage.engine.core.context.StoreFileContext;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 */
@Service
public class IFileServiceImpl extends ServiceImpl<HeyDiskFileMapper, HeyDiskFile> implements IFileService, ApplicationContextAware {

    @Autowired
    private StorageEngine storageEngine;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * 查询用户的文件信息列表
     *
     * @param context
     * @return
     */
//    @Override
//    public List<HeyDiskFile> getFileList(QueryRealFileListContext context) {
//        Long userId = context.getUserId();
//        String  identifier = context.getIdentifier();
//        LambdaQueryWrapper<HeyDiskFile> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(Objects.nonNull(userId),HeyDiskFile::getCreateUser,userId);
//        queryWrapper.eq(StringUtils.isNotBlank(identifier),HeyDiskFile::getIdentifier,identifier);
//        return list(queryWrapper);
//    }

    /**
     * 上传单文件并保存实体记录
     * <p>
     * 1、上传单文件
     * 2、保存实体记录
     *
     * @param context
     */
    @Override
    public void saveFile(FileSaveContext context) {
        storeMultipartFile(context);
        HeyDiskFile record = doSaveFile(context.getFilename(),
                context.getRealPath(),
                context.getTotalSize(),
                context.getIdentifier(),
                context.getUserId());
        context.setRecord(record);
    }


    /*************************************************************private***************************************************/

    /**
     * 上传单文件
     * 该方法委托文件存储引擎实现
     *
     * @param context
     */
    private void storeMultipartFile(FileSaveContext context) {
        try {
            StoreFileContext storeFileContext = new StoreFileContext();
            storeFileContext.setInputStream(context.getFile().getInputStream());
            storeFileContext.setFilename(context.getFilename());
            storeFileContext.setTotalSize(context.getTotalSize());
            storageEngine.store(storeFileContext);
            context.setRealPath(storeFileContext.getRealPath());
        } catch (IOException e) {
            e.printStackTrace();
            throw new HeyDiskBusinessException("文件上传失败");
        }
    }

    /**
     * 保存实体文件记录
     *
     * @param filename
     * @param realPath
     * @param totalSize
     * @param identifier
     * @param userId
     */
    private HeyDiskFile doSaveFile(String filename, String realPath, Long totalSize, String identifier, Long userId) {
        HeyDiskFile record = assembleHeyDiskFile(filename, realPath, totalSize, identifier, userId);
        if (!save(record)) {
         try{
             DeleteFileContext deleteFileContext = new DeleteFileContext();
             deleteFileContext.setRealFilePathList(Lists.newArrayList(realPath));
             storageEngine.delete(deleteFileContext);
         }catch (IOException e){
             e.printStackTrace();
             ErrorLogEvent errorLogEvent = new ErrorLogEvent(this,"文件物理删除失败，请执行手动删除！文件路径: " + realPath,userId);
             applicationContext.publishEvent(errorLogEvent);
         }
        }
        return record;
    }

    /**
     * 拼装文件实体对象
     *
     * @param filename
     * @param realPath
     * @param totalSize
     * @param identifier
     * @param userId
     * @return
     */
    private HeyDiskFile assembleHeyDiskFile(String filename, String realPath, Long totalSize, String identifier, Long userId) {
        HeyDiskFile record = new HeyDiskFile();

        record.setFileId(IdUtil.get());
        record.setFilename(filename);
        record.setRealPath(realPath);
        record.setFileSize(String.valueOf(totalSize));
        record.setFileSizeDesc(FileUtil.byteCountToDisplaySize(totalSize));
        record.setFileSuffix(FileUtil.getFileSuffix(filename));
        record.setIdentifier(identifier);
        record.setCreateUser(userId);
        record.setCreateTime(new Date());

        return record;
    }
}




