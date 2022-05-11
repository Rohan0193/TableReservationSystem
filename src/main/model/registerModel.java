package main.model;

import main.SQLConnection;

import java.sql.*;

public class registerModel {

    Connection connection;

    public registerModel(){

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

    // adds a new user to the Employee database
    public void registerUser(String firstName, String lastName, String role, String username, String password, String secretQuestion,
                             String ansSecretQuestion) {
        connection = SQLConnection.connect();

        String insertFields = "INSERT INTO Employee(firstName, lastName, Role, username, password, secretQuestion, ansSecretQuestion) VALUES ('";
        String insertValues = firstName + "','" + lastName + "','" + role + "','" + username + "','" + password + "','" + secretQuestion +
                "','" + ansSecretQuestion + "')";
        String insertToRegister = insertFields + insertValues;

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(insertToRegister);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    // checks if the entered username is already present in the database
    public boolean isUsernameUnique(String username){
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from employee where username = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);

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

