package com.n.controller;

import com.n.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登陆检查，
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {
    //目标方法执行之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String username = (String)request.getSession().getAttribute("username");
        System.out.println("--------------------" + request.getRequestURI());
        System.out.println("--------------------" + request.getContextPath());
        String uri = request.getRequestURI();
        if (uri.contains("/code/findAll.do") || uri.contains("/code/save.do") || uri.contains("/code/findById.do")) {
            return true;
        }
        if(username == null){
            //未登陆，返回登陆页面
            User user = new User();
            user.setMessage("没有权限请先登陆");
            request.setAttribute("user", user);
            request.getRequestDispatcher("/index.jsp").forward(request,response);
            return false;
        }else{
            //已登陆，放行请求
            return true;
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
