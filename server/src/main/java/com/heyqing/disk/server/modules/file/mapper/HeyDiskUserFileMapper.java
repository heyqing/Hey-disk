package com.heyqing.disk.server.modules.file.mapper;

import com.heyqing.disk.server.modules.file.context.QueryFileListContext;
import com.heyqing.disk.server.modules.file.entity.HeyDiskUserFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heyqing.disk.server.modules.file.vo.HeyDiskUserFileVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.heyqing.disk.server.modules.file.entity.HeyDiskUserFile
 */
public interface HeyDiskUserFileMapper extends BaseMapper<HeyDiskUserFile> {

    /**
     * 查询用户的文件列表
     *
     * @param queryFileListContext
     * @return
     */
    List<HeyDiskUserFileVO> selectFileList(@Param("param") QueryFileListContext queryFileListContext);
}




