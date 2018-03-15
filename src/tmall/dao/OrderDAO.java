package tmall.dao;

import tmall.bean.Order;
import tmall.util.DBUtil;
import tmall.util.DateUtil;

import java.sql.*;

/**
 * @Package: tmall.dao
 * @Author: WangXu
 * @CreateDate: 2018/3/15 15:46
 * @Version: 1.0
 */

public class OrderDAO {
    public static final String waitPay = "waitPAy";
    public static final String waitDelivery = "waitDelivery";
    public static final String waitConfirm = "waitConfirm";
    public static final String waitReview = "waitReview";
    public static final String finish = "finish";
    public static final String delete = "delete";

    public int getTotal() {
        int total = 0;
        try (
                Connection connection = DBUtil.getConnection();
                Statement statement = connection.createStatement()
        ) {
            String sql = "SELECT COUNT(*) FROM order_";
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                total = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    /*

    String sql = "";
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {


        } catch (SQLException e) {
            e.printStackTrace();
        }

    */

    public void add(Order order) {
        String sql = "INSERT INTO order_ VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,)";
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, order.getOrderCode());
            preparedStatement.setString(2, order.getAddress());
            preparedStatement.setString(3, order.getPost());
            preparedStatement.setString(4, order.getReceiver());
            preparedStatement.setString(5, order.getMobile());
            preparedStatement.setString(6, order.getUserMessage());

            preparedStatement.setTimestamp(7, DateUtil.d2t(order.getCreateDate()));
            preparedStatement.setTimestamp(8, DateUtil.d2t(order.getPayDate()));
            preparedStatement.setTimestamp(9, DateUtil.d2t(order.getDeliveryDate()));
            preparedStatement.setTimestamp(10, DateUtil.d2t(order.getConfirmDate()));

            preparedStatement.setInt(11, order.getUser().getId());
            preparedStatement.setString(12, order.getStatus());

            preparedStatement.execute();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                order.setId(id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Order order) {
        String sql = "UPDATE order_ SET orderCode = ?, address = ?, post = ?, receiver = ?, mobile = ?, userMessage = ?, createDate = ?, payDate = ? deliveryDate = ?,confirmDate = ?, uid = ?, status = ? WHERE id = ?";
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, order.getOrderCode());
            preparedStatement.setString(2, order.getAddress());
            preparedStatement.setString(3, order.getPost());
            preparedStatement.setString(4, order.getReceiver());
            preparedStatement.setString(5, order.getMobile());
            preparedStatement.setString(6, order.getUserMessage());

            preparedStatement.setTimestamp(7, DateUtil.d2t(order.getCreateDate()));
            preparedStatement.setTimestamp(8, DateUtil.d2t(order.getPayDate()));
            preparedStatement.setTimestamp(9, DateUtil.d2t(order.getDeliveryDate()));
            preparedStatement.setTimestamp(10, DateUtil.d2t(order.getConfirmDate()));

            preparedStatement.setInt(11, order.getUser().getId());
            preparedStatement.setString(12, order.getStatus());
            preparedStatement.setInt(13, order.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try (
                Connection connection = DBUtil.getConnection();
                Statement statement = connection.createStatement()

        ) {
            String sql = "DELETE FROM order_ WHERE id=" + id;
            statement.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
