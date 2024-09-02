package com.heyqing.disk.server.modules.file.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * ClassName:QueryUploadedChunksPO
 * Package:com.heyqing.disk.server.modules.file.po
 * Description:
 *
 * @Date:2024/9/2
 * @Author:Heyqing
 */
@Data
@ApiModel("查询用户已上传分片的参数实体")
public class QueryUploadedChunksPO implements Serializable {
    private static final long serialVersionUID = 6965064992586833630L;

    @ApiModelProperty(value = "文件的唯一标识", required = true)
    @NotBlank(message = "文件唯一标识不能为空")
    private String identifier;
}
