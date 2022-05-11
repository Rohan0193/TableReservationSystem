package main.model;

import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {

    Connection connection;

    public LoginModel(){

        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);

    }

    public Boolean isDbConnected(){
        try {
            return !connection.isClosed();
        }
        catch(Exception e){
            return false;
        }
    }

    // checks if the users username and password matches and if so extract user info from DB and send to Datastore
    public Boolean isLogin(String user, String pass) throws SQLException {
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        String query = "select * from employee where username = ? and password= ?";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, pass);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                // assign and store values of current logged in user into the Datastore for use in other classes
                Datastore.getInstance().setRole(resultSet.getString("Role"));
                Datastore.getInstance().setEmployeeID(resultSet.getInt("employeeID"));
                Datastore.getInstance().setFirstName(resultSet.getString("firstName"));
                Datastore.getInstance().setLastName(resultSet.getString("lastName"));
                Datastore.getInstance().setUsername(resultSet.getString("username"));
                Datastore.getInstance().setPassword(resultSet.getString("password"));
                Datastore.getInstance().setAccountStatus(resultSet.getString("accountStatus"));
                return true;
            }
            else{
                return false;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }finally {
            if (connection != null){
                connection.close();
            }
            if (preparedStatement != null){
                preparedStatement.close();
            }
           if (resultSet != null){
               resultSet.close();
           }
        }
    }

    // check if the account user type is admin
    public boolean isAdmin(){
        boolean result = false;
        if (Datastore.getInstance().getRole().equals("Admin")){
            result = true;
        }
        return result;
    }

}
