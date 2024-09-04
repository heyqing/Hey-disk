package com.heyqing.disk.storage.engine.core.context;

import lombok.Data;

import java.io.OutputStream;
import java.io.Serializable;

/**
 * ClassName:ReadFileContext
 * Package:com.heyqing.disk.storage.engine.core.context
 * Description:
 * 文件读取的上下文实体信息
 *
 * @Date:2024/9/4
 * @Author:Heyqing
 */
@Data
public class ReadFileContext implements Serializable {
    private static final long serialVersionUID = -8290057381987263335L;

    /**
     * 文件的真实存储路径
     */
    private String realPath;

    /**
     * 文件的输出流
     */
    private OutputStream outputStream;
}
