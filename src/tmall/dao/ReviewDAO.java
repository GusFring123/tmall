package tmall.dao;

import tmall.bean.Product;
import tmall.bean.Review;
import tmall.util.DBUtil;
import tmall.util.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Package: tmall.dao
 * @Author: WangXu
 * @CreateDate: 2018/3/14 11:01
 * @Version: 1.0
 */

public class ReviewDAO {

    public int getTotal() {
        int total = 0;
        try (
                Connection connection = DBUtil.getConnection();
                Statement statement = connection.createStatement();
        ) {
            String sql = "SELECT COUNT(*) FROM  review";
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                total = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public int getTotal(int pid) {
        int total = 0;
        try (
                Connection connection = DBUtil.getConnection();
                Statement statement = connection.createStatement();
        ) {
            String sql = "SELECT COUNT(*) FROM review  WHERE pid = " + pid;
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
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
                ){

        } catch (SQLException e) {
            e.printStackTrace();
        }

try (
                Connection connection = DBUtil.getConnection();
                Statement statement = connection.createStatement();
        ) {
            String sql = "";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    */

    public void add(Review review) {
        String sql = "INSERT INTO  review VALUES (NULL, ?, ?, ?, ?)";
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setString(1, review.getContent());
            preparedStatement.setInt(2, review.getUser().getId());
            preparedStatement.setInt(3, review.getProduct().getId());
            preparedStatement.setTimestamp(4, DateUtil.d2t(review.getCreateDate()));
            preparedStatement.execute();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                review.setId(generatedKeys.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Review review) {
        String sql = "UPDATE review SET content = ?,  uid = ?, pid = ? ,createdate = ? WHERE id = ?";
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setString(1, review.getContent());
            preparedStatement.setInt(2, review.getUser().getId());
            preparedStatement.setInt(3, review.getProduct().getId());
            preparedStatement.setTimestamp(4, DateUtil.d2t(review.getCreateDate()));
            preparedStatement.setInt(5, review.getId());

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try (
                Connection connection = DBUtil.getConnection();
                Statement statement = connection.createStatement();
        ) {
            String sql = "DELETE FROM review WHERE id = " + id;
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Review get(int id) {
        Review review = null;
        try (
                Connection connection = DBUtil.getConnection();
                Statement statement = connection.createStatement();
        ) {
            String sql = "SELECT * FROM review WHERE id=" + id;
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                review = new Review();
                review.setId(resultSet.getInt("id"));
                review.setContent(resultSet.getString("content"));
                review.setUser(new UserDAO().get(resultSet.getInt("uid")));
                review.setProduct(new ProductDAO().get(resultSet.getInt("pid")));
                review.setCreateDate(DateUtil.t2d(resultSet.getTimestamp("createDate")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return review;
    }

    public List<Review> list(int pid, int start, int count) {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM review WHERE pid = ? ORDER BY id DESC limit ?, ?";
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, pid);
            preparedStatement.setInt(2, start);
            preparedStatement.setInt(3, count);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Review review = new Review();
                review.setId(resultSet.getInt("id"));
                review.setUser(new UserDAO().get(resultSet.getInt("uid")));
                review.setProduct(new ProductDAO().get(resultSet.getInt("pid")));
                review.setContent(resultSet.getString("content"));
                review.setCreateDate(DateUtil.t2d(resultSet.getTimestamp("createDate")));
                reviews.add(review);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public List<Review> list(int pid) {
        return list(pid, 0, Short.MAX_VALUE);
    }

}
