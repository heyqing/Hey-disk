package com.heyqing.disk.server.modules.file.context;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName:FileSearchContext
 * Package:com.heyqing.disk.server.modules.file.context
 * Description:
 * 文件搜索上下文实体
 *
 * @Date:2024/9/12
 * @Author:Heyqing
 */
@Data
public class FileSearchContext implements Serializable {
    private static final long serialVersionUID = 4884354941642029495L;

    /**
     * 搜索关键字
     */
    private String keyword;

    /**
     * 搜索文件类型集合
     */
    private List<Integer> fileTypeArray;

    /**
     * 当前登录用户id
     */
    private Long userId;
}
