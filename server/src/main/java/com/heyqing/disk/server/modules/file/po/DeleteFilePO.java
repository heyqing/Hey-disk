package com.heyqing.disk.server.modules.file.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * ClassName:DeleteFilePO
 * Package:com.heyqing.disk.server.modules.file.po
 * Description:
 *
 * @Date:2024/1/17
 * @Author:Heyqing
 */
@Data
@ApiModel(value = "批量删除文件入参对象实体")
public class DeleteFilePO implements Serializable {
    private static final long serialVersionUID = 4500056575585727554L;

    @ApiModelProperty(value = "要删除的文件id，多个使用公用的分隔符分割")
    @NotBlank(message = "请选择要删除的文件信息")
    private String fileIds;
}
