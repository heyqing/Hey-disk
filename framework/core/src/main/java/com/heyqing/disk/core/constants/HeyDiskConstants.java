package com.heyqing.disk.core.constants;

import org.apache.commons.lang3.StringUtils;

/**
 * ClassName:HeyDiskConstants
 * Package:com.heyqing.disk.core.constants
 * Description:
 *          HeyDisk 公用基础常量类
 * @Date:2024/1/6
 * @Author:Heyqing
 */
public interface HeyDiskConstants {

    /**
     * 公用的字符串分隔符
     */
    String COMMON_SEPARATOR = "__,__";

    /**
     * 空字符串
     */
    String EMPTY_STR = StringUtils.EMPTY;

    /**
     * 点常量
     */
    String POINT_STR = ".";

    /**
     * 斜线字符串
     */
    String SLASH_STR = "/";

    /**
     * TRUE字符串
     */
    String TRUE_STR = "true";

    /**
     * FALSE字符串
     */
    String  FALSE_STR = "false";

    /**
     * 组件扫描基础路径
     */
    String BASE_COMPONENT_SCAN_PATH = "com.heyqing.disk";

    /**
     * Long 常量 0
     */
    Long ZERO_LONG = 0l;

    /**
     * Integer 常量 0
     */
    Integer ZERO_INT = 0;

    /**
     * Integer 常量 1
     */
    Integer ONE_INT = 1;

    /**
     * Integer 常量 2
     */
    Integer TWO_INT = 2;

    /**
     * Integer 常量 -1
     */
    Integer MINUS_ONE_INT = -1;

}
