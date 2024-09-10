package com.heyqing.disk.server.modules.file.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * ClassName:TransferFilePO
 * Package:com.heyqing.disk.server.modules.file.po
 * Description:
 *
 * @Date:2024/9/10
 * @Author:Heyqing
 */
@ApiModel("文件转移参数实体对象")
@Data
public class TransferFilePO implements Serializable {
    private static final long serialVersionUID = -7180057951698218694L;

    @ApiModelProperty("要转移的文件id集合，多个使用公用分隔符分开")
    @NotBlank(message = "请选择要转移的文件")
    private String fileIds;

    @ApiModelProperty("要转移到的目标文件夹的id")
    @NotBlank(message = "请选择要转移的目标文件夹")
    private String targetParentId;
}
