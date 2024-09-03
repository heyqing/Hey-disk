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
public class FileChunkMergeAndSaveContext implements Serializable {

    private static final long serialVersionUID = -175883416469777029L;
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

    /**
     * 文件合并之后存储的真实物理路径
     */
    private String realPath;
}
