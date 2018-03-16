package tmall.dao;

import tmall.bean.Order;
import tmall.bean.OrderItem;
import tmall.bean.Product;
import tmall.bean.User;
import tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Package: tmall.dao
 * @Author: WangXu
 * @CreateDate: 2018/3/16 10:23
 * @Version: 1.0
 */

public class OrderItemDAO {
    public int getTotal() {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM orderitem";
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

    /*

    String sql = "";
            try (
                    Connection connection = DBUtil.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ) {

            } catch (SQLException e) {
                e.printStackTrace();
            }



     */
    public void add(OrderItem orderItem) {
        String sql = "INSERT INTO orderitem VALUES (NULL, ?, ?, ?, ?)";
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, orderItem.getProduct().getId());
            preparedStatement.setInt(2, orderItem.getOrder().getId());
            preparedStatement.setInt(3, orderItem.getUser().getId());
            preparedStatement.setInt(4, orderItem.getNumber());

            preparedStatement.execute();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                orderItem.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(OrderItem orderItem) {
        String sql = "UPDATE orderitem SET pid = ?, oid = ?, uid = ?, number = ? WHERE id = ?";
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, orderItem.getProduct().getId());
            preparedStatement.setInt(2, orderItem.getOrder().getId());
            preparedStatement.setInt(3, orderItem.getUser().getId());
            preparedStatement.setInt(4, orderItem.getNumber());
            preparedStatement.setInt(5, orderItem.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM orderitem WHERE id = ?";
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

    public OrderItem get(int id) {
        OrderItem orderItem = null;
        String sql = "SELECT * FROM orderitem WHERE id = ?";
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                orderItem = new OrderItem();
                orderItem.setId(id);
                orderItem.setNumber(resultSet.getInt("number"));
                orderItem.setOrder(new OrderDAO().get(resultSet.getInt("oid")));
                orderItem.setProduct(new ProductDAO().get(resultSet.getInt("pid")));
                orderItem.setUser(new UserDAO().get(resultSet.getInt("uid")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItem;
    }

    public List<OrderItem> listByUser(int uid, int start, int count) {
        List<OrderItem> orderItems = new ArrayList<>();
        String sql = "SELECT * FROM  orderitem WHERE uid = ? ORDER BY id DESC limit ?, ?";
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, uid);
            preparedStatement.setInt(2, start);
            preparedStatement.setInt(3, count);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                OrderItem orderItem = new OrderItem();
                User user = new UserDAO().get(uid);
                Order order = new OrderDAO().get(resultSet.getInt("oid"));
                Product product = new ProductDAO().get(resultSet.getInt("pid"));
                int number = resultSet.getInt("number");
                int id = resultSet.getInt("id");
                orderItem.setUser(user);
                orderItem.setProduct(product);
                orderItem.setOrder(order);
                orderItem.setNumber(number);
                orderItem.setId(id);
                orderItems.add(orderItem);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItems;
    }

    public List<OrderItem> listByUser(int uid) {
        return listByUser(uid, 0, Short.MAX_VALUE);
    }

    public List<OrderItem> listByOrder(int oid, int start, int count) {
        List<OrderItem> orderItems = new ArrayList<>();
        String sql = "SELECT * FROM  orderitem WHERE oid = ? ORDER BY id DESC limit ?, ?";
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, oid);
            preparedStatement.setInt(2, start);
            preparedStatement.setInt(3, count);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                OrderItem orderItem = new OrderItem();
                User user = new UserDAO().get(resultSet.getInt("uid"));
                Order order = new OrderDAO().get(oid);
                Product product = new ProductDAO().get(resultSet.getInt("pid"));
                int number = resultSet.getInt("number");
                int id = resultSet.getInt("id");
                orderItem.setUser(user);
                orderItem.setProduct(product);
                orderItem.setOrder(order);
                orderItem.setNumber(number);
                orderItem.setId(id);
                orderItems.add(orderItem);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItems;
    }

    public List<OrderItem> listByOrder(int oid) {
        return listByOrder(oid, 0, Short.MAX_VALUE);
    }

    public List<OrderItem> listByProduct(int pid, int start, int count) {
        List<OrderItem> orderItems = new ArrayList<>();
        String sql = "SELECT * FROM  orderitem WHERE pid = ? ORDER BY id DESC limit ?, ?";
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, pid);
            preparedStatement.setInt(2, start);
            preparedStatement.setInt(3, count);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                OrderItem orderItem = new OrderItem();
                User user = new UserDAO().get(resultSet.getInt("uid"));
                Order order = new OrderDAO().get(resultSet.getInt("oid"));
                Product product = new ProductDAO().get(pid);
                int number = resultSet.getInt("number");
                int id = resultSet.getInt("id");
                orderItem.setUser(user);
                orderItem.setProduct(product);
                orderItem.setOrder(order);
                orderItem.setNumber(number);
                orderItem.setId(id);
                orderItems.add(orderItem);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItems;
    }

    public List<OrderItem> listByProduct(int pid) {
        return listByProduct(pid, 0, Short.MAX_VALUE);
    }

}
