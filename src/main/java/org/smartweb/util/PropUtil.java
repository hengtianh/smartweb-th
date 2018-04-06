package org.smartweb.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartweb.constant.ConfigConstant;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropUtil.class);

    private static final Properties prop = new Properties();

    public static Properties loadProp() {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(ConfigConstant.CONFIG_FILE);
        if (in == null) {
            try {
                throw new FileNotFoundException(ConfigConstant.CONFIG_FILE + " not found");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            prop.load(in);
        } catch (IOException e) {
            LOGGER.error("load config properties failure",e);
            throw new RuntimeException(e);
        }finally {
            try {
                in.close();
            } catch (IOException e) {
                LOGGER.debug(ConfigConstant.CONFIG_FILE + " inputstream has not being closed");
                in = null;
            }
        }
        return prop;
    }

    public static String getString(String key, String defaultVal) {
        return prop.getProperty(key,defaultVal);
    }

}
