package main;
import java.sql.*;


public class SQLConnection {
    private static Connection connection = null;
/* changed database connect method to avoid database locked/busy errors, if there is
   no connection then it starts a new connection otherwise if there is a connection
   it closes the current one and starts a new one */
    public static Connection connect(){
        try{
            if (connection == null){
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:assignment.db");
                return connection;
            } else {
                connection.close();
                connection = DriverManager.getConnection("jdbc:sqlite:assignment.db");
                return connection;
            }
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
}
