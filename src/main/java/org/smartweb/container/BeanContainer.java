package org.smartweb.container;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartweb.Annotation.Aspect;
import org.smartweb.Annotation.AutoInject;
import org.smartweb.Annotation.Controller;
import org.smartweb.Annotation.Service;
import org.smartweb.aop.Proxy;
import org.smartweb.util.ClassUtil;
import org.smartweb.util.ConfigUtil;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @PROJECT_NAME smartweb
 * @PACKAGE_NAME org.smartweb.container
 * @USER takou
 * @CREATE 2018/4/4
 **/
public final class BeanContainer {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeanContainer.class);

    static final Set<Class<?>> beanSet = new HashSet<Class<?>>(0);

    private static final Set<Class<?>> controllerSet = new HashSet<Class<?>>(0);

    private static final Set<Class<?>> serviceSet = new HashSet<Class<?>>(0);

    static final Set<Class<?>> aspectSet = new HashSet<Class<?>>(0);

    static final Map<Class<?>,Object> beanMap = new HashMap<Class<?>, Object>();

    static {
        initBeanSet();
    }

    /**
     * @Description 初始化bean容器
     * @Param       []
     * @Return      void
     * @Author      takou
     * @Date        2018/4/6
     * @Time        下午12:11
     */
    private static void initBeanSet() {
        Set<Class<?>> set = ClassUtil.getClassSet(ConfigUtil.getBasePackage());
        for (Class cls : set) {
            if (cls.isAnnotationPresent(Controller.class)) {
                controllerSet.add(cls);
            } else if (cls.isAnnotationPresent(Service.class)) {
                serviceSet.add(cls);
            } else if (cls.isAnnotationPresent(Aspect.class)) {
                aspectSet.add(cls);
            }
        }
        beanSet.addAll(controllerSet);
        beanSet.addAll(serviceSet);
        beanSet.addAll(aspectSet);
    }

    /**
     * @Description 从容器中获得bean
     * @Param       [cls]
     * @Return      T
     * @Author      takou
     * @Date        2018/4/6
     * @Time        下午12:11
     */
    public static <T> T getBean(Class<?> cls) {
        T bean = null;
        if (beanSet.contains(cls) && beanMap.containsKey(cls)) {
            bean = (T) beanMap.get(cls);
        } else if (beanSet.contains(cls) && !beanMap.containsKey(cls)) {
            bean = initBean(cls);
        }
        return bean;
    }

    /**
     * @Description 实例化bean
     * @Param       [cls]
     * @Return      T
     * @Author      takou
     * @Date        2018/4/6
     * @Time        下午12:12
     */
    private static <T> T initBean(Class<?> cls) {
        T obj = (T) ClassUtil.newInstance(cls);
        beanMap.put(cls,obj);
        initDependency(cls,obj);
        return obj;
    }

    /**
     * @Description bean的依赖注入
     * @Param       [cls, obj]
     * @Return      void
     * @Author      takou
     * @Date        2018/4/6
     * @Time        下午12:12
     */
    private static <T> void initDependency(Class<?> cls, T obj) {
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(AutoInject.class)) {
                Object bean = getBean(field.getType());
                if (obj == null) {
                    bean = initBean(field.getType());
                }
                field.setAccessible(true);
                try {
                    field.set(obj,bean);
                } catch (IllegalAccessException e) {
                    LOGGER.error("illegal access",e);
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static Set<Class<?>> getControllerSet() {
        return controllerSet;
    }

}
