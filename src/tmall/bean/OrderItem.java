package tmall.bean;

/**
 * @Package: tmall.bean
 * @Author: WangXu
 * @CreateDate: 2018/3/8 11:15
 * @Version: 1.0
 */

public class OrderItem {
    private int number;
    private Product product;
    private User user;
    private int id;
    private Order order;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
