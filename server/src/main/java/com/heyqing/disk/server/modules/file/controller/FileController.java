package com.heyqing.disk.server.modules.file.controller;

import com.google.common.base.Splitter;
import com.heyqing.disk.core.constants.HeyDiskConstants;
import com.heyqing.disk.core.response.Result;
import com.heyqing.disk.core.utils.IdUtil;
import com.heyqing.disk.server.common.utils.UserIdUtil;
import com.heyqing.disk.server.modules.file.constants.FileConstants;
import com.heyqing.disk.server.modules.file.context.*;
import com.heyqing.disk.server.modules.file.converter.FileConverter;
import com.heyqing.disk.server.modules.file.enums.DelFlagEnum;
import com.heyqing.disk.server.modules.file.po.*;
import com.heyqing.disk.server.modules.file.service.IFileService;
import com.heyqing.disk.server.modules.file.service.IUserFileService;
import com.heyqing.disk.server.modules.file.vo.FileChunkUploadVO;
import com.heyqing.disk.server.modules.file.vo.HeyDiskUserFileVO;
import com.heyqing.disk.server.modules.file.vo.UploadedChunksVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * ClassName:FileController
 * Package:com.heyqing.disk.server.modules.file.controller
 * Description:
 *
 * @Date:2024/1/17
 * @Author:Heyqing
 */
@RestController
@Validated
@Api(tags = "文件模块")
public class FileController {

    @Autowired
    private IUserFileService iUserFileService;
    @Autowired
    private FileConverter fileConverter;

    @ApiOperation(
            value = "查询文件列表",
            notes = "该接口提供了用户查询某文件夹下面某些文件类型的文件列表的功能",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @GetMapping("files")
    public Result<List<HeyDiskUserFileVO>> list(@NotBlank(message = "父文件夹Id不能为空") @RequestParam(value = "parentId",required = false) String parentId,
                                                @RequestParam(value = "fileTypes",required = false,defaultValue = FileConstants.ALL_FILE_TYPE) String fileTypes){

        Long realParentId = IdUtil.decrypt(parentId);
        List<Integer> fileTypeArray = null;
        if (Objects.equals(FileConstants.ALL_FILE_TYPE,fileTypes)){
            fileTypeArray = Splitter.on(HeyDiskConstants.COMMON_SEPARATOR).splitToList(fileTypes).stream().map(Integer::valueOf).collect(Collectors.toList());
        }
        QueryFileListContext queryFileListContext = new QueryFileListContext();
        queryFileListContext.setParentId(realParentId);
        queryFileListContext.setFileTypeArray(fileTypeArray);
        queryFileListContext.setUserId(UserIdUtil.get());
        queryFileListContext.setDelFlag(DelFlagEnum.NO.getCode());
        List<HeyDiskUserFileVO> result = iUserFileService.getFileList(queryFileListContext);
        return Result.data(result);
    }

    @ApiOperation(
            value = "创建文件夹",
            notes = "该接口提供了创建文件夹的功能",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @PostMapping("file/folder")
    public Result<String> createFolder(@Validated @RequestBody CreateFolderPO createFolderPO){
        CreateFolderContext folderPO2CreateFolderContext = fileConverter.createFolderPO2CreateFolderContext(createFolderPO);
        Long fileId = iUserFileService.createFolder(folderPO2CreateFolderContext);
        return Result.data(IdUtil.encrypt(fileId));
    }

    @ApiOperation(
            value = "文件重命名",
            notes = "该接口提供了文件重命名的功能",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @PutMapping("file")
    public Result updateFilename(@Validated @RequestBody UpdateFilenamePO updateFilenamePO){
        UpdateFilenameContext context = fileConverter.updateFilenamePO2UpdateFilenameContext(updateFilenamePO);
        iUserFileService.updateFilename(context);
        return Result.success();
    }

    @ApiOperation(
            value = "批量删除文件",
            notes = "该接口提供了批量删除文件的功能",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @DeleteMapping("file/delete")
    public Result deleteFile(@Validated @RequestBody DeleteFilePO deleteFilePO){
        DeleteFileContext context = fileConverter.deleteFilePO2DeleteFileContext(deleteFilePO);
        String fileIds = deleteFilePO.getFileIds();
        List<Long> fileIdList = Splitter.on(HeyDiskConstants.COMMON_SEPARATOR).splitToList(fileIds).stream().map(IdUtil::decrypt).collect(Collectors.toList());
        context.setFileIdList(fileIdList);
        iUserFileService.deleteFile(context);
        return Result.success();
    }

    @ApiOperation(
            value = "文件秒传",
            notes = "该接口提供了文件秒传的功能",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @PostMapping("file/sec-upload")
    public Result secUpload(@Validated @RequestBody SecUploadFilePO secUploadFilePO){
        SecUploadFileContext context = fileConverter.secUploadFilePO2SecUploadFileContext(secUploadFilePO);
        boolean success = iUserFileService.secUpload(context);
        if (success){
            return Result.success();
        }
        return Result.fail("文件唯一标识不存在，请手动执行文件上传操作");
    }

    @ApiOperation(
            value = "单文件上传",
            notes = "该接口提供了单文件上传的功能",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @PostMapping("file/upload")
    public Result upload(@Validated FileUploadPO fileUploadPO){
        FileUploadContext context = fileConverter.fileUploadPO2FileUploadContext(fileUploadPO);
        iUserFileService.upload(context);
        return Result.success();
    }

    @ApiOperation(
            value = "文件分片上传",
            notes = "该接口提供了文件分片上传的功能",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @PostMapping("file/chunk-upload")
    public Result<FileChunkUploadVO> chunkUpload(@Validated FileChunkUploadPO fileChunkUploadPO){
        FileChunkUploadContext context = fileConverter.fileChunkUploadPO2FileChunkUploadContext(fileChunkUploadPO);
        FileChunkUploadVO vo =iUserFileService.chunkUpload(context);
        return Result.data(vo);
    }

    @ApiOperation(
            value = "查询用户已上传的分片列表",
            notes = "该接口提供了查询用户已上传的分片列表的功能",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @GetMapping("file/chunk-upload")
    public Result<UploadedChunksVO> getUploadedChunks(@Validated QueryUploadedChunksPO queryUploadedChunksPO){
        QueryUploadedChunksContext context = fileConverter.queryUploadedChunksPO2QueryUploadedChunksContext(queryUploadedChunksPO);
        UploadedChunksVO vo =iUserFileService.getUploadedChunks(context);
        return Result.data(vo);
    }

    @ApiOperation(
            value = "文件分片合并",
            notes = "该接口提供了文件分片合并的功能",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @GetMapping("file/merge")
    public Result mergeFile(@Validated @RequestBody FileChunkMergePO fileChunkMergePO){
        FileChunkMergeContext context = fileConverter.fileChunkMergePO2FileChunkMergeContext(fileChunkMergePO);
        iUserFileService.mergeFile(context);
        return Result.success();
    }
}
