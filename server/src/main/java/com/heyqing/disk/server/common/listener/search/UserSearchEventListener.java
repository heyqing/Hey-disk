package com.heyqing.disk.server.common.listener.search;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.heyqing.disk.core.utils.IdUtil;
import com.heyqing.disk.server.common.event.search.UserSearchEvent;
import com.heyqing.disk.server.modules.user.entity.HeyDiskUserSearchHistory;
import com.heyqing.disk.server.modules.user.service.IUserSearchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * ClassName:UserSearchEventListener
 * Package:com.heyqing.disk.server.common.listener.search
 * Description:
 * 用户搜索事件监听器
 *
 * @Date:2024/9/12
 * @Author:Heyqing
 */
@Component
public class UserSearchEventListener {

    @Autowired
    private IUserSearchHistoryService iUserSearchHistoryService;

    /**
     * 监听用户搜索事件，将其保存到用户的搜索历史记录当中
     *
     * @param event
     */
    @EventListener(classes = UserSearchEvent.class)
    public void saveSearchHistory(UserSearchEvent event) {
        HeyDiskUserSearchHistory record = new HeyDiskUserSearchHistory();

        record.setId(IdUtil.get());
        record.setUserId(event.getUserId());
        record.setSearchContent(event.getKeyword());
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());

        try {
            iUserSearchHistoryService.save(record);
        } catch (DuplicateKeyException e) {
            UpdateWrapper updateWrapper = Wrappers.update();
            updateWrapper.eq("user_id", event.getUserId());
            updateWrapper.eq("search_content", event.getKeyword());
            updateWrapper.set("update_time", new Date());
            iUserSearchHistoryService.update(updateWrapper);
        }

    }
}
