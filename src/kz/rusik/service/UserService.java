package kz.rusik.service;

import kz.rusik.entity.Product;
import kz.rusik.entity.Subscription;
import kz.rusik.entity.User;
import kz.rusik.exception.NotEnoughtMoney;
import kz.rusik.repository.ProductRepositoryImpl;
import kz.rusik.repository.UserRepositoryImpl;
import org.postgresql.shaded.com.ongres.scram.common.ScramAttributes;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;


//ТОж сервис только для юзеров.
public class UserService {

    private final UserRepositoryImpl userRepository;

    public UserService(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }

    public void createNewUser(String name, String fullname, String adress, String password) throws SQLException {
        userRepository.createNewUser(new User(name, fullname, adress, password));
    }

    public User getUserByNamePass(String name, String password) throws SQLException {
        return userRepository.getUserByNameAndPassword(name, password);
    }

    public void addToCart(long productId, long userId) throws SQLException {
        userRepository.addToCart(userId, productId);
    }

    public void credit(long id, BigDecimal count) throws SQLException {
        userRepository.changeBalance(id, userRepository.getBalance(id).add(count));
    }

    public void debit(long id, BigDecimal count) throws SQLException, NotEnoughtMoney {
        if (userRepository.getBalance(id).subtract(count).compareTo(BigDecimal.ZERO) < 0)
            throw new NotEnoughtMoney("Not enought money on your balance!");
        else
            userRepository.changeBalance(id, userRepository.getBalance(id).subtract(count));
    }

    public BigDecimal getBalance(long id) throws SQLException {
        return userRepository.getBalance(id);
    }

    public void buyAllFromCart(long id) throws SQLException {
        User user = userRepository.getUserById(id);
        for (Product product : user.getCart()) {
            debit(user.getId(), product.getCost());
            userRepository.addActiveSub(
                    user.getId(),
                    new Subscription(product, Date.valueOf(LocalDate.now().plusMonths(product.getMonth())))
            );
            userRepository.removeFromCart(user.getId(), product);
        }
    }
}
