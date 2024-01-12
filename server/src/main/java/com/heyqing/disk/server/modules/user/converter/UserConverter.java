package com.heyqing.disk.server.modules.user.converter;

import com.heyqing.disk.server.modules.user.context.UserRegisterContext;
import com.heyqing.disk.server.modules.user.po.UserRegisterPO;
import org.mapstruct.Mapper;

/**
 * ClassName:UserConverter
 * Package:com.heyqing.disk.server.modules.user.converter
 * Description:
 *          用户模块实体转化实体类
 * @Date:2024/1/12
 * @Author:Heyqing
 */
@Mapper(componentModel = "spring")
public interface UserConverter {

    /**
     * UserRegisterPO 转化成 UserRegisterContext
     * @param userRegisterPO
     * @return
     */
    UserRegisterContext userRegisterPO2UserRegisterContext(UserRegisterPO userRegisterPO);

}
