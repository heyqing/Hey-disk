package com.heyqing.disk.storage.engine.core.context;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName:MergeFileContext
 * Package:com.heyqing.disk.storage.engine.core.context
 * Description:
 * 合并上下文对象
 *
 * @Date:2024/9/3
 * @Author:Heyqing
 */
@Data
public class MergeFileContext implements Serializable {
    private static final long serialVersionUID = -7740511644257805360L;

    /**
     * 文件名称
     */
    private String filename;
    /**
     * 文件唯一标识
     */
    private String identifier;
    /**
     * 当前登录用户Id
     */
    private Long userId;
    /**
     * 文件分片的真实存储物理路径
     */
    private List<String> realPathList;
    /**
     * 文件合并后的真实物理存储路径
     */
    private String realPath;
}
