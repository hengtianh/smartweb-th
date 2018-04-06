package org.smartweb.util;

import org.apache.commons.lang3.StringUtils;

public final class TypeCastUtil {

    public static String castString(Object object) {
        return castString(object,"");
    }

    public static String castString(Object object, String defaultVal) {
        String str = defaultVal;
        if (object != null && object instanceof String) {
            str = (String) object;
        }
        return str;
    }

    public static int castInt(Object object) {
        return castInt(object,0);
    }

    /**
     * @Description
     * @Param       [object, defaultVal]
     * @Return      int
     * @Author      takou
     * @Date        2018/4/4
     * @Time        下午12:30
     */
    public static int castInt(Object object, int defaultVal) {
        int intVal = defaultVal;
        if (object != null) {
            String str = castString(object);
            if (StringUtils.isNotEmpty(str)) {
                try {
                    intVal = Integer.parseInt(str);
                } catch (NumberFormatException e) {
                    intVal = defaultVal;
                }
            }
        }
        return intVal;
    }


}
