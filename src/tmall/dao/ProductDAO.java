package tmall.dao;

import tmall.bean.Category;
import tmall.bean.Product;
import tmall.util.DBUtil;
import tmall.util.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Package: tmall.dao
 * @Author: WangXu
 * @CreateDate: 2018/3/16 16:41
 * @Version: 1.0
 */

public class ProductDAO {

    /**
     * 查询总数
     *
     * @return
     */
    public int getTotal() {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM product";
        try (
                Connection connection = DBUtil.getConnection();
                Statement statement = connection.createStatement();
        ) {
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
     * 增加Product
     *
     * @param product
     */
    public void add(Product product) {
        String sql = "INSERT INTO product VALUES (NULL, ?, ?, ?, ?, ?, ?, ?)";
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getSubTitle());
            preparedStatement.setFloat(3, product.getOriginalPrice());
            preparedStatement.setFloat(4, product.getPromotePrice());
            preparedStatement.setInt(5, product.getStock());
            preparedStatement.setInt(6, product.getCategory().getId());
            preparedStatement.setTimestamp(7, DateUtil.d2t(product.getCreateDate()));

            preparedStatement.execute();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                product.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新Product
     *
     * @param product
     */
    public void update(Product product) {
        String sql = "UPDATE product SET name = ?, subTitle = ?, originPrice = ?, promotePrice = ?, stock = ?, cid = ?, createDate = ? WHERE id = ?";
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getSubTitle());
            preparedStatement.setFloat(3, product.getOriginalPrice());
            preparedStatement.setFloat(4, product.getPromotePrice());
            preparedStatement.setInt(5, product.getStock());
            preparedStatement.setInt(6, product.getCategory().getId());
            preparedStatement.setTimestamp(7, DateUtil.d2t(product.getCreateDate()));
            preparedStatement.setInt(8, product.getId());

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除Product
     *
     * @param id
     */
    public void delete(int id) {
        String sql = "DELETE FROM product WHERE id = ?";
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取Product
     *
     * @param id
     * @return
     */
    public Product get(int id) {
        Product product = null;
        String sql = "SELECT * FROM product WHERE id = ?";
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            String name = resultSet.getString("name");
            String subTitle = resultSet.getString("subTitle");
            float originPrice = resultSet.getFloat("originPrice");
            float promotePrice = resultSet.getFloat("promotePrice");
            int stock = resultSet.getInt("stock");
            int cid = resultSet.getInt("cid");
            Date createDate = DateUtil.t2d(resultSet.getTimestamp("createDate"));
            Category category = new CategoryDAO().get(cid);

            product = new Product();
            product.setId(id);
            product.setName(name);
            product.setSubTitle(subTitle);
            product.setOriginalPrice(originPrice);
            product.setPromotePrice(promotePrice);
            product.setStock(stock);
            product.setCategory(category);
            product.setCreateDate(createDate);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    /**
     * 获取Product列表
     *
     * @param start
     * @param count
     * @return
     */
    public List<Product> list(int start, int count) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM product ORDER BY id DESC limit ?, ?";
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, start);
            preparedStatement.setInt(2, count);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();

                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String subTitle = resultSet.getString("subTitle");
                float originPrice = resultSet.getFloat("originPrice");
                float promotePrice = resultSet.getFloat("promotePrice");
                int stock = resultSet.getInt("stock");
                int cid = resultSet.getInt("cid");
                Date createDate = DateUtil.t2d(resultSet.getTimestamp("createDate"));
                Category category = new CategoryDAO().get(cid);

                product.setId(id);
                product.setName(name);
                product.setSubTitle(subTitle);
                product.setOriginalPrice(originPrice);
                product.setPromotePrice(promotePrice);
                product.setStock(stock);
                product.setCategory(category);
                product.setCreateDate(createDate);

                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    /**
     * 获取Product列表
     *
     * @return
     */
    public List<Product> list() {
        return list(0, Short.MAX_VALUE);
    }

    /**
     * 获取Product列表
     *
     * @param cid
     * @param start
     * @param count
     * @return
     */
    public List<Product> list(int cid, int start, int count) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE cid = ? ORDER BY id DESC limit ?, ?";
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, cid);
            preparedStatement.setInt(2, start);
            preparedStatement.setInt(3, count);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();

                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String subTitle = resultSet.getString("subTitle");
                float originPrice = resultSet.getFloat("originPrice");
                float promotePrice = resultSet.getFloat("promotePrice");
                int stock = resultSet.getInt("stock");
                Date createDate = DateUtil.t2d(resultSet.getTimestamp("createDate"));
                Category category = new CategoryDAO().get(cid);

                product.setId(id);
                product.setName(name);
                product.setSubTitle(subTitle);
                product.setOriginalPrice(originPrice);
                product.setPromotePrice(promotePrice);
                product.setStock(stock);
                product.setCategory(category);
                product.setCreateDate(createDate);

                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    /**
     * 获取Product列表
     *
     * @param cid
     * @return
     */
    public List<Product> list(int cid) {
        return list(cid, 0, Short.MAX_VALUE);
    }

}
