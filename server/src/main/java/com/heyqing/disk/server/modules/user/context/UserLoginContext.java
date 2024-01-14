package com.heyqing.disk.server.modules.user.context;

import com.heyqing.disk.server.modules.user.entity.HeyDiskUser;
import lombok.Data;

import java.io.Serializable;

/**
 * ClassName:UserLoginContext
 * Package:com.heyqing.disk.server.modules.user.context
 * Description:
 * 用户登录业务上下文实体对象
 *
 * @Date:2024/1/14
 * @Author:Heyqing
 */
@Data
public class UserLoginContext implements Serializable {
    private static final long serialVersionUID = -3211641216642906039L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户实体对象
     */
    private HeyDiskUser entity;

    /**
     * 登录成功之后的凭证信息
     */
    private String accessToken;
}
