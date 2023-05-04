package com.hxh.basic.project.filter;

/**
 * @Author: ChenJian
 * @Date: 2021/5/21 18:15
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.DispatcherType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 该类是JWT过滤器，继承OncePerRequestFilter抽象类进行一些信息的过滤,每次请求只执行一次过滤
 */
//@EnableConfigurationProperties(DebugProperties.class)
@WebFilter(filterName = "JwtAuthenticationFilter", urlPatterns = "/*")    //配置过滤器名称和拦截的路径
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LogManager.getLogger(JwtAuthenticationFilter.class);

    public final void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //进行逻辑判断过滤
        if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
            throw new ServletException("OncePerRequestFilter just supports HTTP requests");
        }

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        //这里获取一个名称，该名称后面会被用于放到request当作key
        String alreadyFilteredAttributeName = getAlreadyFilteredAttributeName();
        //检测当前请求是否已经拥有了该标记，如果拥有该标记则代表该过滤器执行过了（后面注释有说明）
        boolean hasAlreadyFilteredAttribute = request.getAttribute(alreadyFilteredAttributeName) != null;



        if (shouldNotFilter(httpRequest)) {

            // Proceed without invoking this filter...
            //放行
            filterChain.doFilter(request, response);
        }

        //如果此过滤器已经被执行过则执行如下的逻辑
        else if (hasAlreadyFilteredAttribute) {

            if (DispatcherType.ERROR.equals(request.getDispatcherType())) {
                doFilterNestedErrorDispatch(httpRequest, httpResponse, filterChain);
                return;
            }

            // Proceed without invoking this filter...
            filterChain.doFilter(request, response);
        }
        //走到这里说明该过滤器没有被执行过
        else {
            // 在当前请求里面设置一个标记，key就是前面拼接的那个变量，value是true，这个标记如果在request存在则在前面会被检测到并改变hasAlreadyFilteredAttribute的值
            request.setAttribute(alreadyFilteredAttributeName, Boolean.TRUE);
            try {
                // 这个方法是一个抽象方法需要子类去实现具体的过滤逻辑
                doFilterInternal(httpRequest, httpResponse, filterChain);
            }
            finally {
                // Remove the "already filtered" request attribute for this request.
                // 执行完毕之后移除该标记
                request.removeAttribute(alreadyFilteredAttributeName);
            }
        }

        //放行
        filterChain.doFilter(request, response);  //放行

    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    }
}
