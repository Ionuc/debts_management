package main.com.debts.management.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBaseConnection {
	private static Connection con;
    private static  String username = "root";
    private static String password = "";
    private static String url="jdbc:mysql://localhost:3306/debts-management";
    private static String driver = "com.mysql.jdbc.Driver";

    public static Connection getCon() {
        try{
        Class.forName(driver);
        con = DriverManager.getConnection(url, username, password);
        return  con;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
