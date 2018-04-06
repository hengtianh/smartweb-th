package org.smartweb.web;

import java.lang.reflect.Method;

/**
 * @PROJECT_NAME smartweb
 * @PACKAGE_NAME org.smartweb.web
 * @USER takou
 * @CREATE 2018/4/5
 **/
public class Handler {

    private Method method;

    private Class<?> cls;

    public Handler(Method method, Class<?> cls) {
        this.method = method;
        this.cls = cls;
    }

    public Method getMethod() {
        return method;
    }

    public Class<?> getCls() {
        return cls;
    }
}
