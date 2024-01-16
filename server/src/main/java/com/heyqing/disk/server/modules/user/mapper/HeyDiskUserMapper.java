package com.heyqing.disk.server.modules.user.mapper;

import com.heyqing.disk.server.modules.user.entity.HeyDiskUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Entity com.heyqing.disk.server.modules.user.entity.HeyDiskUser
 */
public interface HeyDiskUserMapper extends BaseMapper<HeyDiskUser> {

    /**
     * 通过用户名称查询用户设置的密保问题
     * @param username
     * @return
     */
    String selectQuestionByUsername(@Param("username") String username);
}




