package com.heyqing.disk.server.modules.user.service;

import com.heyqing.disk.server.modules.user.context.UserRegisterContext;
import com.heyqing.disk.server.modules.user.entity.HeyDiskUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface IUserService extends IService<HeyDiskUser> {

    /**
     * 用户注册业务
     * @param userRegisterContext
     * @return
     */
    Long register(UserRegisterContext userRegisterContext);
}
