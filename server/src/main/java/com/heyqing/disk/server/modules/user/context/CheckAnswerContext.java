package com.heyqing.disk.server.modules.user.context;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName:CheckAnswerContext
 * Package:com.heyqing.disk.server.modules.user.context
 * Description:
 *
 * @Date:2024/1/16
 * @Author:Heyqing
 */
@Data
public class CheckAnswerContext implements Serializable {

    private static final long serialVersionUID = 3330432045022751550L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密保问题
     */
    private String question;

    /**
     * 密保答案
     */
    private String answer;
}
