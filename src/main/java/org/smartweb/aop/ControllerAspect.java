package org.smartweb.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartweb.Annotation.Aspect;
import org.smartweb.Annotation.Service;

import java.lang.reflect.Method;

@Aspect(Service.class)
public class ControllerAspect extends AspectProxy {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAspect.class);
    @Override
    protected void after(Class<?> targetClass, Method method, Object[] params) {
        LOGGER.debug("after---\r\n" + "---------" + targetClass + "---------\r\n" + "---------" + method.getName() + "---------\r\n" + "---------" + params.toString() + "---------");
    }

    @Override
    protected void before(Class<?> targetClass, Method method, Object[] params) {
        LOGGER.debug("before---\r\n" + "---------" + targetClass + "---------\r\n" + "---------" + method.getName() + "---------\r\n" + "---------" + params.toString() + "---------");
    }
}
