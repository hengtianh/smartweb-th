package org.smartweb.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartweb.Annotation.Aspect;
import org.smartweb.Annotation.Service;

import java.lang.reflect.Method;

@Aspect(Service.class)
public class TimeAspect extends AspectProxy {

    public static final Logger LOGGER = LoggerFactory.getLogger(TimeAspect.class);

    private long start;

    @Override
    protected void after(Class<?> targetClass, Method method, Object[] params) {
        start = System.currentTimeMillis();
    }

    @Override
    protected void before(Class<?> targetClass, Method method, Object[] params) {
        LOGGER.debug("exec " + method.getName() + " cost " + (System.currentTimeMillis() - start));
    }
}
