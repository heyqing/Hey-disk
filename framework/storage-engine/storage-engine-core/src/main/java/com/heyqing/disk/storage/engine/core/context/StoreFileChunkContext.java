package com.heyqing.disk.storage.engine.core.context;

import lombok.Data;

import java.io.InputStream;
import java.io.Serializable;

/**
 * ClassName:StoreFileChunkContext
 * Package:com.heyqing.disk.storage.engine.core.context
 * Description:
 * 保存文件分片的上下文信息
 *
 * @Date:2024/7/10
 * @Author:Heyqing
 */
@Data
public class StoreFileChunkContext implements Serializable {
    private static final long serialVersionUID = -5885863418352434509L;
    /**
     * 文件名称
     */
    private String filename;
    /**
     * 文件唯一标识
     */
    private String identifier;
    /**
     * 文件的总大小
     */
    private Long totalSize;
    /**
     * 文件输入流
     */
    private InputStream inputStream;
    /**
     * 文件真实存储路径
     */
    private String realPath;
    /**
     * 文件总分片数
     */
    private Integer totalChunks;
    /**
     * 文件分片的下标
     */
    private Integer chunkNumber;
    /**
     * 当前文件的大小
     */
    private Long currentChunkSize;
    /**
     * 当前登录用户ID
     */
    private Long userId;
}
