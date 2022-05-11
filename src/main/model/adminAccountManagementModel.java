package main.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class adminAccountManagementModel {
    Connection connection;

    public adminAccountManagementModel(){

        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);

    }

    // gets all employees in employee table and returns observablelist to display in tableview
    public ObservableList<employeeObj> getEmployees() {
        connection = SQLConnection.connect();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        ObservableList<employeeObj> result = FXCollections.observableArrayList();
        try {
            preparedStatement = connection.prepareStatement("SELECT * from Employee");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int employeeID = resultSet.getInt("employeeID");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String Role = resultSet.getString("Role");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String secretQuestion = resultSet.getString("secretQuestion");
                String ansSecretQuestion = resultSet.getString("ansSecretQuestion");
                String accountStatus = resultSet.getString("accountStatus");
                String lastTableBooked = resultSet.getString("lastTableBooked");

                employeeObj employee = new employeeObj(employeeID, firstName, lastName, Role, username, password,
                        secretQuestion, ansSecretQuestion, accountStatus, lastTableBooked);
                result.add(employee);
            }

        }catch(SQLException e){
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null){
                    resultSet.close();
                }
                if (preparedStatement != null){
                    preparedStatement.close();
                }
                if (connection != null){
                    connection.close();
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        return result;
    }

    // deletes a user from the database and any of their pending/accepted bookings
    public void deleteUser(int employeeID) throws SQLException {
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM Bookings WHERE employeeID = ?");
            preparedStatement.setInt(1, employeeID);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM Employee WHERE employeeID = ?");
            preparedStatement.setInt(1, employeeID);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (preparedStatement!= null){
                preparedStatement.close();
            }
            if (connection != null){
                connection.close();
            }
        }
    }

}
