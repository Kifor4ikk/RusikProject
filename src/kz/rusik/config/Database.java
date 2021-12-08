package kz.rusik.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


//тут чисто конфиг для соединения с бд
//и для юзания бд в проге
public class Database {

    //Put you DATABASE HERE
    private static final String jdbcUrl = "jdbc:postgresql://localhost:1489/RusikProject";
    private static final String username = "postgres";
    private static final String password = "pass";

    public static Connection connectWithDataBase() throws SQLException, ClassNotFoundException {
        Connection connection = DriverManager.getConnection(jdbcUrl,username,password);
        System.out.println("connected!");
        return connection;
    }
}

