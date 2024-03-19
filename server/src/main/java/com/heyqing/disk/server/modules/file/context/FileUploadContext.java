package com.heyqing.disk.server.modules.file.context;

import com.heyqing.disk.server.modules.file.entity.HeyDiskFile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * ClassName:FileUploadPO
 * Package:com.heyqing.disk.server.modules.file.po
 * Description:
 * 单文件上传上下文实体
 *
 * @Date:2024/3/3
 * @Author:Heyqing
 */
@Data
@ApiModel(value = "单文件上传参数实体对象")
public class FileUploadContext implements Serializable {


    private static final long serialVersionUID = -1907606593840698590L;

    /**
     * 文件名称
     */
    private String filename;

    /**
     * 文件唯一标识
     */
    private String identifier;

    /**
     * 文件总大小
     */
    private Long totalSize;

    /**
     * 文件父文件夹id
     */
    private Long parentId;

    /**
     * 文件实体
     */
    private MultipartFile file;

    /**
     * 当前用户id
     */
    private Long userId;

    /**
     * 实体文件记录
     */
    private HeyDiskFile record;
}
