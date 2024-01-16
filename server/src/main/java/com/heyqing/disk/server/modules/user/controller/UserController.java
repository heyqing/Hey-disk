package com.heyqing.disk.server.modules.user.controller;

import com.heyqing.disk.core.response.Result;
import com.heyqing.disk.core.utils.IdUtil;
import com.heyqing.disk.server.common.annotation.LoginIgnore;
import com.heyqing.disk.server.common.utils.UserIdUtil;
import com.heyqing.disk.server.modules.user.context.*;
import com.heyqing.disk.server.modules.user.converter.UserConverter;
import com.heyqing.disk.server.modules.user.po.*;
import com.heyqing.disk.server.modules.user.service.IUserService;
import com.heyqing.disk.server.modules.user.vo.UserInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName:UserController
 * Package:com.heyqing.disk.server.modules.user.controller
 * Description:
 *          用户模块的控制器实体
 * @Date:2024/1/12
 * @Author:Heyqing
 */
@RestController
@RequestMapping("user")
@Api(tags = "用户模块")
public class UserController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private UserConverter userConverter;

    @ApiOperation(
            value = "用户注册接口",
            notes = "该接口提供了用户注册的功能，实现了幂等性注册的逻辑，可以放心多并发调用|",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @LoginIgnore
    @PostMapping("register")
    public Result register(@Validated @RequestBody UserRegisterPO userRegisterPO){
        UserRegisterContext userRegisterContext = userConverter.userRegisterPO2UserRegisterContext(userRegisterPO);
        Long userId = iUserService.register(userRegisterContext);
        return Result.data(IdUtil.encrypt(userId));
    }

    @ApiOperation(
            value = "用户登录接口",
            notes = "该接口提供了用户登录的功能，成功登录之后会返回具有时效性的accessToken供后续服务使用",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @LoginIgnore
    @PostMapping("login")
    public Result login(@Validated @RequestBody UserLoginPO userLoginPO){
        UserLoginContext userLoginContext = userConverter.userLoginPO2UserLoginContext(userLoginPO);
        String accessToken = iUserService.login(userLoginContext);
        return Result.data(accessToken);
    }

    @ApiOperation(
            value = "用户登出接口",
            notes = "该接口提供了用户登出的功能",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @PostMapping("logout")
    public Result logout(){
        iUserService.logout(UserIdUtil.get());
        return Result.success();
    }

    @ApiOperation(
            value = "用户忘记密码-校验用户名",
            notes = "该接口提供了用户忘记密码-校验用户名的功能",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @LoginIgnore
    @PostMapping("username/check")
    public Result checkUsername(@Validated @RequestBody CheckUsernamePO checkUsernamePO){
        CheckUsernameContext checkUsernameContext = userConverter.checkUsernamePO2CheckUsernameContext(checkUsernamePO);
        String question = iUserService.checkUsername(checkUsernameContext);
        return Result.data(question);
    }

    @ApiOperation(
            value = "用户忘记密码-校验密保答案",
            notes = "该接口提供了用户忘记密码-校验密保答案的功能",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @LoginIgnore
    @PostMapping("answer/check")
    public Result checkAnswer(@Validated @RequestBody CheckAnswerPO checkAnswerPO){
        CheckAnswerContext checkAnswerContext = userConverter.checkAnswerPO2CheckAnswerContext(checkAnswerPO);
        String token = iUserService.checkAnswer(checkAnswerContext);
        return Result.data(token);
    }

    @ApiOperation(
            value = "用户忘记密码-重置用户密码",
            notes = "该接口提供了用户忘记密码-重置用户密码的功能",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @LoginIgnore
    @PostMapping("password/reset")
    public Result resetPassword(@Validated @RequestBody ResetPasswordPO resetPasswordPO){
        ResetPasswordContext resetPasswordContext = userConverter.resetPasswordPO2ResetPasswordContext(resetPasswordPO);
        iUserService.resetPassword(resetPasswordContext);
        return Result.success();
    }

    @ApiOperation(
            value = "用户在线修改密码",
            notes = "该接口提供了用户在线修改密码的功能",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @PostMapping("password/change")
    public Result changePassword(@Validated @RequestBody ChangePasswordPO changePasswordPO){
        ChangePasswordContext changePasswordContext = userConverter.changePasswordPO2ChangePasswordContext(changePasswordPO);
        changePasswordContext.setUserId(UserIdUtil.get());
        iUserService.changePassword(changePasswordContext);
        return Result.success();
    }

    @ApiOperation(
            value = "查询登录用户的基本信息",
            notes = "该接口提供了查询登录用户的基本信息的功能",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @GetMapping("/")
    public Result<UserInfoVO> info(){
        UserInfoVO userInfoVO = iUserService.info(UserIdUtil.get());
        return Result.data(userInfoVO);
    }
}
