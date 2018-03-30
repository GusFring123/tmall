package tmall.servlet;

import tmall.bean.Category;
import tmall.dao.CategoryDAO;
import tmall.util.ImageUtil;
import tmall.util.Page;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package: tmall.servlet
 * @Author: WangXu
 * @CreateDate: 2018/3/19 15:11
 * @Version: 1.0
 */

@WebServlet(name = "categoryServlet", urlPatterns = "/categoryServlet")
public class CategoryServlet extends BaseBackServlet {

    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        Map<String, String> params = new HashMap<>();
        InputStream inputStream = parseUpload(request, params);
        String name = params.get("name");
        Category category = new Category();
        category.setName(name);
        categoryDAO.add(category);

        File imageFolder = new File(request.getSession().getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, category.getId() + ".jpg");

        try {
            if (null != inputStream && 0 != inputStream.available()) {
                try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                    byte b[] = new byte[1024 * 1024];
                    int length = 0;
                    while (-1 != (length = inputStream.read(b))) {
                        fileOutputStream.write(b, 0, length);
                    }
                    fileOutputStream.flush();
                    //把文件保存成jpg格式
                    BufferedImage bufferedImage = ImageUtil.change2jpg(file);
                    ImageIO.write(bufferedImage, "jpg", file);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "@admin_category_list";
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        Map<String, String> params = new HashMap<>();
        InputStream inputStream = parseUpload(request, params);

        System.out.println(params);
        String name = params.get("name");
        int id = Integer.parseInt(params.get("id"));

        Category category = new Category();
        category.setId(id);
        category.setName(name);
        categoryDAO.update(category);

        File imageFolder = new File(request.getSession().getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, category.getId() + "jpg");
        file.getParentFile().mkdirs();

        try {
            if (null != inputStream && 0 != inputStream.available()) {
                try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                    byte b[] = new byte[1024 * 1024];
                    int length = 0;
                    while (-1 != (length = inputStream.read(b))) {
                        fileOutputStream.write(b, 0, length);
                    }
                    fileOutputStream.flush();

                    BufferedImage bufferedImage = ImageUtil.change2jpg(file);
                    ImageIO.write(bufferedImage, "jpg", file);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "@admin_category_list";
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        categoryDAO.delete(id);
        return "@admin_category_list";
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        Category category = categoryDAO.get(id);
        request.setAttribute("c", category);
        return "admin/editCategory.jsp";
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
        List<Category> categories = categoryDAO.list(page.getStart(), page.getCount());
        int total = categoryDAO.getTotal();
        page.setTotal(total);

        request.setAttribute("thecs", categories);
        request.setAttribute("page", page);

        return "admin/listCategory.jsp";
    }
}
