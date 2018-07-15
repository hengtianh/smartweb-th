package org.smartweb.container;

import org.smartweb.Annotation.Aspect;
import org.smartweb.aop.AspectProxy;
import org.smartweb.aop.Proxy;
import org.smartweb.aop.ProxyFactory;

import java.lang.annotation.Annotation;
import java.util.*;

public class AopBuilder {

    static Map<Class<?>, Set<Class>> proxyMap = new HashMap<>();
    static Map<Class, List<Proxy>> aopMap = new HashMap<>();

    //初始化aop框架
    static {
        try {
            createProxyMap();
            createAopMap();
            createProxy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createProxyMap() {
        //拿到aspectSet中注解的类标识和aspect的对应关系
        for (Class cls : BeanContainer.aspectSet) {
            if (!AspectProxy.class.isAssignableFrom(cls)) {
                return;
            }
            Aspect aspect = (Aspect) cls.getAnnotation(Aspect.class);
            //切面拦截的目标类标识
            Class<?> clazz = aspect.value();
            //创建目标类实例，建立与切面的映射
            proxyMap.put(cls,createTargetClassSet(clazz));
        }
    }

    private static Set<Class> createTargetClassSet(Class<?> aspect) {
        Set<Class> set = new HashSet<>();
        for (Class targetClass : BeanContainer.beanSet) {
            if (targetClass.isAnnotationPresent(aspect)) {
                set.add(targetClass);
            }
        }
        return set;
    }

    private static void createAopMap() throws IllegalAccessException, InstantiationException {

        //map中，切面与目标类 1:n，不同切面对应相同的目标类
        for (Map.Entry<Class<?>, Set<Class>> entry : proxyMap.entrySet()) {
            Class proxyClass = entry.getKey();
            Set<Class> targetSet =  entry.getValue();
            for (Class targetClass : targetSet) {
                Proxy proxy = (Proxy) proxyClass.newInstance();
                if (aopMap.containsKey(targetClass)) {
                    //实际拦截时，目标类与切面类  1:n
                    aopMap.get(targetClass).add(proxy);
                } else {
                    List<Proxy> list = new ArrayList<>();
                    list.add(proxy);
                    aopMap.put(targetClass, list);
                }
            }
        }
    }

    private static void createProxy() {
        for (Map.Entry<Class,List<Proxy>> entry : aopMap.entrySet()) {
            Class targetClass = entry.getKey();
            List<Proxy> list = entry.getValue();
            Object proxy = ProxyFactory.createProxy(targetClass, list);
            BeanContainer.beanMap.put(targetClass, proxy);
        }
    }
}
