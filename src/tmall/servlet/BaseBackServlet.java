package tmall.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import tmall.dao.*;
import tmall.util.Page;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Package: tmall.servlet
 * @Author: WangXu
 * @CreateDate: 2018/3/19 15:04
 * @Version: 1.0
 */

public abstract class BaseBackServlet extends HttpServlet {

    public abstract String add(HttpServletRequest request, HttpServletResponse response, Page page);

    public abstract String update(HttpServletRequest request, HttpServletResponse response, Page page);

    public abstract String delete(HttpServletRequest request, HttpServletResponse response, Page page);

    public abstract String edit(HttpServletRequest request, HttpServletResponse response, Page page);

    public abstract String list(HttpServletRequest request, HttpServletResponse response, Page page);

    protected CategoryDAO categoryDAO = new CategoryDAO();
    protected OrderDAO orderDAO = new OrderDAO();
    protected OrderItemDAO orderItemDAO = new OrderItemDAO();
    protected ProductDAO productDAO = new ProductDAO();
    protected ProductImageDAO productImageDAO = new ProductImageDAO();
    protected PropertyDAO propertyDAO = new PropertyDAO();
    protected PropertyValueDAO propertyValueDAO = new PropertyValueDAO();
    protected ReviewDAO reviewDAO = new ReviewDAO();
    protected UserDAO userDAO = new UserDAO();

    public void service(HttpServletRequest request, HttpServletResponse response) {
        try {
            //获取分页信息
            int start = 0;
            int count = 5;
            //异常捕捉是考虑到不传参数的时候
            try {
                start = Integer.parseInt(request.getParameter("page.start"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                count = Integer.parseInt(request.getParameter("page.count"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            Page page = new Page(start, count);

            //借助反射，调用对应的方法
            String method = (String) request.getAttribute("method");
            Method m = this.getClass().getMethod(method, HttpServletRequest.class, HttpServletResponse.class, Page.class);
            String redirect = m.invoke(this, request, response, page).toString();

            //根据方法的返回值，进行相应的客户端跳转，服务器跳转，或者是仅仅是输出字符串
            if (redirect.startsWith("@")) {
                response.sendRedirect(redirect.substring(1));
            } else if (redirect.startsWith("%")) {
                response.getWriter().print(redirect.substring(1));
            } else {
                //服务器端跳转
                request.getRequestDispatcher(redirect).forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 文件上传解析
     * 判断是否是上传的流，若是则获取输入流
     *
     * @param request
     * @param params
     * @return
     */
    public InputStream parseUpload(HttpServletRequest request, Map<String, String> params) {
        InputStream inputStream = null;
        try {
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            ServletFileUpload servletFileUpload = new ServletFileUpload();
            //设置上传文件的大小限制为10m
            diskFileItemFactory.setSizeThreshold(1024 * 10240);
            List items = servletFileUpload.parseRequest(request);
            Iterator iterator = items.iterator();
            while (iterator.hasNext()) {
                FileItem item = (FileItem) iterator.next();
                if (!item.isFormField()) {
                    //获取上传文件的输入流
                    inputStream = item.getInputStream();
                } else {
                    String paramName = item.getFieldName();
                    String paramValue = item.getString();
                    paramValue = new String(paramValue.getBytes("ISO-8859-1"), "UTF-8");
                    params.put(paramName, paramValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputStream;
    }
}
