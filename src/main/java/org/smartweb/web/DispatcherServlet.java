package org.smartweb.web;

import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartweb.container.BeanContainer;
import org.smartweb.container.BeanContainerBuilder;
import org.smartweb.util.ConfigUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @PROJECT_NAME smartweb
 * @PACKAGE_NAME org.smartweb.web
 * @USER takou
 * @CREATE 2018/4/5
 **/
@WebServlet(loadOnStartup = 0,urlPatterns = "*.do")
public class DispatcherServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(DispatcherServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        BeanContainerBuilder.buildWebContainer();
        ServletContext context = config.getServletContext();
        ServletRegistration jsp = context.getServletRegistration("jsp");
        jsp.addMapping(ConfigUtil.getJspPath() + "*");
        ServletRegistration asset = context.getServletRegistration("default");
        asset.addMapping(ConfigUtil.getAsset() + "*");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获得请求的方法和请求的资源
        String method = req.getMethod().toLowerCase();
        String resource = req.getServletPath();

        //获得处理请求的action
        Handler handler = WebController.getHandler(method,resource);
        if (handler == null) {
            handler = WebController.getHandler("get","/customer/goToIndex");
        }
        Object obj = BeanContainer.getBean(handler.getCls());
        Method reqMethod = handler.getMethod();

        //拦截请求参数，进行封装
        Map<String,String[]> param = req.getParameterMap();

        //调用目标方法
        Object object = null;
        try {
            ModelAndView view = new ModelAndView();
            view.setReqParam(param);
            object = reqMethod.invoke(obj,view);
        } catch (IllegalAccessException e) {
            LOGGER.error(reqMethod.getName() + " illegal access",e);
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            LOGGER.error(reqMethod.getName() + " invoke failure",e);
            throw new RuntimeException(e);
        }

        //处理调用后的返回结果,进行封装
        if (object instanceof ModelAndView) {
            ModelAndView view = (ModelAndView) object;
            view.setRequest(req);
            view.setResponse(resp);
            view.forward();
        } else if (object instanceof Data) {
            //处理json数据...
            Data data = (Data) object;
            //Object jsonObj = data.getModel();
            //String json = JSONValue.toJSONString(jsonObj);
            resp.setCharacterEncoding("utf-8");
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            //JSONValue.writeJSONString(data.getModel().toString());
            writer.write(data.getModel().toString());
            writer.flush();
            writer.close();
        }
    }
}
