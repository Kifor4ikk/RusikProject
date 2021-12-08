package kz.rusik;

import kz.rusik.config.Database;
import kz.rusik.exception.NotEnoughtMoney;
import kz.rusik.repository.ProductRepositoryImpl;
import kz.rusik.repository.UserRepositoryImpl;
import kz.rusik.service.ProductService;
import kz.rusik.service.UserService;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //Тут коннект к базе данных и поднятие наших сервисов, чтобы потом к ним обращаться.
        //Должно быть реализованно 1 раз на стороне сервера.
        Connection connection = Database.connectWithDataBase();
        ProductRepositoryImpl productRepository = new ProductRepositoryImpl(connection);
        ProductService productService = new ProductService(productRepository);

        UserRepositoryImpl userRepository = new UserRepositoryImpl(connection, productRepository);
        UserService userService = new UserService(userRepository);

        //ТЕСТЫ СОЗДАНИЯ И УДАЛЕНИЯ ПРОДУКТОВОГО ЛИСТА
        productService.createNewProduct("ШАУРМА ОСТРАЯ", "ПИЗДЕЦ КАКАЯ ОСТРАЯ НАХУЙ БЛЯТЬ", new BigDecimal(149),2);
        System.out.println(productService.getProductById(1));
        //ТУТ ДОЛЖНО БЫТЬ УДАЛЕНИЕ, НО ОНО КИДАЕТ ЭКСПЕШН, ПОТОМУ ЧТО НЕ СУЩЕСТВУЕТ ШАУРМЫ, КОроче да

        //ТЕСТЫ СОЗДАНИЯ ЮЗЕРА
        //Создаем юзера и логинимся.
        //Со стороны клиента нужно будет слать серверу Login(name) Pass(Password), а тот будет чекать наличие такого говна
        //в базе данных, если есть, то он вернет клиенту объект USER и USER примениться к себе же)
        userService.createNewUser("Rusik1488","Rusik Rusik Rusik", "Kazahstan Astana 14/88", "123456konb");
        System.out.println(userService.getUserByNamePass("Rusik1488","123456konb"));
        System.out.println("\n-----------");

        //ДОБАВЛЯЕМ ШАУРМУ В КОРЗИНУ
        userService.addToCart(1,1);
        System.out.println(userService.getUserByNamePass("Rusik1488","123456konb"));
        System.out.println("\n-----------");

        //Чекаем баланс русика
        System.out.println("Rusik balance -> " + userService.getBalance(1));
        //Такие вот штуки нужны будут на стороне сервера, чтобы отлавливать потенциальные ошибки и при этом не крашить продакшн
        //ПЫТАЕМСЯ ОПЛАТИТЬ ШАУРМУ, но т.к. у РУСИКА НЕТ ДЕНЯХ, НАМ скажет, что НЕТ ДЕНЯХ.
        try {
            userService.buyAllFromCart(1);
        }catch (NotEnoughtMoney e){
            System.out.println("НЕТ ДЕНЯХ");
        }
        //Выводим инфо о русике, чтобы посмотреть, что подписка на шаурму не прошла.
        System.out.println(userService.getUserByNamePass("Rusik1488","123456konb"));
        //И деньги не сняли
        System.out.println("Rusik balance -> " + userService.getBalance(1));
        //Русик получает степуху
        //Всё держиться на ID юзверя, поэтому скорее всего, клиент должен будет хранить у себя только свой ID, но я хз, придумаем что-нибудь
        userService.credit(1,BigDecimal.valueOf(1000));
        //Чекаем баланс русика, чтоб убедится в степухе.
        System.out.println("Rusik balance -> " + userService.getBalance(1));
        //Опять пытаемся купить шавуху
        try {
            userService.buyAllFromCart(1);
        }catch (NotEnoughtMoney e){
            System.out.println("НЕТ ДЕНЯХ");
        }
        //Выводим инфо о русике, чтобы посмотреть, что подписка на шаурму прошла.
        System.out.println(userService.getUserByNamePass("Rusik1488","123456konb"));
        //И деньги сняли
        System.out.println("Rusik balance -> " + userService.getBalance(1));

        System.out.println("END OF WORK");
    }
}
