package org.smartweb.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 代理工厂类
 */
public class ProxyFactory {

    private static ProxyChain chain;

    @SuppressWarnings("unchecked")
    public static <T> T createProxy(Class<?> targetClass, List<Proxy> proxyList) {
        return (T) Enhancer.create(targetClass, (MethodInterceptor) (obj, method, objects, methodProxy) -> {
            if (chain == null) {
                chain = new ProxyChain(targetClass, obj, method, methodProxy, objects, proxyList);
            }
            return chain.doProxyChain();
        });
    }
}
