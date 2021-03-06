package tmall.bean;

/**
 * @Package: tmall.bean
 * @Author: WangXu
 * @CreateDate: 2018/3/8 10:40
 * @Version: 1.0
 */

public class ProductImage {
    private int id;
    private Product product;
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = this.id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
