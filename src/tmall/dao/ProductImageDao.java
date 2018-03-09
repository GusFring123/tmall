package tmall.dao;

import tmall.bean.ProductImage;
import tmall.util.DBUtil;

import java.sql.*;

/**
 * @Package: tmall.dao
 * @Author: WangXu
 * @CreateDate: 2018/3/9 16:20
 * @Version: 1.0
 */

public class ProductImageDao {
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

    public void update(ProductImage productImage) {

    }

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
 * 待续
 */
}
