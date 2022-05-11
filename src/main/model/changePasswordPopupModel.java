package main.model;

import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class changePasswordPopupModel {

    Connection connection;

    public changePasswordPopupModel(){

        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);

    }

    // checks if the current password is correct
    public boolean checkCurrentPassword(String currentPassword){
        if (currentPassword.equals(Datastore.getInstance().getPassword())){
            return true;
        }
        else{
            return false;
        }
    }

    // check if the confirm password matches the new password the user wishes to change to
    public boolean checkNewPasswordMatches(String newPassword, String confirmPassword){
        if (newPassword.equals(confirmPassword)){
            return true;
        }
        else{
            return false;
        }
    }

    // updates a user password in the database
    public void updatePassword(String newPassword, int employeeID) throws SQLException {
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "update Employee set password = replace(password, password, ?) where employeeID = ?");
            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, employeeID);
            preparedStatement.executeUpdate();

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
