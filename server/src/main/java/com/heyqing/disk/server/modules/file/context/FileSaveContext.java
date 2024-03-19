package com.heyqing.disk.server.modules.file.context;

import com.heyqing.disk.server.modules.file.entity.HeyDiskFile;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * ClassName:FileSaveContext
 * Package:com.heyqing.disk.server.modules.file.context
 * Description:
 * 保存单文件的上下文实体
 *
 * @Date:2024/3/3
 * @Author:Heyqing
 */
@Data
public class FileSaveContext implements Serializable {
    private static final long serialVersionUID = 793785827824034662L;
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
     * 文件实体
     */
    private MultipartFile file;

    /**
     * 当前用户id
     */
    private Long userId;

    /**
     * 实体文件记录
     */
    private HeyDiskFile record;

    /**
     * 文件上传的物理路径
     */
    private String realPath;
}
