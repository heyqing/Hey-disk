package com.heyqing.disk.server.modules.file.context;

import lombok.Data;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * ClassName:FileDownloadContext
 * Package:com.heyqing.disk.server.modules.file.context
 * Description:
 * 文件下载的上下文实体对象
 *
 * @Date:2024/9/4
 * @Author:Heyqing
 */
@Data
public class FileDownloadContext implements Serializable {
    private static final long serialVersionUID = 7970891995058407340L;

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
