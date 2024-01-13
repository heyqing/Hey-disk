package com.heyqing.disk.server.modules.user;

import cn.hutool.core.lang.Assert;
import com.heyqing.disk.core.exception.HeyDiskBusinessException;
import com.heyqing.disk.server.HeyDiskServerLauncher;
import com.heyqing.disk.server.modules.user.context.UserRegisterContext;
import com.heyqing.disk.server.modules.user.service.IUserService;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * ClassName:UserTest
 * Package:com.heyqing.disk.server.modules.user
 * Description:
 * 用户模块单元测试类
 *
 * @Date:2024/1/13
 * @Author:Heyqing
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = HeyDiskServerLauncher.class)
@Transactional
public class UserTest {

    @Autowired
    private IUserService iUserService;

    /**
     * 测试成功注册用户信息
     */
    @Test
    public void testRegisterUser() {
        UserRegisterContext context = createUserRegisterContext();
        Long register = iUserService.register(context);
        Assert.isTrue(register.longValue() > 0L);
    }

    /**
     * 测试重复用户名称注册幂等
     */
    @Test(expected = HeyDiskBusinessException.class)
    public void testRegisterDuplicateUsername() {
        UserRegisterContext context = createUserRegisterContext();
        Long register = iUserService.register(context);
        Assert.isTrue(register.longValue() > 0L);
        iUserService.register(context);
    }

    /**
     * 构建注册用户上下文信息
     *
     * @return
     */
    private UserRegisterContext createUserRegisterContext() {

        UserRegisterContext context = new UserRegisterContext();
        context.setUsername("heyqing");
        context.setPassword("12345678");
        context.setQuestion("question");
        context.setAnswer("answer");
        return context;
    }
}
