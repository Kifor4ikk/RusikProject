package kz.rusik.repository;

import kz.rusik.entity.Product;
import kz.rusik.entity.Purse;
import kz.rusik.entity.Subscription;
import kz.rusik.entity.User;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

//ТОж самое только для пользователей
public interface UserRepository {

    void createNewUser(User user) throws SQLException;
    User getUserByNameAndPassword(String name,String password) throws SQLException;
    User getUserById(long id) throws SQLException;
    List<Product> getCart(long userId) throws SQLException;
    List<Subscription> getActiveSubs(long userId) throws SQLException;
    BigDecimal getBalance(long userId) throws SQLException;
    void changeBalance(long userId, BigDecimal sum) throws SQLException;
    void addActiveSub(long userId, Subscription subscription) throws SQLException;
    void removeActiveSub(long userId, Subscription subscription) throws SQLException;
    void addToCart(long userId, long productId) throws SQLException;
    void removeFromCart(long userId, Product product) throws SQLException;
    void buyAllFromCart() throws SQLException;
}
