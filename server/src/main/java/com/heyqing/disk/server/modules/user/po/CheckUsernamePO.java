package com.heyqing.disk.server.modules.user.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * ClassName:CheckUsernamePO
 * Package:com.heyqing.disk.server.modules.user.po
 * Description:
 *校验用户名PO对象
 * @Date:2024/1/16
 * @Author:Heyqing
 */
@Data
@ApiModel(value = "用户忘记密码-校验用户名参数")
public class CheckUsernamePO implements Serializable {

    private static final long serialVersionUID = 2282101371756783771L;

    @ApiModelProperty(value = "用户名",required = true)
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[0-9A-Za-z]{6,16}$",message = "请输入6-16位只包含数字和字母的用户名")
    private String username;
}
