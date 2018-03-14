package tmall.dao;

import tmall.bean.Product;
import tmall.bean.Property;
import tmall.bean.PropertyValue;
import tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Package: tmall.dao
 * @Author: WangXu
 * @CreateDate: 2018/3/12 11:13
 * @Version: 1.0
 */

public class PropertyValueDAO {

    public int getTotal() {
        int total = 0;
        try (
                Connection connection = DBUtil.getConnection();
                Statement statement = connection.createStatement();
        ) {
            String sql = "select count(*) from propertyvalue";
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                total = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public void add(PropertyValue propertyValue) {
        String sql = "insert into properyvalue VALUES (NULL, ?, ?, ?)";
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, propertyValue.getProduct().getId());
            preparedStatement.setInt(2, propertyValue.getProperty().getId());
            preparedStatement.setString(3, propertyValue.getValue());
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
            String sql = "DELETE FROM propertyalue WHERE id =" + id;
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(PropertyValue propertyValue) {
        String sql = "UPDATE propertyvalue SET pid = ?, ptid = ?, value = ? WHERE id = ?";
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, propertyValue.getProduct().getId());
            preparedStatement.setInt(2, propertyValue.getProperty().getId());
            preparedStatement.setString(3, propertyValue.getValue());
            preparedStatement.setInt(4, propertyValue.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PropertyValue get(int id) {
        PropertyValue propertyValue = null;
        try (
                Connection connection = DBUtil.getConnection();
                Statement statement = connection.createStatement();
        ) {
            String sql = "SELECT * FROM propertyvalue WHERE id = " + id;
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                propertyValue.setValue(resultSet.getString("value"));
                propertyValue.setProduct(new ProductDAO().get(resultSet.getInt("pid")));
                propertyValue.setProperty(new PropertyDAO().get(resultSet.getInt("ptid")));
                propertyValue.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return propertyValue;
    }

    public PropertyValue get(int pid, int ptid) {
        PropertyValue propertyValue = null;
        String sql = "SELECT * FROM propertyvalue WHERE pid = ? AND ptid = ?";
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, pid);
            preparedStatement.setInt(2, ptid);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                propertyValue.setValue(resultSet.getString("value"));
                propertyValue.setProduct(new ProductDAO().get(pid));
                propertyValue.setProperty(new PropertyDAO().get(ptid));
                propertyValue.setId(resultSet.getInt("id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return propertyValue;
    }

    public List<PropertyValue> list() {
        return list(0, Short.MAX_VALUE);
    }

    public List<PropertyValue> list(int start, int count) {
        List<PropertyValue> propertyValues = new ArrayList<>();
        String sql = "SELECT * FROM propertyvalue ORDER BY id DESC limit ?, ?";
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, start);
            preparedStatement.setInt(2, count);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PropertyValue propertyValue = new PropertyValue();
                propertyValue.setId(resultSet.getInt("id"));
                propertyValue.setProduct(new ProductDAO().get(resultSet.getInt("pid")));
                propertyValue.setProperty(new PropertyDAO().get(resultSet.getInt("ptid")));
                propertyValue.setValue(resultSet.getString("value"));
                propertyValues.add(propertyValue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return propertyValues;
    }

    public List<PropertyValue> list(int pid) {
        List<PropertyValue> beans = new ArrayList<PropertyValue>();
        String sql = "select * from PropertyValue where pid = ? order by ptid desc";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
            ps.setInt(1, pid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PropertyValue bean = new PropertyValue();
                int id = rs.getInt(1);
                int ptid = rs.getInt("ptid");
                String value = rs.getString("value");
                Product product = new ProductDAO().get(pid);
                Property property = new PropertyDAO().get(ptid);
                bean.setProduct(product);
                bean.setProperty(property);
                bean.setValue(value);
                bean.setId(id);
                beans.add(bean);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return beans;
    }

    public void init(Product product) {
        List<Property> properties = new PropertyDAO().list(product.getCategory().getId());
        for (Property property : properties) {
            PropertyValue propertyValue = this.get(property.getId(), product.getId());
            if (null == propertyValue) {
                propertyValue = new PropertyValue();
                propertyValue.setProduct(product);
                propertyValue.setProperty(property);
                this.add(propertyValue);
            }
        }
    }
}
