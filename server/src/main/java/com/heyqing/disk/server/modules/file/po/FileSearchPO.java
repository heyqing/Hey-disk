package com.heyqing.disk.server.modules.file.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * ClassName:FileSearchPO
 * Package:com.heyqing.disk.server.modules.file.po
 * Description:
 *
 * @Date:2024/9/12
 * @Author:Heyqing
 */
@Data
@ApiModel("文件搜索参数实体")
public class FileSearchPO implements Serializable {
    private static final long serialVersionUID = 6423953684751022L;

    @ApiModelProperty(value = "搜索关键字", required = true)
    @NotBlank(message = "搜索关键字不能为空")
    private String keyword;

    @ApiModelProperty(value = "文件类型，多个文件类型之间使用公用分隔符拼接")
    private String fileTypes;
}
