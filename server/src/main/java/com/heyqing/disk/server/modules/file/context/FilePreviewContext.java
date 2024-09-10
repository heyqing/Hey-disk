package com.heyqing.disk.server.modules.file.context;

import lombok.Data;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * ClassName:FilePreviewContext
 * Package:com.heyqing.disk.server.modules.file.context
 * Description:
 * 文件预览的上下文实体
 *
 * @Date:2024/9/9
 * @Author:Heyqing
 */
@Data
public class FilePreviewContext implements Serializable {
    private static final long serialVersionUID = 1857093412371156440L;

    /**
     * 文件id
     */
    private Long fileId;

    /**
     * 请求响应对象
     */
    private HttpServletResponse response;

    /**
     * 当前登录用户的id
     */
    private Long userId;
}
