package org.smartweb.web;

import org.apache.commons.lang3.StringUtils;
import org.smartweb.Annotation.Action;
import org.smartweb.container.BeanContainer;

import javax.servlet.Registration;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @PROJECT_NAME smartweb
 * @PACKAGE_NAME org.smartweb.web
 * @USER takou
 * @CREATE 2018/4/5
 **/
public final class WebController {

    private static final Map<Request,Handler> ACTION_MAP = new HashMap<Request, Handler>();

    private static final Set<Class<?>> controllerSet = BeanContainer.getControllerSet();
    static {
        //扫描所有controller，建立request和handler的对应关系
        for (Class<?> cls : controllerSet) {
            Method[] methods = cls.getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Action.class)) {
                    Action action = method.getAnnotation(Action.class);
                    String reqMethod = action.method();
                    String reqResource = action.resource();
                    Request request = new Request(reqMethod,reqResource);
                    Handler handler = new Handler(method,cls);
                    ACTION_MAP.put(request,handler);
                }
            }
        }
    }

    public static Handler getHandler(String method, String resource) {
        Request request = new Request(method,resource);
        return ACTION_MAP.get(request);
    }

    public static Map<Request, Handler> getActionMap() {
        return ACTION_MAP;
    }
}
