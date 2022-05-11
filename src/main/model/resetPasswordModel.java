package main.model;

import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class resetPasswordModel {

    Connection connection;

    // these are found and used in below methods
    String secretQuestionAns = "";
    String newPassword = "";
    int employeeID;

    public resetPasswordModel(){

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

    public String findSecretQuestion(String username) throws SQLException {
        connection = SQLConnection.connect();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        String result = "";

        try {
            preparedStatement = connection.prepareStatement("SELECT * from Employee where username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            try{
                if (resultSet.next()){
                   result = resultSet.getString("secretQuestion");
                   secretQuestionAns = resultSet.getString("ansSecretQuestion");
                   employeeID = resultSet.getInt("employeeID");
                }
                else{
                    result = "invalid username";
                }
            }catch (Exception e){
                e.printStackTrace();
                e.getCause();
            }

        }catch(SQLException e){
            e.printStackTrace();
        } finally {
            if (connection != null){
                connection.close();
            }
            if (resultSet != null){
                resultSet.close();
            }
            if (preparedStatement != null){
                preparedStatement.close();
            }
        }

        return result;

    }

    public boolean checkSecretQuestionAns(String answer) {
        boolean result = false;
        if (answer.equals(secretQuestionAns)){
            result = true;
        }
        else {
            result = false;
        }
        return result;
    }

    public String generateNewPassword() {
        String[] password = {"51age", "pattern64", "25cent", "lost67", "term34", "rain34", "burn82", "brother34",
                "66mean", "56bear", "28company", "thought61", "55play", "16room", "96silent", "clock36", "character33",
                "45product", "software52", "11music", "grass26", "fraction77", "53gentle", "75summer", "18bacon"};
        Random random = new Random();
        newPassword = password[random.nextInt(25)+1];

        return newPassword;
    }

    public void updatePasswordDatabase() throws SQLException {
        PreparedStatement preparedStatement = null;
        connection = SQLConnection.connect();

        try {
            preparedStatement = connection.prepareStatement(
                    "update Employee set password = replace(password, password, ?) where employeeID = ?");
            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, employeeID);
            preparedStatement.executeUpdate();
            newPassword = "";
            employeeID = 0;
        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (connection != null){
                connection.close();
            }
            if (preparedStatement != null){
                preparedStatement.close();
            }
        }
    }

}
