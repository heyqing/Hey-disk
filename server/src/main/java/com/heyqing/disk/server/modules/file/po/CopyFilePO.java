package com.heyqing.disk.server.modules.file.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * ClassName:CopyFilePO
 * Package:com.heyqing.disk.server.modules.file.po
 * Description:
 *
 * @Date:2024/9/10
 * @Author:Heyqing
 */
@Data
@ApiModel("文件复制参数实体对象")
public class CopyFilePO implements Serializable {
    private static final long serialVersionUID = 5703975129507596815L;

    @ApiModelProperty("要复制的文件id集合，多个使用公用分隔符分开")
    @NotBlank(message = "请选择要复制的文件")
    private String fileIds;

    @ApiModelProperty("要复制到的目标文件夹的id")
    @NotBlank(message = "请选择要复制的目标文件夹")
    private String targetParentId;
}
