package com.ddfantasy.todoapp.filter;


import com.alibaba.fastjson.JSON;
import com.ddfantasy.todoapp.common.BaseContext;
import com.ddfantasy.todoapp.common.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 检查用户是否登录
 */
@Slf4j
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

//    路径匹配器
    public static final AntPathMatcher PATH_MATCHER=new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpServletResponse response=(HttpServletResponse)servletResponse;
        //前端：8081，解决跨域问题
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        //        log.info("request---getRequestURI {}",request.getRequestURI());

//        不需要处理的请求路径
        String[] urls=new String[]{
                "/user/login",
                "/user/logout",
//                "/backend/**",
//                "/front/**",
                "/common/**",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources",
                "/v2/api-docs"
        };

        String requestURI = request.getRequestURI();

        //不需要拦截的请求
        boolean check = check(urls, requestURI);
        if(check){
            log.info("不需要拦截！");
            filterChain.doFilter(request,response);
            return;
        }

//        后台端已登录
        if(request.getSession().getAttribute("employee")!=null){
            log.info("用户已登录---{}",request.getSession().getAttribute("employee"));

//            保存id到线程中
            Integer id=(Integer)request.getSession().getAttribute("employee");
            BaseContext.setCurrendId(id);
            filterChain.doFilter(request,response);
            return;
        }

//        移动端端已登录
        if(request.getSession().getAttribute("user")!=null){
            log.info("用户已登录---{}",request.getSession().getAttribute("user"));

//            保存id到线程中
            Integer userId=(Integer)request.getSession().getAttribute("user");
            BaseContext.setCurrendId(userId);
            filterChain.doFilter(request,response);
            return;
        }

//      通过输出求写入R类型的json数据并返回response
        log.info("用户未登录！");
        response.getWriter().write(JSON.toJSONString(ResultData.error("NOTLOGIN")));
        return;
    }

    /**
     * @description: 路径匹配
     * @params: uris,requestURI
     */
    public boolean check(String[] uris,String requestURI){
        for (String uri : uris) {
            boolean match = PATH_MATCHER.match(uri, requestURI);
            if(match){
                return true;
            }
        }
        return false;

    }
}
