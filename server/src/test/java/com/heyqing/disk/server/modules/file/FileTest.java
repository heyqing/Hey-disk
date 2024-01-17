package com.heyqing.disk.server.modules.file;

import cn.hutool.core.lang.Assert;
import com.heyqing.disk.core.exception.HeyDiskBusinessException;
import com.heyqing.disk.server.HeyDiskServerLauncher;
import com.heyqing.disk.server.modules.file.context.CreateFolderContext;
import com.heyqing.disk.server.modules.file.context.DeleteFileContext;
import com.heyqing.disk.server.modules.file.context.QueryFileListContext;
import com.heyqing.disk.server.modules.file.context.UpdateFilenameContext;
import com.heyqing.disk.server.modules.file.enums.DelFlagEnum;
import com.heyqing.disk.server.modules.file.service.IUserFileService;
import com.heyqing.disk.server.modules.file.vo.HeyDiskUserFileVO;
import com.heyqing.disk.server.modules.user.context.UserLoginContext;
import com.heyqing.disk.server.modules.user.context.UserRegisterContext;
import com.heyqing.disk.server.modules.user.service.IUserService;
import com.heyqing.disk.server.modules.user.vo.UserInfoVO;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * ClassName:FileTest
 * Package:com.heyqing.disk.server.modules.file
 * Description:
 * 文件模块的单元测试类
 *
 * @Date:2024/1/17
 * @Author:Heyqing
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = HeyDiskServerLauncher.class)
@Transactional
public class FileTest {

    @Autowired
    private IUserFileService iUserFileService;
    @Autowired
    private IUserService iUserService;


    /**
     * 测试用户查询文件列表成功
     */
    @Test
    public void testQueryUserFileListSuccess() {
        Long userId = register();
        UserInfoVO userInfoVO = info(userId);

        QueryFileListContext queryFileListContext = new QueryFileListContext();
        queryFileListContext.setParentId(userInfoVO.getRootFileId());
        queryFileListContext.setUserId(userId);
        queryFileListContext.setFileTypeArray(null);
        queryFileListContext.setDelFlag(DelFlagEnum.NO.getCode());

        List<HeyDiskUserFileVO> result = iUserFileService.getFileList(queryFileListContext);
        Assert.isTrue(CollectionUtils.isEmpty(result));
    }

    /**
     * 测试创建文件夹成功
     */
    @Test
    public void testCreateFolderSuccess() {
        Long userId = register();
        UserInfoVO userInfoVO = info(userId);

        CreateFolderContext createFolderContext = new CreateFolderContext();
        createFolderContext.setFolderName("folder-name");
        createFolderContext.setParentId(userInfoVO.getRootFileId());
        createFolderContext.setUserId(userId);

        Long fileId = iUserFileService.createFolder(createFolderContext);
        Assert.notNull(fileId);
    }

    /**
     * 测试文件夹重命名-失败-fileId
     */
    @Test(expected = HeyDiskBusinessException.class)
    public void testUpdateFilenameFailByWrongFileId(){
        Long userId = register();
        UserInfoVO userInfoVO = info(userId);

        CreateFolderContext createFolderContext = new CreateFolderContext();
        createFolderContext.setFolderName("folder-name");
        createFolderContext.setParentId(userInfoVO.getRootFileId());
        createFolderContext.setUserId(userId);

        Long fileId = iUserFileService.createFolder(createFolderContext);
        Assert.notNull(fileId);


        UpdateFilenameContext updateFilenameContext = new UpdateFilenameContext();
        updateFilenameContext.setFileId(fileId + 1L);
        updateFilenameContext.setUserId(userId);
        updateFilenameContext.setNewFilename("folder-name-new");

        iUserFileService.updateFilename(updateFilenameContext);
    }

