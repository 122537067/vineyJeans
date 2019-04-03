package com.hwx.viney.config;

import com.hwx.viney.entity.Manager;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author onee
 * @date 2018/12/3 0003 上午 9:01
 */
public class MyInterceptor implements HandlerInterceptor {

    //在请求处理之前进行调用（Controller方法调用之前
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        Manager manager= (Manager) httpServletRequest.getSession().getAttribute("manager");
        if(manager!=null){
            return true;
        }
        httpServletResponse.sendRedirect("/login.html");
        return false;
    }
}
