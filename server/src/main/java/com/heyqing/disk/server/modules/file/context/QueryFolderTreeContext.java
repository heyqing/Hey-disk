package com.heyqing.disk.server.modules.file.context;

import com.heyqing.disk.server.modules.file.vo.FolderTreeNodeVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName:QueryFolderTreeContext
 * Package:com.heyqing.disk.server.modules.file.context
 * Description:
 * 查询文件夹树上下文实体
 *
 * @Date:2024/9/9
 * @Author:Heyqing
 */
@Data
public class QueryFolderTreeContext implements Serializable {
    private static final long serialVersionUID = 7742158743627699184L;

    /**
     * 当前登录的userid
     */
    private Long userId;
}
