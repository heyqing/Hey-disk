package com.heyqing.disk.server.modules.file.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * ClassName:CreateFolderPO
 * Package:com.heyqing.disk.server.modules.file.po
 * Description:
 *
 * @Date:2024/1/17
 * @Author:Heyqing
 */
@Data
@ApiModel(value = "创建文件夹参数实体")
public class CreateFolderPO implements Serializable {
    private static final long serialVersionUID = -694359599905572763L;

    @ApiModelProperty(value = "加密的父文件夹id",required = true)
    @NotBlank(message = "父文件夹id不能为空")
    private String parentId;

    @ApiModelProperty(value = "文件夹名称",required = true)
    @NotBlank(message = "文件夹名称不能为空")
    private String folderName;

}
