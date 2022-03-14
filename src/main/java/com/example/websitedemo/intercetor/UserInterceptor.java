package com.example.websitedemo.intercetor;

import com.example.websitedemo.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class UserInterceptor implements HandlerInterceptor {

    /*
     * 进入controller层之前拦截请求
     * 返回值：表示是否将当前的请求拦截下来  false：拦截请求，请求别终止。true：请求不被拦截，继续执行
     * Object obj:表示被拦的请求的目标对象（controller中方法）
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        System.out.println("preHandle works now");
        System.out.println(handler);
        User user = (User) request.getSession().getAttribute("session_user");
        String url = request.getRequestURI();
        boolean test =url.contains(".css")||url.contains(".js");
        System.out.println(url+" "+test);
        if(url.contains(".css") || url.contains(".js")||url.contains(".png")|url.contains(".jpg")) {
            System.out.println("do not need filter");
            return true;
        }

        if (user==null){
            StringBuffer fileURL = request.getRequestURL();
            System.out.println(fileURL.toString());
            if (fileURL.indexOf(".jpg") > 0 || fileURL.indexOf(".bmp") > 0
                    || fileURL.indexOf(".gif") > 0|| fileURL.indexOf(".js") > 0|| fileURL.indexOf(".css") > 0
                    || fileURL.indexOf("image.jsp") > 0){
                return true;
            }
            response.sendRedirect(request.getContextPath()+"/UserController/toIndex");//拦截后跳转的方法
            System.out.println("filter method works");
            return false;
        }
        System.out.println(user.getUserName());
        System.out.println("do not need filter");
        return true;
    }

    /*
     * 处理请求完成后视图渲染之前的处理操作
     * 通过ModelAndView参数改变显示的视图，或发往视图的方法
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        System.out.println("postHandle works");
    }

    /*
     * 视图渲染之后的操作
     */
    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
        System.out.println("afterCompletion works");
    }

}