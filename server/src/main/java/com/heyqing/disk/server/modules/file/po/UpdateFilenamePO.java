package com.heyqing.disk.server.modules.file.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * ClassName:UpdateFilenamePO
 * Package:com.heyqing.disk.server.modules.file.po
 * Description:
 *
 * @Date:2024/1/17
 * @Author:Heyqing
 */
@Data
@ApiModel(value = "重命名文件夹参数实体")
public class UpdateFilenamePO implements Serializable {
    private static final long serialVersionUID = -8246802602105634827L;

    @ApiModelProperty(value = "更新的文件id",required = true)
    @NotBlank(message = "更新的文件id不能为空")
    private String fileId;

    @ApiModelProperty(value = "新的文件名称",required = true)
    @NotBlank(message = "新的文件名称不能为空")
    private String newFilename;
}
