package com.heyqing.disk.server.modules.user.context;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName:ResetPasswordContext
 * Package:com.heyqing.disk.server.modules.user.context
 * Description:
 * 重置用户密码上下文信息实体
 *
 * @Date:2024/1/16
 * @Author:Heyqing
 */
@Data
public class ResetPasswordContext implements Serializable {
    private static final long serialVersionUID = -7238415009730465520L;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 用户新密码
     */
    private String password;

    /**
     * 重置密码的token信息
     */
    private String token;
}
