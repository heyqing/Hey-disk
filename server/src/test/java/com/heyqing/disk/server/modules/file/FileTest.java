package com.heyqing.disk.server.modules.file;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import com.heyqing.disk.core.exception.HeyDiskBusinessException;
import com.heyqing.disk.core.utils.IdUtil;
import com.heyqing.disk.server.HeyDiskServerLauncher;
import com.heyqing.disk.server.modules.file.context.*;
import com.heyqing.disk.server.modules.file.entity.HeyDiskFile;
import com.heyqing.disk.server.modules.file.entity.HeyDiskFileChunk;
import com.heyqing.disk.server.modules.file.enums.DelFlagEnum;
import com.heyqing.disk.server.modules.file.enums.MergeFlagEnum;
import com.heyqing.disk.server.modules.file.service.IFileChunkService;
import com.heyqing.disk.server.modules.file.service.IFileService;
import com.heyqing.disk.server.modules.file.service.IUserFileService;
import com.heyqing.disk.server.modules.file.vo.FileChunkUploadVO;
import com.heyqing.disk.server.modules.file.vo.FolderTreeNodeVO;
import com.heyqing.disk.server.modules.file.vo.HeyDiskUserFileVO;
import com.heyqing.disk.server.modules.file.vo.UploadedChunksVO;
import com.heyqing.disk.server.modules.user.context.UserLoginContext;
import com.heyqing.disk.server.modules.user.context.UserRegisterContext;
import com.heyqing.disk.server.modules.user.service.IUserService;
import com.heyqing.disk.server.modules.user.vo.UserInfoVO;
import lombok.AllArgsConstructor;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

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
    @Autowired
    private IFileService iFileService;
    @Autowired
    private IFileChunkService iFileChunkService;


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
    public void testUpdateFilenameFailByWrongFileId() {
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
    public void testUpdateFilenameFailByWrongUserId() {
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
    public void testUpdateFilenameFailByWrongFilename() {
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
    public void testUpdateFilenameFailByFilenameUnAvailable() {
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
    public void testUpdateFilenameSuccess() {
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
    public void testDeleteFileFailByWrongFileId() {
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
    public void testDeleteFileFailByWrongUserId() {
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
    public void testDeleteFileSuccess() {
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

    /**
     * 测试文件秒传-成功
     */
    @Test
    public void testSceUploadSuccess() {
        Long userId = register();
        UserInfoVO userInfoVO = info(userId);

        String identifier = "identifier";
        HeyDiskFile record = new HeyDiskFile();
        record.setFileId(IdUtil.get());
        record.setFilename("filename");
        record.setRealPath("realPath");
        record.setFileSize("dileSize");
        record.setFileSizeDesc("desc");
        record.setFileSuffix("suffix");
        record.setFilePreviewContentType("");
        record.setIdentifier(identifier);
        record.setCreateUser(userId);
        record.setCreateTime(new Date());

        boolean save = iFileService.save(record);
        Assert.isTrue(save);

        SecUploadFileContext context = new SecUploadFileContext();
        context.setIdentifier(identifier);
        context.setFilename("filename");
        context.setParentId(userInfoVO.getRootFileId());
        context.setUserId(userId);

        boolean success = iUserFileService.secUpload(context);
        Assert.isTrue(success);
    }

    /**
     * 测试文件秒传-失败
     */
    @Test
    public void testSceUploadFail() {
        Long userId = register();
        UserInfoVO userInfoVO = info(userId);

        String identifier = "identifier";

        SecUploadFileContext context = new SecUploadFileContext();
        context.setIdentifier(identifier);
        context.setFilename("filename");
        context.setParentId(userInfoVO.getRootFileId());
        context.setUserId(userId);

        boolean success = iUserFileService.secUpload(context);
        Assert.isFalse(success);
    }

    /**
     * 测试单文件上传-成功
     */
    @Test
    public void testUploadSuccess() {
        Long userId = register();
        UserInfoVO userInfoVO = info(userId);

        FileUploadContext context = new FileUploadContext();
        MultipartFile file = generateMultipartFile();

        context.setFile(file);
        context.setParentId(userInfoVO.getRootFileId());
        context.setUserId(userId);
        context.setIdentifier("12345678");
        context.setTotalSize(file.getSize());
        context.setFilename(file.getOriginalFilename());
        iUserFileService.upload(context);

        QueryFileListContext queryFileListContext = new QueryFileListContext();
        queryFileListContext.setDelFlag(DelFlagEnum.NO.getCode());
        queryFileListContext.setUserId(userId);
        queryFileListContext.setParentId(userInfoVO.getRootFileId());
        List<HeyDiskUserFileVO> fileList = iUserFileService.getFileList(queryFileListContext);

        Assert.notEmpty(fileList);
        Assert.isTrue(fileList.size() == 1);

    }

    /**
     * 测试查询用户已上传的分片信息列表-成功
     */
    @Test
    public void testQueryUploadedChunksSuccess() {
        Long userId = register();

        String identifier = "123456";

        HeyDiskFileChunk record = new HeyDiskFileChunk();
        record.setId(IdUtil.get());
        record.setIdentifier(identifier);
        record.setRealPath("realPath");
        record.setChunkNumber(1);
        record.setExpirationTime(DateUtil.offsetDay(new Date(), 1));
        record.setCreateUser(userId);
        record.setCreateTime(new Date());
        boolean save = iFileChunkService.save(record);
        Assert.isTrue(save);

        QueryUploadedChunksContext context = new QueryUploadedChunksContext();
        context.setIdentifier(identifier);
        context.setUserId(userId);

        UploadedChunksVO vo = iUserFileService.getUploadedChunks(context);
        Assert.notNull(vo);
        Assert.notEmpty(vo.getUploadedChunks());

    }

    /**
     * 分片上传整体-成功
     */
    @Test
    public void testUploadWithChunkSuccess() throws InterruptedException {
        Long userId = register();
        UserInfoVO userInfoVO = info(userId);

        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new ChunkUploader(countDownLatch, i + 1, 10, iUserFileService, userId, userInfoVO.getRootFileId()).start();
        }
        countDownLatch.await();
    }

    /**
     * 查询文件夹树-成功
     */
    @Test
    public void testGetFolderTreeNodeVOListSuccess() {
        Long userId = register();
        UserInfoVO userInfoVO = info(userId);

        CreateFolderContext createFolderContext = new CreateFolderContext();
        createFolderContext.setFolderName("folder-name-1");
        createFolderContext.setParentId(userInfoVO.getRootFileId());
        createFolderContext.setUserId(userId);

        Long fileId = iUserFileService.createFolder(createFolderContext);
        Assert.notNull(fileId);


        createFolderContext.setFolderName("folder-name-2");
        fileId = iUserFileService.createFolder(createFolderContext);
        Assert.notNull(fileId);

        createFolderContext.setFolderName("folder-name-2-1");
        createFolderContext.setParentId(fileId);
        iUserFileService.createFolder(createFolderContext);
        Assert.notNull(fileId);

        QueryFolderTreeContext queryFolderTreeContext = new QueryFolderTreeContext();
        queryFolderTreeContext.setUserId(userId);

        List<FolderTreeNodeVO> folderTree = iUserFileService.getFolderTree(queryFolderTreeContext);

        Assert.isTrue(folderTree.size() == 1);
        folderTree.stream().forEach(FolderTreeNodeVO::print);
    }

    /**
     * 文件转移-成功
     */
    @Test
    public void testTransferFileSuccess() {
        Long userId = register();
        UserInfoVO userInfoVO = info(userId);

        CreateFolderContext createFolderContext = new CreateFolderContext();
        createFolderContext.setFolderName("folder-name-1");
        createFolderContext.setParentId(userInfoVO.getRootFileId());
        createFolderContext.setUserId(userId);

        Long folder1 = iUserFileService.createFolder(createFolderContext);
        Assert.notNull(folder1);

        createFolderContext.setFolderName("folder-name-2");
        Long folder2 = iUserFileService.createFolder(createFolderContext);
        Assert.notNull(folder2);

        TransferFileContext context = new TransferFileContext();
        context.setTargetParentId(folder1);
        context.setFileIdList(Lists.newArrayList(folder2));
        context.setUserId(userId);
        iUserFileService.transfer(context);

        QueryFileListContext queryFileListContext = new QueryFileListContext();
        queryFileListContext.setParentId(userInfoVO.getRootFileId());
        queryFileListContext.setUserId(userId);
        queryFileListContext.setDelFlag(DelFlagEnum.NO.getCode());
        List<HeyDiskUserFileVO> records = iUserFileService.getFileList(queryFileListContext);
        Assert.notEmpty(records);
    }

    /**
     * 文件转移-失败
     */
    @Test(expected = HeyDiskBusinessException.class)
    public void testTransferFileFail() {
        //目标文件夹为要转移文件列表的文件夹或子文件夹
        Long userId = register();
        UserInfoVO userInfoVO = info(userId);

        CreateFolderContext createFolderContext = new CreateFolderContext();
        createFolderContext.setFolderName("folder-name-1");
        createFolderContext.setParentId(userInfoVO.getRootFileId());
        createFolderContext.setUserId(userId);

        Long folder1 = iUserFileService.createFolder(createFolderContext);
        Assert.notNull(folder1);

        createFolderContext.setParentId(folder1);
        createFolderContext.setFolderName("folder-name-2");
        Long folder2 = iUserFileService.createFolder(createFolderContext);
        Assert.notNull(folder2);

        TransferFileContext context = new TransferFileContext();
        context.setTargetParentId(folder2);
        context.setFileIdList(Lists.newArrayList(folder1));
        context.setUserId(userId);
        iUserFileService.transfer(context);
    }

    /***************************************************private***************************************************/

    private final static String USERNAME = "heyqing";
    private final static String PASSWORD = "12345678";
    private final static String QUESTION = "question";
    private final static String ANSWER = "answer";

    /**
     * 文件分片上传器
     */
    @AllArgsConstructor
    private static class ChunkUploader extends Thread {
        private CountDownLatch countDownLatch;
        private Integer chunk;
        private Integer chunks;
        private IUserFileService iUserFileService;
        private Long userId;
        private Long parentId;

        /**
         * 1、上传文件分片
         * 2、根据上传的结果来调用文件分片合并
         */
        @Override
        public void run() {
            super.run();
            MultipartFile file = generateMultipartFile();
            long totalSize = file.getSize() * chunks;
            String filename = "test.txt";
            String identifier = "123456789";

            FileChunkUploadContext fileChunkUploadContext = new FileChunkUploadContext();
            fileChunkUploadContext.setFilename(filename);
            fileChunkUploadContext.setIdentifier(identifier);
            fileChunkUploadContext.setTotalChunks(chunks);
            fileChunkUploadContext.setChunkNumber(chunk);
            fileChunkUploadContext.setCurrentChunkSize(file.getSize());
            fileChunkUploadContext.setTotalSize(totalSize);
            fileChunkUploadContext.setFile(file);
            fileChunkUploadContext.setUserId(userId);

            FileChunkUploadVO vo = iUserFileService.chunkUpload(fileChunkUploadContext);

            if (vo.getMergeFlag().equals(MergeFlagEnum.READY.getCode())) {
                System.out.println("分片" + chunk + "检测到可以合并分片");
                FileChunkMergeContext fileChunkMergeContext = new FileChunkMergeContext();
                fileChunkMergeContext.setFilename(filename);
                fileChunkMergeContext.setIdentifier(identifier);
                fileChunkMergeContext.setTotalSize(totalSize);
                fileChunkMergeContext.setParentId(parentId);
                fileChunkMergeContext.setUserId(userId);

                iUserFileService.mergeFile(fileChunkMergeContext);
                countDownLatch.countDown();
            } else {
                countDownLatch.countDown();
            }
        }
    }

    /**
     * 生成模拟的网络文件实体
     *
     * @return
     */
    private static MultipartFile generateMultipartFile() {
        MultipartFile file = null;
        try {
            file = new MockMultipartFile("file", "test.txt", "multipart/form-data", "test upload context".getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

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
