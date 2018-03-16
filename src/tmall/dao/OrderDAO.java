package tmall.dao;

import tmall.bean.Order;
import tmall.bean.User;
import tmall.util.DBUtil;
import tmall.util.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

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

    /**
     * 获取总数
     *
     * @return
     */
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

    /**
     * 添加Order
     *
     * @param order
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

    /**
     * 更新Order
     *
     * @param order
     */
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

    /**
     * 删除Order
     *
     * @param id
     */
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

    /**
     * 查询Order
     *
     * @param id
     * @return
     */
    public Order get(int id) {
        Order order = null;
        try (
                Connection connection = DBUtil.getConnection();
                Statement statement = connection.createStatement();
        ) {
            String sql = "SELECT * FROM order_ WHERE  id = " + id;
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                order = new Order();
                order.setId(resultSet.getInt("id"));
                order.setAddress(resultSet.getString("address"));
                order.setMobile(resultSet.getString("mobile"));

                order.setConfirmDate(DateUtil.t2d(resultSet.getTimestamp("confirmDate")));
                order.setCreateDate(DateUtil.t2d(resultSet.getTimestamp("createDate")));
                order.setDeliveryDate(DateUtil.t2d(resultSet.getTimestamp("deliveryDate")));
                order.setPayDate(DateUtil.t2d(resultSet.getTimestamp("payDate")));

                order.setOrderCode(resultSet.getString("orderCode"));
                order.setPost(resultSet.getString("post"));
                order.setReceiver(resultSet.getString("receiver"));
                order.setStatus(resultSet.getString("status"));
                order.setUserMessage(resultSet.getString("userMessage"));
                order.setUser(new UserDAO().get(resultSet.getInt("uid")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    /**
     * 获取Order列表
     *
     * @return
     */
    public List<Order> list() {
        return list(0, Short.MAX_VALUE);
    }

    /**
     * 获取Order列表
     *
     * @param start
     * @param count
     * @return
     */
    public List<Order> list(int start, int count) {
        List<Order> beans = new ArrayList<Order>();

        String sql = "select * from Order_ order by id desc limit ?,? ";

        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {

            ps.setInt(1, start);
            ps.setInt(2, count);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order bean = new Order();
                String orderCode = rs.getString("orderCode");
                String address = rs.getString("address");
                String post = rs.getString("post");
                String receiver = rs.getString("receiver");
                String mobile = rs.getString("mobile");
                String userMessage = rs.getString("userMessage");
                String status = rs.getString("status");
                Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
                Date payDate = DateUtil.t2d(rs.getTimestamp("payDate"));
                Date deliveryDate = DateUtil.t2d(rs.getTimestamp("deliveryDate"));
                Date confirmDate = DateUtil.t2d(rs.getTimestamp("confirmDate"));
                int uid = rs.getInt("uid");

                int id = rs.getInt("id");
                bean.setId(id);
                bean.setOrderCode(orderCode);
                bean.setAddress(address);
                bean.setPost(post);
                bean.setReceiver(receiver);
                bean.setMobile(mobile);
                bean.setUserMessage(userMessage);
                bean.setCreateDate(createDate);
                bean.setPayDate(payDate);
                bean.setDeliveryDate(deliveryDate);
                bean.setConfirmDate(confirmDate);
                User user = new UserDAO().get(uid);
                bean.setUser(user);
                bean.setStatus(status);
                beans.add(bean);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return beans;
    }

    /**
     * 获取Order列表
     *
     * @param uid
     * @param excludedStatus
     * @return
     */
    public List<Order> list(int uid, String excludedStatus) {
        return list(uid, excludedStatus, 0, Short.MAX_VALUE);
    }

    /**
     * 获取指定user非状态Order列表
     *
     * @param uid
     * @param excludedStatus
     * @param start
     * @param count
     * @return
     */
    public List<Order> list(int uid, String excludedStatus, int start, int count) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM order_ WHERE uid = ? AND status != ? ORDER BY id DESC limit ?, ?";
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, uid);
            preparedStatement.setString(2, excludedStatus);
            preparedStatement.setInt(3, start);
            preparedStatement.setInt(4, count);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                int id = resultSet.getInt("id");
                String orderCode = resultSet.getString("orderCode");
                String address = resultSet.getString("address");
                String post = resultSet.getString("post");
                String receiver = resultSet.getString("receiver");
                String mobile = resultSet.getString("mobile");
                String userMessage = resultSet.getString("userMessage");

                Date createDate = DateUtil.t2d(resultSet.getTimestamp("createDate"));
                Date payDate = DateUtil.t2d(resultSet.getTimestamp("payDate"));
                Date deliveryDate = DateUtil.t2d(resultSet.getTimestamp("deliveryDate"));
                Date confirmDate = DateUtil.t2d(resultSet.getTimestamp("confirmDate"));

                String status = resultSet.getString("status");

                order.setOrderCode(orderCode);
                order.setAddress(address);
                order.setPost(post);
                order.setReceiver(receiver);
                order.setMobile(mobile);
                order.setUserMessage(userMessage);
                order.setCreateDate(createDate);
                order.setPayDate(payDate);
                order.setDeliveryDate(deliveryDate);
                order.setConfirmDate(confirmDate);
                order.setUser(new UserDAO().get(uid));
                order.setStatus(status);

                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
}
