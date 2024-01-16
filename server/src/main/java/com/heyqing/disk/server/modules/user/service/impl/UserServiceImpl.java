package com.heyqing.disk.server.modules.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heyqing.disk.cache.core.constants.CacheConstants;
import com.heyqing.disk.core.exception.HeyDiskBusinessException;
import com.heyqing.disk.core.response.ResponseCode;
import com.heyqing.disk.core.utils.IdUtil;
import com.heyqing.disk.core.utils.JWTUtil;
import com.heyqing.disk.core.utils.PasswordUtil;
import com.heyqing.disk.server.modules.file.constants.FileConstants;
import com.heyqing.disk.server.modules.file.context.CreateFolderContext;
import com.heyqing.disk.server.modules.file.entity.HeyDiskUserFile;
import com.heyqing.disk.server.modules.file.service.IUserFileService;
import com.heyqing.disk.server.modules.user.constants.UserConstants;
import com.heyqing.disk.server.modules.user.context.*;
import com.heyqing.disk.server.modules.user.converter.UserConverter;
import com.heyqing.disk.server.modules.user.entity.HeyDiskUser;
import com.heyqing.disk.server.modules.user.service.IUserService;
import com.heyqing.disk.server.modules.user.mapper.HeyDiskUserMapper;
import com.heyqing.disk.server.modules.user.vo.UserInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 *
 */
@Service(value = "userService")
public class UserServiceImpl extends ServiceImpl<HeyDiskUserMapper, HeyDiskUser> implements IUserService {

    @Autowired
    private UserConverter userConverter;
    @Autowired
    private IUserFileService iUserFileService;
    @Autowired
    private CacheManager cacheManager;


    /**
     * 用户注册业务的实现
     * 功能：
     * 1、注册用户信息
     * 2、创建新用户的根本目录信息
     * 需要实现的技术难点
     * 1、该业务时幂等的
     * 2、要保证用户名全局唯一
     * 处理方案
     * 幂等性通过数据库表对于用户名字段添加唯一字段索引，我们上游业务捕获对应的冲突异常，转化返回
     *
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

    /**
     * 用户登录业务实现
     * 1、用户登录信息校验
     * 2、生成一个具有时效性的accessToken
     * 3、将accessToken缓存起来，去实现单机登录
     *
     * @param userLoginContext
     * @return
     */
    @Override
    public String login(UserLoginContext userLoginContext) {
        checkLoginInfo(userLoginContext);
        generateAndSaveAccessToken(userLoginContext);
        return userLoginContext.getAccessToken();
    }

    /**
     * 用户退出登录
     * 1、清除用户的登录凭证缓存
     *
     * @param userId
     */
    @Override
    public void logout(Long userId) {
        try {
            Cache cache = cacheManager.getCache(CacheConstants.HEY_DISK_CACHE_NAME);
            cache.evict(UserConstants.USER_LOGIN_PREFIX + userId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new HeyDiskBusinessException("用户退出登录失败");
        }
    }

    /**
     * 用户忘记密码-校验用户名称
     *
     * @param checkUsernameContext
     * @return
     */
    @Override
    public String checkUsername(CheckUsernameContext checkUsernameContext) {
        String question = baseMapper.selectQuestionByUsername(checkUsernameContext.getUsername());
        if (StringUtils.isBlank(question)) {
            throw new HeyDiskBusinessException("没有此用户");
        }
        return question;
    }

    /**
     * 用户忘记密码-校验密保答案
     *
     * @param checkAnswerContext
     * @return
     */
    @Override
    public String checkAnswer(CheckAnswerContext checkAnswerContext) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", checkAnswerContext.getUsername());
        queryWrapper.eq("question", checkAnswerContext.getQuestion());
        queryWrapper.eq("answer", checkAnswerContext.getAnswer());
        int count = count(queryWrapper);
        if (count == 0) {
            throw new HeyDiskBusinessException("密保答案错误");
        }
        return generateCheckAnswerToken(checkAnswerContext);
    }

    /**
     * 用户忘记密码-用户重置密码
     * 1、校验token是否有效
     * 2、重置密码
     *
     * @param resetPasswordContext
     */
    @Override
    public void resetPassword(ResetPasswordContext resetPasswordContext) {
        checkForgetPasswordToken(resetPasswordContext);
        checkAndResetUserPassword(resetPasswordContext);
    }

