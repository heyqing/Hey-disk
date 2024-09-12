package com.heyqing.disk.server.modules.file.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.heyqing.disk.web.serializer.Date2StringSerializer;
import com.heyqing.disk.web.serializer.IdEncryptSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName:FileSearchResultVO
 * Package:com.heyqing.disk.server.modules.file.vo
 * Description:
 *
 * @Date:2024/9/12
 * @Author:Heyqing
 */
@Data
@ApiModel("文件搜索响应实体")
public class FileSearchResultVO implements Serializable {
    private static final long serialVersionUID = -7802484817416969924L;

    @ApiModelProperty(value = "文件id")
    @JsonSerialize(using = IdEncryptSerializer.class)
    private Long fileId;

    @ApiModelProperty(value = "父文件夹id")
    @JsonSerialize(using = IdEncryptSerializer.class)
    private Long parentId;

    @ApiModelProperty(value = "父文件夹名称")
    private String parentFilename;
    @ApiModelProperty(value = "文件名称")
    private String filename;

    @ApiModelProperty(value = "文件大小描述")
    private String fileSizeDesc;

    @ApiModelProperty(value = "文件夹标识 0否1是")
    private Integer folderFlag;

    @ApiModelProperty(value = "文件类型 1普通文件2压缩文件3excel4word5pdf6txt7图片8音频9视频10ppt11源码文件12csv")
    private Integer fileType;

    @ApiModelProperty(value = "文件更新时间")
    @JsonSerialize(using = Date2StringSerializer.class)
    private Date updateTime;
}
