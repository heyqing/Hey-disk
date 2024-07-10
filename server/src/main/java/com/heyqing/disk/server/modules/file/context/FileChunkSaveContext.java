package com.heyqing.disk.server.modules.file.context;

import com.heyqing.disk.server.modules.file.enums.MergeFlagEnum;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * ClassName:FileChunkSaveContext
 * Package:com.heyqing.disk.server.modules.file.context
 * Description:
 * 文件分片保存的上下文实体信息
 *
 * @Date:2024/3/20
 * @Author:Heyqing
 */
@Data
public class FileChunkSaveContext implements Serializable {
    private static final long serialVersionUID = -4717521866181461575L;

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
     * 文件实体
     */
    private MultipartFile file;

    /**
     * 当前登录用户id
     */
    private Long userId;

    /**
     * 文件合并标识
     */
    private MergeFlagEnum mergeFlagEnum = MergeFlagEnum.NOT_READY;

    /**
     * 文件分片的真实存储路径
     */
    private String realPath;
}
