package com.heyqing.disk.server.modules.file.context;

import lombok.Data;


import java.io.Serializable;

/**
 * ClassName:FileChunkUploadContext
 * Package:com.heyqing.disk.server.modules.file.context
 * Description:
 * 文件分片上传上下文实体
 *
 * @Date:2024/3/20
 * @Author:Heyqing
 */
@Data
public class FileChunkUploadContext implements Serializable {

    private static final long serialVersionUID = 3229077512384315307L;

    /**
     * 文件名称
     */
    private String filename;

    /**
     * 文件唯一标识
     */
    private String identifier;

    /**
     * 总分片数
     */
    private Integer totalChunks;

    /**
     * 当前分片的下标
     */
    private Integer chunkNumber;

    /**
     * 当前分片的大小
     */
    private Long currentChunkSize;

    /**
     * 分片文件实体
     */
    private Long totalSize;

    /**
     * 当前登录用户id
     */
    private Long userId;
}
