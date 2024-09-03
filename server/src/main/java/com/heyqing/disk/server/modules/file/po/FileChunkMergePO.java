package com.heyqing.disk.server.modules.file.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * ClassName:FileChunkMergePO
 * Package:com.heyqing.disk.server.modules.file.po
 * Description:
 *
 * @Date:2024/9/2
 * @Author:Heyqing
 */
@Data
@ApiModel("文件分片合并参数实体")
public class FileChunkMergePO implements Serializable {
    private static final long serialVersionUID = -2115945754816107441L;

    @ApiModelProperty(value = "文件名称", required = true)
    @NotBlank(message = "文件名称不能为空")
    private String filename;
    @ApiModelProperty(value = "文件唯一标识", required = true)
    @NotBlank(message = "文件唯一标识不能为空")
    private String identifier;
    @ApiModelProperty(value = "文件总大小", required = true)
    @NotNull(message = "文件总大小不能为空")
    private Long totalSize;
    @ApiModelProperty(value = "文件的父文件夹Id", required = true)
    @NotBlank(message = "文件的父文件夹Id不能为空")
    private String parentId;
}
