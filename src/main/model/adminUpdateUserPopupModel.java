package main.model;

import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class adminUpdateUserPopupModel {
    Connection connection;

    public adminUpdateUserPopupModel(){

        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);

    }

    // retrieves a particular users data when an admin wants to change a Users details
    public employeeObj getUserData(int employeeID) throws SQLException {
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        String query = "select * from employee where employeeID = ?";
        employeeObj employee = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, employeeID);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                // assign and store values of current logged in user into the Datastore for use in other classes
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String role = resultSet.getString("Role");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String secretQuestion = resultSet.getString("secretQuestion");
                String ansSecretQuestion = resultSet.getString("ansSecretQuestion");
                String accountStatus = resultSet.getString("accountStatus");
                String lastTableBooked = resultSet.getString("lastTableBooked");
                employee = new employeeObj(employeeID, firstName, lastName, role, username, password, secretQuestion,
                        ansSecretQuestion, accountStatus, lastTableBooked);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
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
        return employee;
    }

    // updates user data when an admin alters it
    public void updateUserData(int employeeID, String firstname, String lastname, String role, String username,
                               String password, String secretQuestion, String ansSecretQuestion, String accountStatus) throws SQLException {
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;
        String query = "UPDATE Employee SET firstName = replace(firstName, firstName, ?), lastName = replace(lastName, lastName, ?)," +
                "Role = replace(Role, Role, ?), username = replace(username, username, ?), password = replace(password, password, ?)," +
                "secretQuestion = replace(secretQuestion, secretQuestion, ?), ansSecretQuestion = replace(ansSecretQuestion, ansSecretQuestion, ?)," +
                "accountStatus = replace(accountStatus, accountStatus, ?) WHERE employeeID = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, firstname);
            preparedStatement.setString(2, lastname);
            preparedStatement.setString(3, role);
            preparedStatement.setString(4, username);
            preparedStatement.setString(5, password);
            preparedStatement.setString(6, secretQuestion);
            preparedStatement.setString(7, ansSecretQuestion);
            preparedStatement.setString(8, accountStatus);
            preparedStatement.setInt(9, employeeID);
            preparedStatement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (connection != null){
                connection.close();
            }
            if (preparedStatement!=null){
                preparedStatement.close();
            }
        }
    }

    // checks if a particular username when registering or altering details is unique
    public boolean isUsernameUnique(String newUsername, String currentUsername){
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from Employee where username = ? and username <> ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newUsername);
            preparedStatement.setString(2, currentUsername);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return false;
            }
            else{
                return true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            try {
                if (connection != null){
                    connection.close();
                }
                if(preparedStatement != null){
                    preparedStatement.close();
                }
                if (resultSet != null){
                    resultSet.close();
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

}
