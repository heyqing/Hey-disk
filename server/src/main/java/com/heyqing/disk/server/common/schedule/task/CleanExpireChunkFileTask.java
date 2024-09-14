package com.heyqing.disk.server.common.schedule.task;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.heyqing.disk.core.constants.HeyDiskConstants;
import com.heyqing.disk.schedule.ScheduleTask;
import com.heyqing.disk.server.common.event.log.ErrorLogEvent;
import com.heyqing.disk.server.modules.file.entity.HeyDiskFileChunk;
import com.heyqing.disk.server.modules.file.service.IFileChunkService;
import com.heyqing.disk.storage.engine.core.StorageEngine;
import com.heyqing.disk.storage.engine.core.context.DeleteFileContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName:CleanExpireChunkFileTask
 * Package:com.heyqing.disk.server.common.task
 * Description:
 * 过期分片清理任务
 *
 * @Date:2024/9/14
 * @Author:Heyqing
 */
@Component
@Slf4j
public class CleanExpireChunkFileTask implements ScheduleTask, ApplicationContextAware {

    private static final Long BATCH_SIZE = 500L;
    @Autowired
    private IFileChunkService iFileChunkService;
    @Autowired
    private StorageEngine storageEngine;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 定时任务名称
     *
     * @return
     */
    @Override
    public String getName() {
        return "CleanExpireChunkFileTask";
    }

    /**
     * 执行清理任务
     * <p>
     * 1、滚动查询过期分片
     * 2、删除物理文件
     * 3、删除过期文件分片的记录信息
     * 4、重置上次查询的最大文件分片记录ID，继续滚动查询
     */
    @Override
    public void run() {
        log.info("{} start clean expire chunk file ...", getName());
        List<HeyDiskFileChunk> expireFileChunkRecords;
        Long scrollPointer = 1L;
        do {
            expireFileChunkRecords = scrollQueryExpireFileChunkRecords(scrollPointer);
            if (CollectionUtils.isNotEmpty(expireFileChunkRecords)) {
                deleteRealChunkFiles(expireFileChunkRecords);
                List<Long> idList = deleteChunkFileRecords(expireFileChunkRecords);
                scrollPointer = Collections.max(idList);
            }
        } while (CollectionUtils.isNotEmpty(expireFileChunkRecords));
        log.info("{} finish clean expire chunk file ...", getName());
    }


    /************************************************************private************************************************************/

    /**
     * 滚动查询过期文件记录
     *
     * @param scrollPointer
     * @return
     */
    private List<HeyDiskFileChunk> scrollQueryExpireFileChunkRecords(Long scrollPointer) {
        QueryWrapper queryWrapper = Wrappers.query();
        queryWrapper.le("expiration_time", new Date());
        queryWrapper.ge("id", scrollPointer);
        queryWrapper.last("limit " + BATCH_SIZE);
        return iFileChunkService.list(queryWrapper);
    }

    /**
     * 删除物理文件
     * 委托文件存储引擎
     *
     * @param expireFileChunkRecords
     */
    private void deleteRealChunkFiles(List<HeyDiskFileChunk> expireFileChunkRecords) {
        DeleteFileContext context = new DeleteFileContext();
        List<String> realPaths = expireFileChunkRecords.stream().map(HeyDiskFileChunk::getRealPath).collect(Collectors.toList());
        context.setRealFilePathList(realPaths);
        try {
            storageEngine.delete(context);
        } catch (IOException e) {
            saveErrorLog(realPaths);
        }
    }

    /**
     * 删除过期文件分片记录
     *
     * @param expireFileChunkRecords
     * @return
     */
    private List<Long> deleteChunkFileRecords(List<HeyDiskFileChunk> expireFileChunkRecords) {
        List<Long> idList = expireFileChunkRecords.stream().map(HeyDiskFileChunk::getId).collect(Collectors.toList());
        iFileChunkService.removeByIds(idList);
        return idList;
    }

    private void saveErrorLog(List<String> realPaths) {
        ErrorLogEvent errorLogEvent = new ErrorLogEvent(this, "文件物理删除失败，路径为：" + JSON.toJSONString(realPaths), HeyDiskConstants.ZERO_LONG);
        applicationContext.publishEvent(errorLogEvent);
    }
}
