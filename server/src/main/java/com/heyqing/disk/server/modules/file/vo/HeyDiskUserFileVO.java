package com.heyqing.disk.server.modules.file.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.heyqing.disk.web.serializer.IdEncryptSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName:HeyDiskUserFileVO
 * Package:com.heyqing.disk.server.modules.file.vo
 * Description:
 *用户查询文件列表响应实体
 * @Date:2024/1/17
 * @Author:Heyqing
 */
@Data
@ApiModel(value = "文件列表响应实体")
public class HeyDiskUserFileVO implements Serializable {

    private static final long serialVersionUID = 2633068457405332294L;

    @ApiModelProperty(value = "文件id")
    @JsonSerialize(using = IdEncryptSerializer.class)
    private Long fileId;

    @ApiModelProperty(value = "父文件夹id")
    @JsonSerialize(using = IdEncryptSerializer.class)
    private Long parentId;

    @ApiModelProperty(value = "文件名称")
    private String filename;

    @ApiModelProperty(value = "文件大小描述")
    private String fileSizeDesc;

    @ApiModelProperty(value = "文件夹标识 0否1是")
    private Integer folderFlag;

    @ApiModelProperty(value = "文件类型 1普通文件2压缩文件3excel4word5pdf6txt7图片8音频9视频10ppt11源码文件12csv")
    private Integer fileType;

    @ApiModelProperty(value = "文件更新时间")
    private Date updateTime;


}