    /**
     * 测试文件夹重命名-失败-userId
     */
    @Test(expected = HeyDiskBusinessException.class)
    public void testUpdateFilenameFailByWrongUserId(){
        Long userId = register();
        UserInfoVO userInfoVO = info(userId);

        CreateFolderContext createFolderContext = new CreateFolderContext();
        createFolderContext.setFolderName("folder-name");
        createFolderContext.setParentId(userInfoVO.getRootFileId());
        createFolderContext.setUserId(userId);

        Long fileId = iUserFileService.createFolder(createFolderContext);
        Assert.notNull(fileId);


        UpdateFilenameContext updateFilenameContext = new UpdateFilenameContext();
        updateFilenameContext.setFileId(fileId);
        updateFilenameContext.setUserId(userId + 1L);
        updateFilenameContext.setNewFilename("folder-name-new");

        iUserFileService.updateFilename(updateFilenameContext);
    }

    /**
     * 测试文件重命名-失败-重复
     */
    @Test(expected = HeyDiskBusinessException.class)
    public void testUpdateFilenameFailByWrongFilename(){
        Long userId = register();
        UserInfoVO userInfoVO = info(userId);

        CreateFolderContext createFolderContext = new CreateFolderContext();
        createFolderContext.setFolderName("folder-name");
        createFolderContext.setParentId(userInfoVO.getRootFileId());
        createFolderContext.setUserId(userId);

        Long fileId = iUserFileService.createFolder(createFolderContext);
        Assert.notNull(fileId);


        UpdateFilenameContext updateFilenameContext = new UpdateFilenameContext();
        updateFilenameContext.setFileId(fileId);
        updateFilenameContext.setUserId(userId);
        updateFilenameContext.setNewFilename("folder-name");

        iUserFileService.updateFilename(updateFilenameContext);

    }

    /**
     * 测试文件夹重命名-失败-与兄弟文件夹重复
     */
    @Test(expected = HeyDiskBusinessException.class)
    public void testUpdateFilenameFailByFilenameUnAvailable(){
        Long userId = register();
        UserInfoVO userInfoVO = info(userId);

        CreateFolderContext createFolderContext = new CreateFolderContext();
        createFolderContext.setFolderName("folder-name-1");
        createFolderContext.setParentId(userInfoVO.getRootFileId());
        createFolderContext.setUserId(userId);

        Long fileId = iUserFileService.createFolder(createFolderContext);
        Assert.notNull(fileId);

        createFolderContext.setFolderName("folder-name-2");
        createFolderContext.setParentId(userInfoVO.getRootFileId());
        createFolderContext.setUserId(userId);

        fileId = iUserFileService.createFolder(createFolderContext);
        Assert.notNull(fileId);


        UpdateFilenameContext updateFilenameContext = new UpdateFilenameContext();
        updateFilenameContext.setFileId(fileId + 1L);
        updateFilenameContext.setUserId(userId);
        updateFilenameContext.setNewFilename("folder-name-1");

        iUserFileService.updateFilename(updateFilenameContext);
    }

    /**
     * 测试文件夹重命名-成功
     */
    @Test
    public void testUpdateFilenameSuccess(){
        Long userId = register();
        UserInfoVO userInfoVO = info(userId);

        CreateFolderContext createFolderContext = new CreateFolderContext();
        createFolderContext.setFolderName("folder-name");
        createFolderContext.setParentId(userInfoVO.getRootFileId());
        createFolderContext.setUserId(userId);

        Long fileId = iUserFileService.createFolder(createFolderContext);
        Assert.notNull(fileId);


        UpdateFilenameContext updateFilenameContext = new UpdateFilenameContext();
        updateFilenameContext.setFileId(fileId);
        updateFilenameContext.setUserId(userId);
        updateFilenameContext.setNewFilename("folder-name-new");

        iUserFileService.updateFilename(updateFilenameContext);
    }

