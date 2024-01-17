package com.heyqing.disk.server.modules.file.context;

import com.heyqing.disk.server.modules.file.entity.HeyDiskUserFile;
import lombok.Data;

import java.io.Serializable;

/**
 * ClassName:UpdateFilenameContext
 * Package:com.heyqing.disk.server.modules.file.context
 * Description:
 *文件重命名参数上下文对象
 * @Date:2024/1/17
 * @Author:Heyqing
 */
@Data
public class UpdateFilenameContext implements Serializable {
    private static final long serialVersionUID = 1858435355351396632L;

    /**
     * 更新文件的id
     */
    private Long fileId;

    /**
     * 新的文件名称
     */
    private String newFilename;

    /**
     * 当前用户id
     */
    private Long userId;

    /**
     * 当前用户实体
     */
    private HeyDiskUserFile entity;
}