    /**
     * 用户在线修改密码
     * 1、校验旧密码
     * 2、重置新密码
     * 3、退出当前登录
     *
     * @param changePasswordContext
     */
    @Override
    public void changePassword(ChangePasswordContext changePasswordContext) {
        checkOldPassword(changePasswordContext);
        doChangePassword(changePasswordContext);
        exitLoginStatus(changePasswordContext);
    }

    /**
     * 查询在线用户基本信息
     * 1、查询用户的基本信息实体
     * 2、查询用户的跟文件夹信息
     * 3、拼装VO对象返回
     *
     * @param userId
     * @return
     */
    @Override
    public UserInfoVO info(Long userId) {
        HeyDiskUser entity = getById(userId);
        if (Objects.isNull(entity)) {
            throw new HeyDiskBusinessException("用户信息查询失败");
        }
        HeyDiskUserFile heyDiskUserFile = getUserRootFileInfo(userId);
        if (Objects.isNull(heyDiskUserFile)){
            throw new HeyDiskBusinessException("查询用户根文件夹信息失败");
        }
        return userConverter.assembleUserInfoVO(entity, heyDiskUserFile);
    }


    /***************************************************private***************************************************/

    /**
     * 获取用户根目录信息
     *
     * @param userId
     * @return
     */
    private HeyDiskUserFile getUserRootFileInfo(Long userId) {
        return iUserFileService.getUserRootFile(userId);
    }


    /**
     * 退出用户登录状态
     *
     * @param changePasswordContext
     */
    private void exitLoginStatus(ChangePasswordContext changePasswordContext) {
        logout(changePasswordContext.getUserId());
    }

    /**
     * 修改新密码
     *
     * @param changePasswordContext
     */
    private void doChangePassword(ChangePasswordContext changePasswordContext) {
        String newPassword = changePasswordContext.getNewPassword();
        HeyDiskUser entity = changePasswordContext.getEntity();
        String salt = entity.getSalt();
        String encNewPassword = PasswordUtil.encryptPassword(salt, newPassword);
        entity.setPassword(encNewPassword);
        if (!updateById(entity)) {
            throw new HeyDiskBusinessException("修改用户密码失败");
        }
    }

    /**
     * 校验用户旧密码
     * 该步骤会查询并封装用户的实体信息到上下文信息中
     *
     * @param changePasswordContext
     */
    private void checkOldPassword(ChangePasswordContext changePasswordContext) {
        Long userId = changePasswordContext.getUserId();
        String oldPassword = changePasswordContext.getOldPassword();
        HeyDiskUser entity = getById(userId);
        if (Objects.isNull(entity)) {
            throw new HeyDiskBusinessException("旧密码错误");
        }
        changePasswordContext.setEntity(entity);
        String encOldPassword = PasswordUtil.encryptPassword(entity.getSalt(), oldPassword);
        String dbOldPassword = entity.getPassword();
        if (!Objects.equals(encOldPassword, dbOldPassword)) {
            throw new HeyDiskBusinessException("旧密码错误");
        }
    }

    /**
     * 校验用户信息并重置用户密码
     *
     * @param resetPasswordContext
     */
    private void checkAndResetUserPassword(ResetPasswordContext resetPasswordContext) {
        String username = resetPasswordContext.getUsername();
        String password = resetPasswordContext.getPassword();
        HeyDiskUser entity = getHeyDiskUserByUsername(username);
        if (Objects.isNull(entity)) {
            throw new HeyDiskBusinessException("用户信息不存在");
        }
        String newDbPassword = PasswordUtil.encryptPassword(entity.getSalt(), password);
        entity.setPassword(newDbPassword);
        entity.setUpdateTime(new Date());

        if (!updateById(entity)) {
            throw new HeyDiskBusinessException("重置用户密码失败");
        }

    }

    /**
     * 验证忘记密码的token是否有效
     *
     * @param resetPasswordContext
     */
    private void checkForgetPasswordToken(ResetPasswordContext resetPasswordContext) {
        String token = resetPasswordContext.getToken();
        Object value = JWTUtil.analyzeToken(token, UserConstants.FORGET_USERNAME);
        if (Objects.isNull(value)) {
            throw new HeyDiskBusinessException(ResponseCode.TOKEN_EXPIRE);
        }
        String tokenUsername = String.valueOf(value);
        if (!Objects.equals(tokenUsername, resetPasswordContext.getUsername())) {
            throw new HeyDiskBusinessException("token错误");
        }
    }