    /**
     * 测试文件删除-失败-fileId
     */
    @Test(expected = HeyDiskBusinessException.class)
    public void testDeleteFileFailByWrongFileId(){
        Long userId = register();
        UserInfoVO userInfoVO = info(userId);

        CreateFolderContext createFolderContext = new CreateFolderContext();
        createFolderContext.setFolderName("folder-name");
        createFolderContext.setParentId(userInfoVO.getRootFileId());
        createFolderContext.setUserId(userId);

        Long fileId = iUserFileService.createFolder(createFolderContext);
        Assert.notNull(fileId);

        DeleteFileContext deleteFileContext = new DeleteFileContext();
        List<Long> fileIdList = Lists.newArrayList();
        fileIdList.add(fileId + 1L);
        deleteFileContext.setFileIdList(fileIdList);
        deleteFileContext.setUserId(userId);
        iUserFileService.deleteFile(deleteFileContext);
    }

    /**
     * 测试文件删除-失败-userId
     */
    @Test(expected = HeyDiskBusinessException.class)
    public void testDeleteFileFailByWrongUserId(){
        Long userId = register();
        UserInfoVO userInfoVO = info(userId);

        CreateFolderContext createFolderContext = new CreateFolderContext();
        createFolderContext.setFolderName("folder-name");
        createFolderContext.setParentId(userInfoVO.getRootFileId());
        createFolderContext.setUserId(userId);

        Long fileId = iUserFileService.createFolder(createFolderContext);
        Assert.notNull(fileId);

        DeleteFileContext deleteFileContext = new DeleteFileContext();
        List<Long> fileIdList = Lists.newArrayList();
        fileIdList.add(fileId);
        deleteFileContext.setFileIdList(fileIdList);
        deleteFileContext.setUserId(userId + 1L);
        iUserFileService.deleteFile(deleteFileContext);
    }

    /**
     * 测试文件删除-成功
     */
    @Test
    public void testDeleteFileSuccess(){
        Long userId = register();
        UserInfoVO userInfoVO = info(userId);

        CreateFolderContext createFolderContext = new CreateFolderContext();
        createFolderContext.setFolderName("folder-name");
        createFolderContext.setParentId(userInfoVO.getRootFileId());
        createFolderContext.setUserId(userId);

        Long fileId = iUserFileService.createFolder(createFolderContext);
        Assert.notNull(fileId);

        DeleteFileContext deleteFileContext = new DeleteFileContext();
        List<Long> fileIdList = Lists.newArrayList();
        fileIdList.add(fileId);
        deleteFileContext.setFileIdList(fileIdList);
        deleteFileContext.setUserId(userId);
        iUserFileService.deleteFile(deleteFileContext);
    }

    /***************************************************private***************************************************/

    private final static String USERNAME = "heyqing";
    private final static String PASSWORD = "12345678";
    private final static String QUESTION = "question";
    private final static String ANSWER = "answer";

    /**
     * 用户注册
     *
     * @return
     */
    private Long register() {
        UserRegisterContext context = createUserRegisterContext();
        Long register = iUserService.register(context);
        Assert.isTrue(register.longValue() > 0L);
        return register;
    }

    /**
     * 查询用户的计本信息
     *
     * @param userId
     * @return
     */
    private UserInfoVO info(Long userId) {

        UserInfoVO userInfoVO = iUserService.info(userId);

        Assert.notNull(userInfoVO);
        return userInfoVO;
    }

    /**
     * 构建注册用户上下文信息
     *
     * @return
     */
    private UserRegisterContext createUserRegisterContext() {

        UserRegisterContext context = new UserRegisterContext();
        context.setUsername(USERNAME);
        context.setPassword(PASSWORD);
        context.setQuestion(QUESTION);
        context.setAnswer(ANSWER);
        return context;
    }

    /**
     * 构建用户登录上下文实体
     *
     * @return
     */
    private UserLoginContext createUserLoginContext() {
        UserLoginContext userLoginContext = new UserLoginContext();
        userLoginContext.setUsername(USERNAME);
        userLoginContext.setPassword(PASSWORD);
        return userLoginContext;
    }
}
