package com.heyqing.disk.core.utils;

import com.heyqing.disk.core.constants.HeyDiskConstants;
import org.apache.commons.lang3.StringUtils;

/**
 * ClassName:FileUtil
 * Package:com.heyqing.disk.core.utils
 * Description:
 * 文件相关工具类
 *
 * @Date:2024/2/28
 * @Author:Heyqing
 */
public class FileUtil {
    public static String getFileSuffix(String filename) {
        if (StringUtils.isBlank(filename) || filename.indexOf(HeyDiskConstants.POINT_STR) == HeyDiskConstants.MINUS_ONE_INT){
            return StringUtils.EMPTY;
        }
        return filename.substring(filename.lastIndexOf(HeyDiskConstants.POINT_STR));
    }
}
