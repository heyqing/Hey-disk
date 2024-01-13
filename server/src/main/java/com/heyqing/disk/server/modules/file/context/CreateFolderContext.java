package com.heyqing.disk.server.modules.file.context;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName:CreateFolderContext
 * Package:com.heyqing.disk.server.modules.file.context
 * Description:
 *          创建文件夹上下文实体
 * @Date:2024/1/13
 * @Author:Heyqing
 */
@Data
public class CreateFolderContext implements Serializable {
    private static final long serialVersionUID = -2201518654237086163L;

    /**
     * 父文件夹id
     */
    private Long parentId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 文件夹名称
     */
    private String folderName;
}
