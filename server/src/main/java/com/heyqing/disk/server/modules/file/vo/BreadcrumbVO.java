package com.heyqing.disk.server.modules.file.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.heyqing.disk.server.modules.file.entity.HeyDiskUserFile;
import com.heyqing.disk.web.serializer.IdEncryptSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * ClassName:BreadcrumbVO
 * Package:com.heyqing.disk.server.modules.file.vo
 * Description:
 *
 * @Date:2024/9/13
 * @Author:Heyqing
 */
@Data
@ApiModel("文件面包屑查询返回实体")
public class BreadcrumbVO implements Serializable {
    private static final long serialVersionUID = -1619066947027306230L;

    @ApiModelProperty("文件id")
    @JsonSerialize(using = IdEncryptSerializer.class)
    private Long id;

    @ApiModelProperty("父文件夹id")
    @JsonSerialize(using = IdEncryptSerializer.class)
    private Long parentId;

    @ApiModelProperty("文件夹名称")
    private String name;

    /**
     * 实体转换
     *
     * @param record
     * @return
     */
    public static BreadcrumbVO transfer(HeyDiskUserFile record) {
        BreadcrumbVO vo = new BreadcrumbVO();
        if (Objects.nonNull(record)) {
            vo.setId(record.getFileId());
            vo.setParentId(record.getParentId());
            vo.setName(record.getFilename());
        }
        return vo;
    }
}
