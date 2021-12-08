package kz.rusik.repository;

import kz.rusik.entity.Product;

import java.sql.SQLException;

//Репа, описывает что нужно делать с продуктами
public interface ProductRepository {

    void addNewProduct(Product product) throws SQLException;
    void removeProductById(long id) throws SQLException;
    Product getProductById(long id) throws SQLException;
}
