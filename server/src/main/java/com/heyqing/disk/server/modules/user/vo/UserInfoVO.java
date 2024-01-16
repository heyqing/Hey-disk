package com.heyqing.disk.server.modules.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.heyqing.disk.web.serializer.IdEncryptSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ClassName:UserInfoVO
 * Package:com.heyqing.disk.server.modules.user.vo
 * Description:
 *
 * @Date:2024/1/16
 * @Author:Heyqing
 */
@Data
@ApiModel(value = "用户基本信息实体")
public class UserInfoVO implements Serializable {
    private static final long serialVersionUID = -6666934581558119360L;

    @ApiModelProperty("用户名称")
    private String username;

    @ApiModelProperty("用户根目录加密id")
    @JsonSerialize(using = IdEncryptSerializer.class)
    private Long rootFileId;

    @ApiModelProperty("用户根目录名称")
    private String rootFilename;
}
