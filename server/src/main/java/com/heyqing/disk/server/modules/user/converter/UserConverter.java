package com.heyqing.disk.server.modules.user.converter;

import com.heyqing.disk.server.modules.file.entity.HeyDiskUserFile;
import com.heyqing.disk.server.modules.user.context.*;
import com.heyqing.disk.server.modules.user.entity.HeyDiskUser;
import com.heyqing.disk.server.modules.user.po.*;
import com.heyqing.disk.server.modules.user.vo.UserInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * ClassName:UserConverter
 * Package:com.heyqing.disk.server.modules.user.converter
 * Description:
 * 用户模块实体转化实体类
 *
 * @Date:2024/1/12
 * @Author:Heyqing
 */
@Mapper(componentModel = "spring")
public interface UserConverter {

    /**
     * UserRegisterPO 转化成 UserRegisterContext
     *
     * @param userRegisterPO
     * @return
     */
    UserRegisterContext userRegisterPO2UserRegisterContext(UserRegisterPO userRegisterPO);

    /**
     * userRegisterContext转化HeyDiskUser
     *
     * @param userRegisterContext
     * @return
     */
    @Mapping(target = "password", ignore = true)
    HeyDiskUser userRegisterContext2HeyDiskUser(UserRegisterContext userRegisterContext);

    /**
     * UserLoginPO转化UserLoginContext
     *
     * @param userLoginPO
     * @return
     */
    UserLoginContext userLoginPO2UserLoginContext(UserLoginPO userLoginPO);

    /**
     * CheckUsernamePO转化CheckUsernameContext
     *
     * @param checkUsernamePO
     * @return
     */
    CheckUsernameContext checkUsernamePO2CheckUsernameContext(CheckUsernamePO checkUsernamePO);

    /**
     * CheckAnswerPO转化CheckAnswerContext
     *
     * @param checkAnswerPO
     * @return
     */
    CheckAnswerContext checkAnswerPO2CheckAnswerContext(CheckAnswerPO checkAnswerPO);

    /**
     * ResetPasswordPO转化ResetPasswordContext
     *
     * @param resetPasswordPO
     * @return
     */
    ResetPasswordContext resetPasswordPO2ResetPasswordContext(ResetPasswordPO resetPasswordPO);

    /**
     * ChangePasswordPO转化ChangePasswordContext
     *
     * @param changePasswordPO
     * @return
     */
    ChangePasswordContext changePasswordPO2ChangePasswordContext(ChangePasswordPO changePasswordPO);

    /**
     * 拼装用户基本信息返回实体
     *
     * @param heyDiskUser
     * @param heyDiskUserFile
     * @return
     */
    @Mapping(source = "heyDiskUser.username", target = "username")
    @Mapping(source = "heyDiskUserFile.fileId", target = "rootFileId")
    @Mapping(source = "heyDiskUserFile.filename", target = "rootFilename")
    UserInfoVO assembleUserInfoVO(HeyDiskUser heyDiskUser, HeyDiskUserFile heyDiskUserFile);

}
