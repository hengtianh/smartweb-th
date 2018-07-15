package org.smartweb.util;

import org.smartweb.constant.ConfigConstant;

import java.lang.invoke.ConstantCallSite;

/**
 * @PROJECT_NAME smartweb
 * @PACKAGE_NAME org.smartweb.util
 * @USER takou
 * @CREATE 2018/4/5
 **/
public final class ConfigUtil {

    /**
     * @Description  获得基础包路径
     * @Param       []
     * @Return      java.lang.String
     * @Author      takou
     * @Date        2018/4/6
     * @Time        上午11:37
     */
    public static String getBasePackage() {
        return PropUtil.getString(ConfigConstant.BASE_PACKAGE,"org.smartweb");
    }

    /**
     * @Description 获得jsp文件路径
     * @Param       []
     * @Return      java.lang.String
     * @Author      takou
     * @Date        2018/4/6
     * @Time        上午11:37
     */
    public static String getJspPath() {
        return PropUtil.getString(ConfigConstant.JSP_PATH,"/WEB-INF/view/");
    }

    /**
     * @Description 获得资源文件路径
     * @Param       []
     * @Return      java.lang.String
     * @Author      takou
     * @Date        2018/4/6
     * @Time        上午11:38
     */
    public static String getAsset() {
        return PropUtil.getString(ConfigConstant.ASSET,"/asset");
    }

    public static String getJdbcDriver() {
        return PropUtil.getString(ConfigConstant.DRIVER,"/jdbc.driver");
    }

    public static String getJdbcUrl() {
        return PropUtil.getString(ConfigConstant.URL,"/jdbc.driver");
    }

    public static String getJdbcUSERNAME() {
        return PropUtil.getString(ConfigConstant.USERNAME,"/jdbc.driver");
    }

    public static String getJdbcPASSWORD() {
        return PropUtil.getString(ConfigConstant.PASSWORD,"/jdbc.driver");
    }

    public static String getJdbcConfigFile() {
        return PropUtil.getString(ConfigConstant.CONFIG_FILE,"/jdbc.driver");
    }


}
