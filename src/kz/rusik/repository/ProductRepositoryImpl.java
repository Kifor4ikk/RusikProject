package kz.rusik.repository;

import kz.rusik.entity.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



//Реализация репозитория
//Тут непосредственные запросы в базу данных и обмен информацией с ней
public class ProductRepositoryImpl implements ProductRepository {
    private final Connection connection;

    public ProductRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    private Statement state() throws SQLException {
        return connection.createStatement();
    }

    @Override
    public void addNewProduct(Product product) throws SQLException {
        state().execute("INSERT INTO product_entity (name,description,cost,month) VALUES ('" +
                product.getName() + "', '" +
                product.getDescription() + "', '" +
                product.getCost() + "', '" +
                product.getMonth() + "')"
        );
    }

    @Override
    public void removeProductById(long id) throws SQLException {
        state().execute("DELETE from product_entity WHERE ID = '" + id + "'");
    }

    @Override
    public Product getProductById(long id) throws SQLException {
        Product product = null;
        try (ResultSet rs = state().executeQuery("SELECT * FROM product_entity WHERE id = '" + id + "'")){
            while (rs.next()){
                product = new Product(
                        id,
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getBigDecimal("cost"),
                        rs.getInt("month")
                );
            }
        }
        return product;
    }
}
