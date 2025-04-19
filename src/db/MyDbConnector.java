package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class MyDbConnector {

    private static String user;
    private static String pw;
    private static final String url = "jdbc:mysql://localhost:3306/sample";

    public static void setCredentials(String username, String password) {
        user = username;
        pw = password;
    }

    public Connection getMyConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, pw);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
