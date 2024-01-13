package com.heyqing.disk.server.modules.file.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ClassName:FolderFlagEnum
 * Package:com.heyqing.disk.server.modules.file.enums
 * Description:
 *          文件夹表示枚举类
 * @Date:2024/1/13
 * @Author:Heyqing
 */
@AllArgsConstructor
@Getter
public enum FolderFlagEnum {

    /**
     * 非文件夹
     */
    NO(0),

    /**
     * 是文件夹
     */
    YES(1);

    private Integer code;

}
