package kz.rusik.repository;

import kz.rusik.entity.Product;
import kz.rusik.entity.Role;
import kz.rusik.entity.Subscription;
import kz.rusik.entity.User;
import kz.rusik.exception.UserNotFoundException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// Было уже
public class UserRepositoryImpl implements UserRepository {

    private final Connection connection;
    private final ProductRepositoryImpl productRepository;

    public UserRepositoryImpl(Connection connection, ProductRepositoryImpl productRepository) {
        this.connection = connection;
        this.productRepository = productRepository;
    }

    private Statement state() throws SQLException {
        return connection.createStatement();
    }


    @Override
    public void createNewUser(User user) throws SQLException {
        state().execute("INSERT INTO user_entity (name,fullname,address,password,role) VALUES ('" +
                user.getName() + "', '" +
                user.getFullName() + "', '" +
                user.getAddress() + "', '" +
                user.getPassword() + "', '" +
                user.getRole().toString() + "');"
        );
        User temp = getUserByNameAndPassword(user.getName(), user.getPassword());
        state().execute("INSERT INTO purse_entity (userId, balance) VALUES ('" + temp.getId() + "','0')");
    }

    @Override
    public User getUserByNameAndPassword(String name, String password) throws SQLException {
        User user = null;
        try (ResultSet rs = state().executeQuery("SELECT * FROM user_entity WHERE name = '" + name + "' AND password = '" + password + "'")) {
            while (rs.next()) {
                user = new User(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("fullname"),
                        rs.getString("address"),
                        Role.valueOf(rs.getString("role"))
                );
            }
            assert user != null;
            user.setActiveSubs(getActiveSubs(user.getId()));
            user.setCart(getCart(user.getId()));
        }
        if (user.getId() == 0)
            throw new UserNotFoundException("User with current log and pass was not found");
        return user;
    }

    @Override
    public User getUserById(long id) throws SQLException {
        User user = null;
        try (ResultSet rs = state().executeQuery("SELECT * FROM user_entity WHERE id = '" + id + "'")) {
            while (rs.next()) {
                user = new User(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("fullname"),
                        rs.getString("address"),
                        Role.valueOf(rs.getString("role"))
                );
            }
            assert user != null;
            user.setActiveSubs(getActiveSubs(user.getId()));
            user.setCart(getCart(user.getId()));
        }
        if (user.getId() == 0)
            throw new UserNotFoundException("User with current ID was not found");
        return user;
    }

    @Override
    public List<Product> getCart(long userId) throws SQLException {
        List<Product> list = new ArrayList<>();
        try (ResultSet rs = state().executeQuery("SELECT productId FROM user_cart WHERE userId = '" + userId + "'")) {
            while (rs.next())
                list.add(productRepository.getProductById(rs.getLong("productId")));
        }
        return list;
    }

    @Override
    public List<Subscription> getActiveSubs(long userId) throws SQLException {
        List<Subscription> list = new ArrayList<>();
        try (ResultSet rs = state().executeQuery("SELECT * FROM user_subs WHERE userid = '" + userId + "'")) {
            while (rs.next())
                list.add(
                        new Subscription(
                                productRepository.getProductById(rs.getLong("productid")),
                                rs.getDate("expired")
                        )
                );
        }
        return list;
    }

    @Override
    public BigDecimal getBalance(long userId) throws SQLException {
        BigDecimal balance = BigDecimal.ZERO;
        try (ResultSet rs = state().executeQuery("SELECT balance from purse_entity WHERE userId = '" + userId + "'")){
            while (rs.next())
                balance = rs.getBigDecimal("balance");
        }
        return balance;
    }

    @Override
    public void changeBalance(long userId, BigDecimal sum) throws SQLException {
        state().execute("UPDATE purse_entity SET balance = '" + sum + "' WHERE userId = '" + userId + "'");
    }

    @Override
    public void addActiveSub(long userId, Subscription subscription) throws SQLException {
        state().execute("INSERT INTO user_subs (userId,productId,expired) VALUES ('" +
                userId + "', '" +
                subscription.getProduct().getId() + "', '" +
                subscription.getExpiredDate() + "')"
        );
    }

    @Override
    public void removeActiveSub(long userId, Subscription subscription) throws SQLException {
        state().execute("DELETE FROM user_cart WHERE userId = '" + userId + "' AND productId = '" + subscription.getProduct().getId() + "'");
    }

    @Override
    public void addToCart(long userId, long productId) throws SQLException {
        state().execute("INSERT INTO user_cart (userId,productId) VALUES ('" +
                userId + "', '" +
                productId + "')"
        );
    }

    @Override
    public void removeFromCart(long userId, Product product) throws SQLException {
        state().execute("DELETE FROM user_cart WHERE userId = '" + userId + "' AND productId = '" + product.getId() + "'");
    }

    @Override
    public void buyAllFromCart() throws SQLException {

    }
}
