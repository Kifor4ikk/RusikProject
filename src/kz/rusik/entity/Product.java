package kz.rusik.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;


//Ентити моделкьа продукта для продажи
//вроде все поля ясны для чего
public class Product {

    private long id;
    private String name;
    private String description;
    private BigDecimal cost;
    private int month;

    public Product(String name, String description, BigDecimal cost, int month) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.month = month;
    }

    public Product(long id, String name, String description, BigDecimal cost, int mounth) {
        this(name,description,cost, mounth);
        this.id = id;
    }

    public int getMonth() {
        return month;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public String getDescription() {
        return description;
    }

    public BigDecimal getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "\n|PRODUCT|\n|ID|:" + id + "\n|NAME|:" + name + "\n|DESCRIPTION|:" + description + "\n|COST|:" + cost + "\n|SUBTIME|: " + month + "\n";
    }
}
