package com.heyqing.disk.server.modules.file.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * ClassName:FileUploadPO
 * Package:com.heyqing.disk.server.modules.file.po
 * Description:
 *
 * @Date:2024/3/3
 * @Author:Heyqing
 */
@Data
@ApiModel(value = "单文件上传参数实体对象")
public class FileUploadPO implements Serializable {
    private static final long serialVersionUID = -6123029553006296036L;

    @ApiModelProperty(value = "文件名称",required = true)
    @NotBlank(message = "文件名称不能为空")
    private String filename;

    @ApiModelProperty(value = "文件唯一标识",required = true)
    @NotBlank(message = "文件唯一标识不能为空")
    private String identifier;

    @ApiModelProperty(value = "文件总大小",required = true)
    @NotNull(message = "文件总大小不能为空")
    private Long totalSize;

    @ApiModelProperty(value = "文件的父文件夹id",required = true)
    @NotBlank(message = "文件的父文件夹id不能为空")
    private String parentId;

    @ApiModelProperty(value = "文件实体",required = true)
    @NotNull(message = "文件实体不能为空")
    private MultipartFile file;
}
