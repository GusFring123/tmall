package tmall.dao;

import tmall.bean.Category;
import tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Package: tmall.dao
 * @Author: WangXu
 * @CreateDate: 2018/3/8 13:56
 * @Version: 1.0
 */

public class CategoryDAO {
    /**
     * 获取分类总数
     *
     * @return int
     */
    public int getTotal() {
        int total = 0;
        try (Connection connection = DBUtil.getConnection(); Statement statement = connection.createStatement();) {
            String sql = "select count(*) from category";
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
     * 增加Category
     *
     * @param category category
     */
    public void add(Category category) {
        String sql = "insert into category values(null,?)";
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setString(1, category.getName());
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                category.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新Category
     *
     * @param category category
     */
    public void update(Category category) {
        String sql = "update category set name = ? where id = ?";
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setString(1, category.getName());
            preparedStatement.setInt(2, category.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除Category
     *
     * @param id id
     */
    public void delete(int id) {
        try (
                Connection connection = DBUtil.getConnection();
                Statement statement = connection.createStatement();
        ) {
            String sql = "delete from category where id = " + id;
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据id查询Category
     *
     * @param id id
     * @return Category
     */
    public Category get(int id) {
        Category category = null;
        try (
                Connection connection = DBUtil.getConnection();
                Statement statement = connection.createStatement();
        ) {
            String sql = "select * from category where id = " + id;
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                category = new Category();
                String name = resultSet.getString(2);
                category.setId(id);
                category.setName(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }

    /**
     * 分页查询
     *
     * @param start 开始页数
     * @param count 数据条数
     * @return List<Category>
     */
    public List<Category> list(int start, int count) {
        List<Category> categories = new ArrayList<>();
        String sql = "select * from category order by id desc limit ?, ?";
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, start);
            preparedStatement.setInt(2, count);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getInt(1));
                category.setName(resultSet.getString(2));
                categories.add(category);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    /**
     * 查询所有
     *
     * @return List<Category>
     */
    public List<Category> list() {
        return list(0, Short.MAX_VALUE);
    }
}
