package com.heyqing.disk.server.modules.user.service;

import com.heyqing.disk.server.modules.user.context.*;
import com.heyqing.disk.server.modules.user.entity.HeyDiskUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.heyqing.disk.server.modules.user.vo.UserInfoVO;


public interface IUserService extends IService<HeyDiskUser> {

    /**
     * 用户注册业务
     *
     * @param userRegisterContext
     * @return
     */
    Long register(UserRegisterContext userRegisterContext);

    /**
     * 用户登录业务
     *
     * @param userLoginContext
     * @return
     */
    String login(UserLoginContext userLoginContext);

    /**
     * 用户退出登录业务
     *
     * @param userId
     */
    void logout(Long userId);

    /**
     * 用户忘记密码-校验用户名业务
     *
     * @param checkUsernameContext
     * @return
     */
    String checkUsername(CheckUsernameContext checkUsernameContext);

    /**
     * 用户忘记密码-校验密保答案业务
     *
     * @param checkAnswerContext
     * @return
     */
    String checkAnswer(CheckAnswerContext checkAnswerContext);

    /**
     * 用户忘记密码-重置用户密码业务
     *
     * @param resetPasswordContext
     */
    void resetPassword(ResetPasswordContext resetPasswordContext);

    /**
     * 用户在线修改密码业务
     *
     * @param changePasswordContext
     */
    void changePassword(ChangePasswordContext changePasswordContext);

    /**
     * 查询在线用户的基本信息业务
     *
     * @param userId
     * @return
     */
    UserInfoVO info(Long userId);
}
