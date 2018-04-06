package org.smartweb.web;


import org.smartweb.util.ConfigUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @PROJECT_NAME smartweb
 * @PACKAGE_NAME org.smartweb.web
 * @USER takou
 * @CREATE 2018/4/5
 **/
public class ModelAndView {

    private HttpServletRequest request;

    private HttpServletResponse response;

    private String resPath;

    private Map<String,String[]> param;

    private Map<String,Object> models = new HashMap<String, Object>();

    public ModelAndView(HttpServletRequest request, String resPath) {
        this.request = request;
        this.resPath = resPath;
    }

    public ModelAndView() {
    }

    public void forward() {
        if (resPath.startsWith("/")) {
            try {
                response.sendRedirect(request.getContextPath() + resPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            setAttribute();
            try {
                request.getRequestDispatcher(ConfigUtil.getJspPath() + resPath).forward(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setAttribute() {
        for (Map.Entry<String,Object> entry : models.entrySet()) {
            request.setAttribute(entry.getKey(),entry.getValue());
        }
    }

    public void addModel(String key, Object obj) {
        models.put(key,obj);
    }

    public String getResPath() {
        return resPath;
    }

    public void setResPath(String resPath) {
        this.resPath = resPath;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public void setParam(Map<String, String[]> param) {
        this.param = param;
    }
}
