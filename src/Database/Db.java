package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {
    private final static String url="jdbc:mysql://localhost:3306/store";
    private final static String user ="your name";
    private final static String password ="your password";
    private Connection con;
    public Connection getConnection() throws  SQLException {
            return DriverManager.getConnection(url, user, password);

    }


    }
