package org.smartweb.aop;

import java.lang.reflect.Method;

public abstract class AspectProxy implements Proxy {

    @Override
    public final Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;
        Class<?> targetClass = proxyChain.getTargetClass();
        Method method = proxyChain.getMethod();
        Object[] params = proxyChain.getParams();
        begin();
        try {
            if (intercept(targetClass, method, params)) {
                before(targetClass, method, params);
                result = proxyChain.doProxyChain();
                after(targetClass, method, params);
            } else {
                result = proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            error(targetClass, method, params, e);
            e.printStackTrace();
            throw e;
        } finally {
            end();
        }
        return result;
    }

    protected abstract void after(Class<?> targetClass, Method method, Object[] params);

    protected abstract void before(Class<?> targetClass, Method method, Object[] params);

    private boolean intercept(Class<?> targetClass, Method method, Object[] params) {
        return true;
    }

    private void begin() {
    }

    private void end() {
    }

    private void error(Class<?> targetClass, Method method, Object[] params, Throwable e) {
    }


}
