package org.smartweb.container;

import org.smartweb.util.ClassUtil;
import org.smartweb.web.WebController;

/**
 * @PROJECT_NAME smartweb
 * @PACKAGE_NAME org.smartweb.container
 * @USER takou
 * @CREATE 2018/4/5
 **/
public final class BeanContainerBuilder {

    private static Class<?>[] containers = new Class<?>[]{BeanContainer.class, AopBuilder.class,WebController.class};

    /**
     * @Description 构建bean容器
     * @Param       []
     * @Return      void
     * @Author      takou
     * @Date        2018/4/6
     * @Time        下午12:17
     */
    public static void buildWebContainer() {
        for (Class<?> cls : containers) {
            ClassUtil.loadClass(cls.getName(),true);
        }
    }
}
