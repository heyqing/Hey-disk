package com.heyqing.disk.server.modules.file.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName:UploadedChunksVO
 * Package:com.heyqing.disk.server.modules.file.vo
 * Description:
 *
 * @Date:2024/9/2
 * @Author:Heyqing
 */
@Data
@ApiModel("查询用户已上传的文件列表返回实体")
public class UploadedChunksVO implements Serializable {
    private static final long serialVersionUID = -2849943589974470174L;

    @ApiModelProperty("已上传的分片编号列表")
    private List<Integer> uploadedChunks;
}
