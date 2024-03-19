package com.heyqing.disk.server.modules.log.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heyqing.disk.server.modules.log.entity.HeyDiskErrorLog;
import com.heyqing.disk.server.modules.log.service.IErrorLogService;
import com.heyqing.disk.server.modules.log.mapper.HeyDiskErrorLogMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class IErrorLogServiceImpl extends ServiceImpl<HeyDiskErrorLogMapper, HeyDiskErrorLog>
    implements IErrorLogService {

}




