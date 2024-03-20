package com.heyqing.disk.server.modules.file.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ClassName:FileChunkUploadVO
 * Package:com.heyqing.disk.server.modules.file.vo
 * Description:
 *
 * @Date:2024/3/20
 * @Author:Heyqing
 */
@Data
@ApiModel("文件分片上传响应实体")
public class FileChunkUploadVO implements Serializable {
    private static final long serialVersionUID = 6049872874949689883L;

    @ApiModelProperty("是否需要合并文件 0 不需要 1 需要")
    private Integer mergeFlag;

}
