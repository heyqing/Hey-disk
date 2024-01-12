package com.heyqing.disk.server.modules.user.context;

import com.heyqing.disk.server.modules.user.entity.HeyDiskUser;
import lombok.Data;
import java.io.Serializable;

/**
 * ClassName:UserRegisterContext
 * Package:com.heyqing.disk.server.modules.user.context
 * Description:
 *          用户注册业务的上下文实体对象
 * @Date:2024/1/12
 * @Author:Heyqing
 */
@Data
public class UserRegisterContext implements Serializable {
    private static final long serialVersionUID = 4031517037346817771L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 密保问题
     */
    private String question;

    /**
     * 密保答案
     */
    private String answer;

    /**
     *用户实体对象
     */
    private HeyDiskUser entity;

}
