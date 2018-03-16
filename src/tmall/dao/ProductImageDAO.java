package tmall.dao;

import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Package: tmall.dao
 * @Author: WangXu
 * @CreateDate: 2018/3/9 16:20
 * @Version: 1.0
 */

public class ProductImageDAO {
    public static final String type_single = "type_single";
    public static final String type_detail = "type_detail";

    /**
     * 获取总数
     *
     * @return
     */
    public int getTotal() {
        int total = 0;
        try (
                Connection connection = DBUtil.getConnection();
                Statement statement = connection.createStatement();
        ) {
            String sql = "select count(*) from productimage";
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                total = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    /**
     * 增加ProductImage
     *
     * @param productImage
     */
    public void add(ProductImage productImage) {
        String sql = "insert into productimage values(null, ?, ?)";
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, productImage.getProduct().getId());
            preparedStatement.setString(2, productImage.getType());
            preparedStatement.execute();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt("id");
                productImage.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新ProductImage
     *
     * @param productImage
     */
    public void update(ProductImage productImage) {

    }

    /**
     * 删除ProductImage
     *
     * @param id
     */
    public void delete(int id) {
        try (
                Connection connection = DBUtil.getConnection();
                Statement statement = connection.createStatement();
        ) {
            String sql = "delete from productimage where id =" + id;
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取ProductImage
     *
     * @param id
     * @return
     */
    public ProductImage get(int id) {
        ProductImage productImage = null;
        try (
                Connection connection = DBUtil.getConnection();
                Statement statement = connection.createStatement();
        ) {
            String sql = "select * from productimage where id=" + id;
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                productImage = new ProductImage();
                productImage.setId(id);
                productImage.setProduct(new ProductDAO().get(resultSet.getInt("pid")));
                productImage.setType(resultSet.getString("type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productImage;
    }

    /**
     * 获取ProductImage列表
     *
     * @param product
     * @param type
     * @param start
     * @param count
     * @return
     */
    public List<ProductImage> list(Product product, String type, int start, int count) {
        List<ProductImage> productImages = new ArrayList<>();
        String sql = "SELECT * FROM productimage WHERE pid = ? AND type = ? ORDER BY id DESC limit ?, ?";
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, product.getId());
            preparedStatement.setString(2, type);
            preparedStatement.setInt(3, start);
            preparedStatement.setInt(4, count);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ProductImage productImage = new ProductImage();
                productImage.setId(resultSet.getInt("id"));
                productImage.setType(resultSet.getString("type"));
                productImage.setProduct(new ProductDAO().get(resultSet.getInt("pid")));
                productImages.add(productImage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productImages;
    }

    /**
     * 获取ProductImage列表
     *
     * @param product
     * @param type
     * @return
     */
    public List<ProductImage> list(Product product, String type) {
        return list(product, type, 0, Short.MAX_VALUE);
    }

}
