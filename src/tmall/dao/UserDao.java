package tmall.dao;

import tmall.bean.User;
import tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Package: tmall.dao
 * @Author: WangXu
 * @CreateDate: 2018/3/9 9:54
 * @Version: 1.0
 */

public class UserDao {
    /**
     * 获取总数
     *
     * @return int
     */
    public int getTotal() {
        int total = 0;
        try (
                Connection connection = DBUtil.getConnection();
                Statement statement = connection.createStatement();

        ) {
            String sql = "select count(*) from user";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                total = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    /**
     * 增加User 添加后可获得主键id
     *
     * @param user User
     */
    public void add(User user) {
        String sql = "insert into user values(null, ?, ?)";
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.execute();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                //插入后获得id;
                int id = generatedKeys.getInt(1);
                user.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新User
     *
     * @param user user
     */
    public void update(User user) {
        String sql = "update user set name = ?, password = ? where id = ?";
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(3, user.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除User
     *
     * @param id int
     */
    public void delete(int id) {
        try (
                Connection connection = DBUtil.getConnection();
                Statement statement = connection.createStatement();
        ) {
            String sql = "delete from user where id = " + id;
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询User
     *
     * @param id int
     * @return User
     */
    public User get(int id) {
        User user = null;
        try (
                Connection connection = DBUtil.getConnection();
                Statement statement = connection.createStatement();
        ) {
            String sql = "select * from user where id = " + id;
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                user = new User();
                user.setId(id);
                user.setName(resultSet.getString(1));
                user.setPassword(resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 分页查询 User
     *
     * @param start int
     * @param count int
     * @return List<User>
     */
    public List<User> list(int start, int count) {
        List<User> users = new ArrayList<>();
        String sql = "select * from user order by id desc limit ?, ?";
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, start);
            preparedStatement.setInt(2, count);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * 查询所有  但有最大值32767
     *
     * @return List<User>
     */
    public List<User> list() {
        return list(0, Short.MAX_VALUE);
    }

    /**
     * 根据名称和密码查询
     *
     * @param name     String
     * @param password String
     * @return User
     */

    public User get(String name, String password) {
        User user = null;
        String sql = "select * from user where name = ? and password = ?";
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
                user.setId(resultSet.getInt("id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 根据名称查询
     *
     * @param name String
     * @return User
     */
    public User get(String name) {
        User user = null;
        try (
                Connection connection = DBUtil.getConnection();
                Statement statement = connection.createStatement();
        ) {
            String sql = "select * from user where name = " + name;
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 判断是否存在
     *
     * @param name String
     * @return boolean
     */
    public boolean isExist(String name) {
        return get(name) != null;
    }

}
