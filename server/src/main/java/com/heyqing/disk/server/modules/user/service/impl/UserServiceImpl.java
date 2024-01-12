package com.heyqing.disk.server.modules.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heyqing.disk.server.modules.user.context.UserRegisterContext;
import com.heyqing.disk.server.modules.user.converter.UserConverter;
import com.heyqing.disk.server.modules.user.entity.HeyDiskUser;
import com.heyqing.disk.server.modules.user.service.IUserService;
import com.heyqing.disk.server.modules.user.mapper.HeyDiskUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service(value = "userService")
public class UserServiceImpl extends ServiceImpl<HeyDiskUserMapper, HeyDiskUser> implements IUserService {

    @Autowired
    private UserConverter userConverter;

    /**
     * 用户注册业务的实现
     * 功能：
     *  1、注册用户信息
     *  2、创建新用户的根本目录信息
     * 需要实现的技术难点
     *  1、该业务时幂等的
     *  2、要保证用户名全局唯一
     * 处理方案
     *  幂等性通过数据库表对于用户名字段添加唯一字段索引，我们上游业务捕获对应的冲突异常，转化返回
     * @param userRegisterContext
     * @return
     */
    @Override
    public Long register(UserRegisterContext userRegisterContext) {

        assembleUserEntity(userRegisterContext);
        doRegister(userRegisterContext);
        createUserRootFolder(userRegisterContext);
        return userRegisterContext.getEntity().getUserId();
    }

    private void createUserRootFolder(UserRegisterContext userRegisterContext) {

    }

    private void doRegister(UserRegisterContext userRegisterContext) {

    }

    private void assembleUserEntity(UserRegisterContext userRegisterContext) {

    }
}




