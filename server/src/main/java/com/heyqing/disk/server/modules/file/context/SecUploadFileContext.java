package com.heyqing.disk.server.modules.file.context;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName:SecUploadFileContext
 * Package:com.heyqing.disk.server.modules.file.context
 * Description:
 * 文件秒传上下文实体对象
 *
 * @Date:2024/2/28
 * @Author:Heyqing
 */
@Data
public class SecUploadFileContext implements Serializable {

    private static final long serialVersionUID = 834284451803188316L;

    /**
     * 文件夹id
     */
    private Long parentId;
    /**
     * 文件名称
     */
    private String filename;
    /**
     * 文件唯一标识
     */
    private String identifier;
    /**
     * 当前登录用户
     */
    private Long userId;
}
