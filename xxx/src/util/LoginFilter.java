/**
 * 
 */
package util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init LoginFilter");
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        //把ServletRequest和ServletResponse转换成真正的类型
        HttpServletRequest req = (HttpServletRequest)request;
        HttpSession session = req.getSession();

        //由于web.xml中设置Filter过滤全部请求，可以排除不需要过滤的url
        String requestURI = req.getRequestURI();
        if(!requestURI.contains("model!")){
            chain.doFilter(request, response);
            return;
        }

        //判断用户是否登录且具有权限，进行页面的处理
        if(null == session.getAttribute("username")||(Integer)session.getAttribute("level")!=0){
            //未登录用户，重定向到登录页面
        	String path = req.getContextPath();
        	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
            ((HttpServletResponse)response).sendRedirect(basePath+"model/model_authentication.jsp");
            return;
        } else {
            //已登录用户且具有权限，允许访问
            chain.doFilter(request, response);
        }
    }

    public void destroy() {
        System.out.println("destroy!!!");
    }
}