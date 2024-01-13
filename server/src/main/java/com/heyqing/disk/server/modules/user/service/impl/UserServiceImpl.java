package com.heyqing.disk.server.modules.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heyqing.disk.core.exception.HeyDiskBusinessException;
import com.heyqing.disk.core.response.ResponseCode;
import com.heyqing.disk.core.utils.IdUtil;
import com.heyqing.disk.core.utils.PasswordUtil;
import com.heyqing.disk.server.modules.file.constants.FileConstants;
import com.heyqing.disk.server.modules.file.context.CreateFolderContext;
import com.heyqing.disk.server.modules.file.service.IUserFileService;
import com.heyqing.disk.server.modules.user.context.UserRegisterContext;
import com.heyqing.disk.server.modules.user.converter.UserConverter;
import com.heyqing.disk.server.modules.user.entity.HeyDiskUser;
import com.heyqing.disk.server.modules.user.service.IUserService;
import com.heyqing.disk.server.modules.user.mapper.HeyDiskUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    /***************************************************private***************************************************/

    /**
     * 创建用户的更目录信息
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
     * @param userRegisterContext
     */
    private void doRegister(UserRegisterContext userRegisterContext) {
        HeyDiskUser entity = userRegisterContext.getEntity();
        if (Objects.nonNull(entity)){
            try {
                if (!save(entity)){
                    throw new HeyDiskBusinessException("用户注册失败");
                }
            }catch (DuplicateKeyException duplicateKeyException){
                throw new HeyDiskBusinessException("用户名已存在");
            }
            return;
        }
        throw new HeyDiskBusinessException(ResponseCode.ERROR);
    }

    /**
     * 实体转化
     * 由上下文信息转化成用户实体，封装上下文
     * @param userRegisterContext
     */
    private void assembleUserEntity(UserRegisterContext userRegisterContext) {
        HeyDiskUser entity = userConverter.userRegisterContext2HeyDiskUser(userRegisterContext);
        String salt = PasswordUtil.getSalt(),
                dbPassword = PasswordUtil.encryptPassword(salt,userRegisterContext.getPassword());
        entity.setUserId(IdUtil.get());
        entity.setSalt(salt);
        entity.setPassword(dbPassword);
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        userRegisterContext.setEntity(entity);
    }
}




