package com.heyqing.disk.server.modules.user.context;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName:CheckUsernameContext
 * Package:com.heyqing.disk.server.modules.user.context
 * Description:
 *
 * @Date:2024/1/16
 * @Author:Heyqing
 */
@Data
public class CheckUsernameContext implements Serializable {

    private static final long serialVersionUID = 7324015252947206379L;

    /**
     * 用户名称
     */
    private String username;
}
