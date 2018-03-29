package tmall.filter;

import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Package: tmall.filter
 * @Author: WangXu
 * @CreateDate: 2018/3/19 11:20
 * @Version: 1.0
 */

public class BackServletFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String contextPath = request.getServletContext().getContextPath();//        /tmall
        String uri = request.getRequestURI();  //    /tmall/admin_category_add
        uri = StringUtils.remove(uri, contextPath);

        if (uri.startsWith("/admin_")) {
            String servletPath = StringUtils.substringBetween(uri, "_", "_") + "Servlet";  //    category
            String method = StringUtils.substringAfterLast(uri, "_");    //list
            request.setAttribute("method", method);
            servletRequest.getRequestDispatcher("/" + servletPath).forward(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
