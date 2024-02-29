package com.heyqing.disk.server.modules.file.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heyqing.disk.server.modules.file.entity.HeyDiskFile;
import com.heyqing.disk.server.modules.file.service.IFileService;
import com.heyqing.disk.server.modules.file.mapper.HeyDiskFileMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class IFileServiceImpl extends ServiceImpl<HeyDiskFileMapper, HeyDiskFile>
        implements IFileService {

}




