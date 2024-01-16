package com.heyqing.disk.server.modules.user.context;

import com.heyqing.disk.server.modules.user.entity.HeyDiskUser;
import lombok.Data;

import java.io.Serializable;

/**
 * ClassName:ChangePasswordContext
 * Package:com.heyqing.disk.server.modules.user.context
 * Description:
 *
 * @Date:2024/1/16
 * @Author:Heyqing
 */
@Data
public class ChangePasswordContext implements Serializable {
    private static final long serialVersionUID = 6608505737957528802L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 旧密码
     */
    private String oldPassword;

    /**
     * 新密码
     */
    private String newPassword;

    /**
     * 当前登录用户的实体信息
     */
    private HeyDiskUser entity;
}
