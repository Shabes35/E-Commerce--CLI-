package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {
    private final static String url="jdbc:mysql://localhost:3306/store";
    private final static String user ="root";
    private final static String password ="pass123";
    private Connection con;
    public Connection getConnection() throws  SQLException {
            return DriverManager.getConnection(url, user, password);

    }
        public void closeCon() {
            try {
                if (con != null ) {
                    con.close();
                    System.out.println("Connection Closed bruh... ");

                }
            }catch (SQLException e){
                System.out.println("Error in closing connection bruh ...." + e);
            }
        }
    }
