package com.heyqing.disk.server.modules.file.vo;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.heyqing.disk.web.serializer.IdEncryptSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName:FolderTreeNodeVO
 * Package:com.heyqing.disk.server.modules.file.vo
 * Description:
 * 文件夹树节点实体
 *
 * @Date:2024/9/9
 * @Author:Heyqing
 */
@ApiModel("文件夹树节点实体")
@Data
public class FolderTreeNodeVO implements Serializable {
    private static final long serialVersionUID = 644808889730820016L;
    @ApiModelProperty("文件夹名称")
    private String label;

    @ApiModelProperty("文件id")
    @JsonSerialize(using = IdEncryptSerializer.class)
    private Long id;

    @ApiModelProperty("父文件id")
    @JsonSerialize(using = IdEncryptSerializer.class)
    private Long parentId;
    @ApiModelProperty("子节点集合")
    private List<FolderTreeNodeVO> children;

    public void print() {
        String jsonString = JSON.toJSONString(this);
        System.out.println(jsonString);
    }
}
