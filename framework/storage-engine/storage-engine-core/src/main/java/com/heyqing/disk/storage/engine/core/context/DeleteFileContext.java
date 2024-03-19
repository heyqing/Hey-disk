package com.heyqing.disk.storage.engine.core.context;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName:DeleteFileContext
 * Package:com.heyqing.disk.storage.engine.core.context
 * Description:
 * 删除物理文件的上下文实体信息
 *
 * @Date:2024/3/19
 * @Author:Heyqing
 */
@Data
public class DeleteFileContext implements Serializable {
    private static final long serialVersionUID = -8816241708624596634L;

    /**
     * 要删除物理文件的路径集合
     */
    private List<String> realFilePathList;
}
