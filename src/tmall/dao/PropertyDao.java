package tmall.dao;

import tmall.bean.Category;
import tmall.bean.Property;
import tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Package: tmall.dao
 * @Author: WangXu
 * @CreateDate: 2018/3/9 13:58
 * @Version: 1.0
 */

public class PropertyDao {

    /**
     * 根据cid获取总数
     *
     * @param cid int
     * @return int
     */
    public int getTotal(int cid) {
        int total = 0;
        try (
                Connection connection = DBUtil.getConnection();
                Statement statement = connection.createStatement();
        ) {
            String sql = "select count(*) from property where cid = " + cid;
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
     * 增加 Property
     *
     * @param property Property
     */
    public void add(Property property) {
        String sql = "insert into property values(null, ?, ?)";
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, property.getCategory().getId());
            preparedStatement.setString(2, property.getName());
            preparedStatement.execute();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                property.setId(id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除 Property
     *
     * @param id int
     */
    public void delete(int id) {
        try (
                Connection connection = DBUtil.getConnection();
                Statement statement = connection.createStatement();
        ) {
            String sql = "delete from property where id = " + id;
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新 Property
     *
     * @param property
     */
    public void update(Property property) {
        String sql = "update property set cid = ?, name = ? where id = ?";
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, property.getCategory().getId());
            preparedStatement.setString(2, property.getName());
            preparedStatement.setInt(3, property.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    public Property get(int id) {
        Property property = null;
        try (
                Connection connection = DBUtil.getConnection();
                Statement statement = connection.createStatement();
        ) {
            String sql = "select * from property where id = " + id;
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                property = new Property();
                property.setId(id);
                property.setName(resultSet.getString("name"));
                Category category = new CategoryDao().get(resultSet.getInt("cid"));
                property.setCategory(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return property;
    }

    /**
     * 分页查询
     *
     * @param cid
     * @param start
     * @param count
     * @return
     */
    public List<Property> list(int cid, int start, int count) {
        List<Property> properties = new ArrayList<>();
        String sql = "select * from property where cid = ? order by id desc limit ?, ?";
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, cid);
            preparedStatement.setInt(2, start);
            preparedStatement.setInt(3, count);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Property property = new Property();
                property.setId(resultSet.getInt("id"));
                property.setName(resultSet.getString("name"));
                property.setCategory(new CategoryDao().get(cid));
                properties.add(property);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * 查询所有
     *
     * @param cid
     * @return
     */
    public List<Property> list(int cid) {
        return list(cid, 0, Short.MAX_VALUE);
    }

}
