package kz.rusik.entity;

import java.math.BigDecimal;
import java.sql.Date;

//ПАДПИСАЧКИИИИИИИ МММММ
//Класс энтитя который подписочки юзера на доставку X показывают
//Тут типо сам продукт + дата истекания подписки.
public class Subscription {

    private Product product;
    private Date expiredDate;

    public Subscription(Product product, Date date) {
        this.product = product;
        this.expiredDate = date;
    }

    public Product getProduct() {
        return product;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public String toString(){
        return "\n|SUBSCRIPTION|\n|PRODUCT|: " + product.getName() + "\n|EXPIRED|:" + expiredDate + "\n";
    }
}

