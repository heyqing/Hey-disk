package com.heyqing.disk.storage.engine.core.context;

import lombok.Data;

import java.io.InputStream;
import java.io.Serializable;

/**
 * ClassName:StoreFileContext
 * Package:com.heyqing.disk.storage.engine.core.context
 * Description:
 * 文件存储引擎存储物理文件的上下文实体
 *
 * @Date:2024/3/19
 * @Author:Heyqing
 */
@Data
public class StoreFileContext implements Serializable {
    private static final long serialVersionUID = 6770897157674343336L;

    /**
     * 上传文件的名称
     */
    private String filename;

    /**
     * 文件总大小
     */
    private Long totalSize;

    /**
     * 文件输入流信息
     */
    private InputStream inputStream;

    /**
     * 文件上传后的物理路径
     */
    private String realPath;


}
