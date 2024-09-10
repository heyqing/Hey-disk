package com.heyqing.disk.server.modules.file.context;

import com.heyqing.disk.server.modules.file.entity.HeyDiskUserFile;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName:TransferFileContext
 * Package:com.heyqing.disk.server.modules.file.context
 * Description:
 * 文件转移上下文实体
 *
 * @Date:2024/9/10
 * @Author:Heyqing
 */
@Data
public class TransferFileContext implements Serializable {
    private static final long serialVersionUID = 2610132310097931195L;

    /**
     * 要转移的文件id集合
     */
    private List<Long> fileIdList;

    /**
     * 要转移到的目标文件夹的id
     */
    private Long targetParentId;

    /**
     * 当前登录的用户id
     */
    private Long userId;

    /**
     * 要转移的文件列表
     */
    private List<HeyDiskUserFile> prepareRecords;
}
