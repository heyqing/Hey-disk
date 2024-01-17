package com.heyqing.disk.server.modules.file.context;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName:DeleteFileContext
 * Package:com.heyqing.disk.server.modules.file.context
 * Description:
 *
 * @Date:2024/1/17
 * @Author:Heyqing
 */
@Data
public class DeleteFileContext implements Serializable {

    private static final long serialVersionUID = 4402301322808368989L;

    /**
     *要删除的文件id集合
     */
    private List<Long> fileIdList;

    /**
     * 当前用户id
     */
    private Long userId;
}
