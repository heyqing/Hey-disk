package com.heyqing.disk.server.modules.file.context;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName:QueryFileListContext
 * Package:com.heyqing.disk.server.modules.file.context
 * Description:
 * 查询文件列表上下文实体
 *
 * @Date:2024/1/17
 * @Author:Heyqing
 */
@Data
public class QueryFileListContext implements Serializable {
    private static final long serialVersionUID = 1148857325021409099L;

    /**
     * 父文件夹id
     */
    private Long parentId;

    /**
     *文件类型集合
     */
    private List<Integer> fileTypeArray;

    /**
     * 当前登录用户
     */
    private Long userId;

    /**
     * 文件的删除标识
     */
    private Integer delFlag;
}
