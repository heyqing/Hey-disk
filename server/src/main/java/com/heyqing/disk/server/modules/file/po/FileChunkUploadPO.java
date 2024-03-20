package com.heyqing.disk.server.modules.file.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * ClassName:FileChunkUploadPO
 * Package:com.heyqing.disk.server.modules.file.po
 * Description:
 *
 * @Date:2024/3/20
 * @Author:Heyqing
 */
@Data
@ApiModel("文件分片上传参数实体")
public class FileChunkUploadPO implements Serializable {
    private static final long serialVersionUID = 8769074684660472563L;

    @ApiModelProperty(value = "文件名称", required = true)
    @NotBlank(message = "文件名称不能为空")
    private String filename;

    @ApiModelProperty(value = "文件唯一标识", required = true)
    @NotBlank(message = "文件唯一标识不能为空")
    private String identifier;

    @ApiModelProperty(value = "总体的分片数", required = true)
    @NotNull(message = "总体的分片数不能为空")
    private Integer totalChunks;

    @ApiModelProperty(value = "当前分片的下标", required = true)
    @NotNull(message = "当前分片的下标不能为空")
    private Integer chunkNumber;

    @ApiModelProperty(value = "当前分片的大小", required = true)
    @NotNull(message = "当前分片的大小不能为空")
    private Long currentChunkSize;

    @ApiModelProperty(value = "分片文件实体", required = true)
    @NotNull(message = "分片文件实体不能为空")
    private Long totalSize;

}
