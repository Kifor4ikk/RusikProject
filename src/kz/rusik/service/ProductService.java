package kz.rusik.service;

import kz.rusik.entity.Product;
import kz.rusik.repository.ProductRepositoryImpl;

import java.math.BigDecimal;
import java.sql.SQLException;

//Сервис - более удобное представление для репозитория.
//Вообще в хороших проектах(написанных с спрингом), а забей хуй, короче так надо.
public class ProductService {

    private final ProductRepositoryImpl productRepository;

    public ProductService(ProductRepositoryImpl productRepository) {
        this.productRepository = productRepository;
    }

    public void createNewProduct(String name, String dcs, BigDecimal cost,int mounth) throws SQLException {
        productRepository.addNewProduct(new Product(
                name,
                dcs,
                cost,
                mounth
        ));
    }

    public void deleteProduct(long id) throws SQLException {
        productRepository.removeProductById(id);
    }

    public Product getProductById(long id) throws SQLException {
        return productRepository.getProductById(id);
    }
}
