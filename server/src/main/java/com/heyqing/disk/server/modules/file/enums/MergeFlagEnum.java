package com.heyqing.disk.server.modules.file.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ClassName:MergeFlagEnum
 * Package:com.heyqing.disk.server.modules.file.enums
 * Description:
 * 文件合并标识枚举类
 *
 * @Date:2024/3/20
 * @Author:Heyqing
 */
@Getter
@AllArgsConstructor
public enum MergeFlagEnum {

    /**
     * 不需要合并
     */
    NOT_READY(0),

    /**
     * 需要合并
     */
    READY(1);

    private Integer coda;

}
