package com.money.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object userName = request.getSession().getAttribute("userName");
        System.out.println(userName);
        if (null != userName && !" ".equals(userName)){
            //userName存在返回true 放行
            return true;
        }else {
            request.getRequestDispatcher("/center/login").forward(request,response);
            return false;
        }

    }

    //controller方法执行后，页面执行之前执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
}
