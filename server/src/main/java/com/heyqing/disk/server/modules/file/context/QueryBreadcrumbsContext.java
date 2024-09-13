package com.heyqing.disk.server.modules.file.context;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName:QueryBreadcrumbsContext
 * Package:com.heyqing.disk.server.modules.file.context
 * Description:
 * 文件面包屑查询上下文实体
 *
 * @Date:2024/9/13
 * @Author:Heyqing
 */
@Data
public class QueryBreadcrumbsContext implements Serializable {
    private static final long serialVersionUID = -4939355011710015011L;

    /**
     * 文件id
     */
    private Long fileId;

    /**
     * 当前登录的用户id
     */
    private Long userId;
}