    /**
     * 生成用户忘记密码-校验密保答案通过的临时token
     * 时效为 5 min
     *
     * @param checkAnswerContext
     * @return
     */
    private String generateCheckAnswerToken(CheckAnswerContext checkAnswerContext) {
        String token = JWTUtil.generateToken(checkAnswerContext.getUsername(), UserConstants.FORGET_USERNAME, checkAnswerContext.getUsername(), UserConstants.FIVE_MINUTES_LONG);
        return token;
    }

    /**
     * 创建用户的更目录信息
     *
     * @param userRegisterContext
     */
    private void createUserRootFolder(UserRegisterContext userRegisterContext) {
        CreateFolderContext createFolderContext = new CreateFolderContext();
        createFolderContext.setParentId(FileConstants.TOP_PARENT_ID);
        createFolderContext.setUserId(userRegisterContext.getEntity().getUserId());
        createFolderContext.setFolderName(FileConstants.ALL_FILE_CN_STR);
        iUserFileService.createFolder(createFolderContext);
    }

    /**
     * 实现注册用户的业务
     * 需要捕获数据库的唯一索引冲突异常，来实现全局用户名称唯一
     *
     * @param userRegisterContext
     */
    private void doRegister(UserRegisterContext userRegisterContext) {
        HeyDiskUser entity = userRegisterContext.getEntity();
        if (Objects.nonNull(entity)) {
            try {
                if (!save(entity)) {
                    throw new HeyDiskBusinessException("用户注册失败");
                }
            } catch (DuplicateKeyException duplicateKeyException) {
                throw new HeyDiskBusinessException("用户名已存在");
            }
            return;
        }
        throw new HeyDiskBusinessException(ResponseCode.ERROR);
    }

    /**
     * 实体转化
     * 由上下文信息转化成用户实体，封装上下文
     *
     * @param userRegisterContext
     */
    private void assembleUserEntity(UserRegisterContext userRegisterContext) {
        HeyDiskUser entity = userConverter.userRegisterContext2HeyDiskUser(userRegisterContext);
        String salt = PasswordUtil.getSalt(),
                dbPassword = PasswordUtil.encryptPassword(salt, userRegisterContext.getPassword());
        entity.setUserId(IdUtil.get());
        entity.setSalt(salt);
        entity.setPassword(dbPassword);
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        userRegisterContext.setEntity(entity);
    }

    /**
     * 生成并保存登录之后的凭证
     *
     * @param userLoginContext
     */
    private void generateAndSaveAccessToken(UserLoginContext userLoginContext) {
        HeyDiskUser entity = userLoginContext.getEntity();

        String accessToken = JWTUtil.generateToken(entity.getUsername(), UserConstants.LOGIN_USER_ID, entity.getUserId(), UserConstants.ONE_DAY_LONG);
        Cache cache = cacheManager.getCache(CacheConstants.HEY_DISK_CACHE_NAME);
        cache.put(UserConstants.USER_LOGIN_PREFIX + entity.getUserId(), accessToken);
        userLoginContext.setAccessToken(accessToken);
    }

    /**
     * 校验用户名密码
     *
     * @param userLoginContext
     */
    private void checkLoginInfo(UserLoginContext userLoginContext) {
        String username = userLoginContext.getUsername();
        String password = userLoginContext.getPassword();

        HeyDiskUser entity = getHeyDiskUserByUsername(username);
        if (Objects.isNull(entity)) {
            throw new HeyDiskBusinessException("用户名称不存在");
        }

        String salt = entity.getSalt();
        String encPassword = PasswordUtil.encryptPassword(salt, password);
        String dbPassword = entity.getPassword();
        if (!Objects.equals(encPassword, dbPassword)) {
            throw new HeyDiskBusinessException("密码信息不正确");
        }
        userLoginContext.setEntity(entity);
    }

    /**
     * 通过用户名称获取用户实体信息
     *
     * @param username
     * @return
     */
    private HeyDiskUser getHeyDiskUserByUsername(String username) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        return getOne(queryWrapper);
    }
}




