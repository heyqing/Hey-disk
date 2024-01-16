package com.heyqing.disk.server.modules.user.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * ClassName:ResetPasswordPO
 * Package:com.heyqing.disk.server.modules.user.po
 * Description:
 *重置用户密码参数PO对象
 * @Date:2024/1/16
 * @Author:Heyqing
 */
@Data
@ApiModel(value = "用户忘记密码-重置用户密码参数")
public class ResetPasswordPO implements Serializable {
    private static final long serialVersionUID = -2993817147691886578L;

    @ApiModelProperty(value = "用户名",required = true)
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[0-9A-Za-z]{6,16}$",message = "请输入6-16位只包含数字和字母的用户名")
    private String username;

    @ApiModelProperty(value = "密码",required = true)
    @NotBlank(message = "密码不能为空")
    @Length(min = 8,max = 16,message = "请输入8-16位的密码")
    private String password;

    @ApiModelProperty(value = "提交重置密码的token")
    @NotBlank(message = "token不能为空")
    private String token;
}
