package com.heyqing.disk.server.modules.file.context;

import com.heyqing.disk.server.modules.file.entity.HeyDiskFile;
import lombok.Data;

import java.io.Serializable;

/**
 * ClassName:FileChunkMergeContext
 * Package:com.heyqing.disk.server.modules.file.context
 * Description:
 *
 * @Date:2024/9/2
 * @Author:Heyqing
 */
@Data
public class FileChunkMergeContext implements Serializable {
    private static final long serialVersionUID = 1379200881593892347L;

    /**
     * 文件名称
     */
    private String filename;
    /**
     * 文件唯一标识
     */
    private String identifier;
    /**
     * 文件总大小
     */
    private Long totalSize;
    /**
     * 文件的父文件夹Id
     */
    private Long parentId;

    /**
     * 当前登录用户的userId
     */
    private Long userId;

    /**
     * 物理文件记录
     */
    private HeyDiskFile record;
}
