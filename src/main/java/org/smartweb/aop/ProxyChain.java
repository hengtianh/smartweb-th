package org.smartweb.aop;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 代理链
 */
public class ProxyChain {
    private final Class<?> targetClass;
    private final Object target;
    private final Method method;
    private final MethodProxy methodProxy;
    private final Object[] params;

    private List<Proxy> proxyList = new ArrayList<>();
    private int proxyIndex = 0;

    public ProxyChain(Class<?> targetClass, Object target, Method method, MethodProxy methodProxy, Object[] params, List<Proxy> proxyList) {
        this.targetClass = targetClass;
        this.target = target;
        this.method = method;
        this.methodProxy = methodProxy;
        this.params = params;
        this.proxyList = proxyList;
    }

    /**
     * 执行代理链的切面
     * @return Object
     * @throws Throwable
     */
    public Object doProxyChain() throws Throwable {
        Object proxyResult;
        //先调用切面类组成的代理链
        if (proxyIndex < proxyList.size()) {
            proxyResult = proxyList.get(proxyIndex++).doProxy(this);
        } else {
            //调用目标类的目标方法
            proxyResult = methodProxy.invokeSuper(target, params);
        }
        return proxyResult;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Object getTarget() {
        return target;
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getParams() {
        return params;
    }
}
