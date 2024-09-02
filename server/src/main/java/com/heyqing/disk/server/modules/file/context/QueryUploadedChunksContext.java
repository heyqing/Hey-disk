package com.heyqing.disk.server.modules.file.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * ClassName:QueryUploadedChunksContext
 * Package:com.heyqing.disk.server.modules.file.context
 * Description:
 * 查询用户已上传的分片列表的上下文实体
 *
 * @Date:2024/9/2
 * @Author:Heyqing
 */
@Data
public class QueryUploadedChunksContext implements Serializable {
    private static final long serialVersionUID = -4806880140421184266L;

    /**
     * 文件唯一标识
     */
    private String identifier;

    /**
     * 当前登录的用户id
     */
    private Long userId;
}
