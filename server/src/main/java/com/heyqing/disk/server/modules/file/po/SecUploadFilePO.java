package com.heyqing.disk.server.modules.file.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * ClassName:SecUploadFilePO
 * Package:com.heyqing.disk.server.modules.file.po
 * Description:
 *
 * @Date:2024/2/28
 * @Author:Heyqing
 */
@Data
@ApiModel("文件秒传参数实体")
public class SecUploadFilePO implements Serializable {
    private static final long serialVersionUID = 8128485106722765381L;

    @ApiModelProperty(value = "文件夹id", required = true)
    @NotBlank(message = "文件夹id不能为空")

    private String parentId;
    @ApiModelProperty(value = "文件名称", required = true)
    @NotBlank(message = "文件名称不能为空")
    private String filename;

    @ApiModelProperty(value = "文件唯一标识", required = true)
    @NotBlank(message = "文件唯一标识不能为空")
    private String identifier;
}
