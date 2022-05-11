package main.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.SQLConnection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.*;
import java.time.LocalDate;

public class adminMenuModel {

    Connection connection;

    public adminMenuModel(){

        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);

    }

    // adds a restriction to admin restriction database
    public void addRestriction(String restrictionType, String restrictionDate) {
        connection = SQLConnection.connect();
        String insertFields = "INSERT INTO adminRestrictions(restrictionType,restrictionDate) VALUES ('";
        String insertValues = restrictionType + "','" + restrictionDate + "')";
        String insertToRegister = insertFields + insertValues;

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(insertToRegister);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        } finally {
                try {
                    if (connection != null){
                        connection.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    }

    //removes a restriction from the admin restriction database
    public void removeRestriction(String date) throws SQLException {
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement("DELETE FROM adminRestrictions WHERE restrictionDate = ?");
            preparedStatement.setString(1, date);
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



    // gets all restrictions from database to display in table view
    public ObservableList<restrictionObj> getRestrictions() {
        connection = SQLConnection.connect();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        ObservableList<restrictionObj> result = FXCollections.observableArrayList();
        try {
            preparedStatement = connection.prepareStatement("SELECT * from adminRestrictions");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String restrictionType = resultSet.getString("restrictionType");
                String restrictionDate = resultSet.getString("restrictionDate");

                restrictionObj restrictionObj = new restrictionObj(restrictionType, restrictionDate);
                result.add(restrictionObj);
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

    // check if there is a restriction which exists at a particular date
    public Boolean checkExistingRestrictions(String date) throws SQLException {
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        String query = "select * from adminRestrictions where restrictionDate = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, date);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
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
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null){
                resultSet.close();
            }
        }
    }

    // generates a .csv file of the Bookings database
    public void generateBookingReport() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        connection = SQLConnection.connect();

        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM Bookings");
            resultSet = preparedStatement.executeQuery();

            File bookingReport = new File("bookingReport" + LocalDate.now() + ".csv");
            PrintWriter printWriter = new PrintWriter(bookingReport);

            printWriter.println("bookingID, employeeID, bookingDate, cancellationCutoff, tableBooked, bookingStatus, checkinStatus");

            while (resultSet.next()){
                int bookingID = resultSet.getInt("bookingID");
                int employeeID = resultSet.getInt("employeeID");
                String bookingDate = resultSet.getString("bookingDate");
                String cancellationCutoff = resultSet.getString("cancellationCutoff");
                String tableBooked = resultSet.getString("tableBooked");
                String bookingStatus = resultSet.getString("bookingStatus");
                String checkinStatus = resultSet.getString("checkinStatus");

                printWriter.printf("%d,%d,%s,%s,%s,%s,%s%n",bookingID, employeeID, bookingDate, cancellationCutoff,
                        tableBooked, bookingStatus, checkinStatus);
            }
            printWriter.close();
        }
        catch (SQLException | FileNotFoundException e){
            e.printStackTrace();
        }
        finally {
            if (connection != null) {
                connection.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }

    // generates a .csv file of the employee database
    public void generateEmployeeReport() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        connection = SQLConnection.connect();

        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM Employee");
            resultSet = preparedStatement.executeQuery();

            File employeeReport = new File("employeeReport" + LocalDate.now() + ".csv");
            PrintWriter printWriter = new PrintWriter(employeeReport);

            printWriter.println("employeeID, firstName, lastName, Role, username, password, secretQuestion, " +
                    "ansSecretQuestion, accountStatus, lastTableBooked");

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

                printWriter.printf("%d,%s,%s,%s,%s,%s,%s,%s,%s,%s%n",employeeID, firstName, lastName, Role, username,
                        password, secretQuestion, ansSecretQuestion, accountStatus, lastTableBooked);
            }
            printWriter.close();
        }
        catch (SQLException | FileNotFoundException e){
            e.printStackTrace();
        }
        finally {
            if (connection != null) {
                connection.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }
}
