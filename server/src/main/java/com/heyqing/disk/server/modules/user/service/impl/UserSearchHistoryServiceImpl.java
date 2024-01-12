package com.heyqing.disk.server.modules.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heyqing.disk.server.modules.user.entity.HeyDiskUserSearchHistory;
import com.heyqing.disk.server.modules.user.service.IUserSearchHistoryService;
import com.heyqing.disk.server.modules.user.mapper.HeyDiskUserSearchHistoryMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service(value = "userSearchHistoryService")
public class UserSearchHistoryServiceImpl extends ServiceImpl<HeyDiskUserSearchHistoryMapper, HeyDiskUserSearchHistory>
    implements IUserSearchHistoryService {

}




