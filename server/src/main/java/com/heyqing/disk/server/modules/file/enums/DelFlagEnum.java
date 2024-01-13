package com.heyqing.disk.server.modules.file.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ClassName:DelFlagEnum
 * Package:com.heyqing.disk.server.modules.file.enums
 * Description:
 *          文件删除标识枚举类
 * @Date:2024/1/13
 * @Author:Heyqing
 */
@AllArgsConstructor
@Getter
public enum DelFlagEnum {

    /**
     * 未删除
     */
    NO(0),

    /**
     * 已删除
     */
    YES(1);
    private Integer code;

}
