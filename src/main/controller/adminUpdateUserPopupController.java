package main.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.model.Datastore;
import main.model.adminUpdateUserPopupModel;
import main.model.employeeObj;
import main.model.util;

import java.sql.SQLException;

public class adminUpdateUserPopupController {
    adminUpdateUserPopupModel adminUpdateUserPopupModel = new adminUpdateUserPopupModel();

    @FXML
    private TextField firstNameInput;
    @FXML
    private TextField lastNameInput;
    @FXML
    private TextField usernameInput;
    @FXML
    private TextField passwordInput;
    @FXML
    private TextField secretQuestionAns;
    @FXML
    private ComboBox<String> secretQuestionSelection;
    @FXML
    private ComboBox<String> roleSelection;
    @FXML
    private ComboBox<String> accountStatusSelection;
    @FXML
    private Button addUserBtn, exitBtn;

    // create list of options for the secretQuestion combo box
    ObservableList<String> secretQuestionOptions = FXCollections.observableArrayList("What is the name of your favourite Pet?",
            "Where did you go to high school/college?", "What city were you born in?", "What is your favourite movie?",
            "What is your mother’s maiden name?");

    // create list of options for the roleSelection combo box
    ObservableList<String> roleSelectionOptions = FXCollections.observableArrayList("Employee",
            "Admin");

    // create list of options for the roleSelection combo box
    ObservableList<String> accountStatusOptions = FXCollections.observableArrayList("Active",
            "Deactivated");

    @FXML
    private void initialize() throws SQLException {
        secretQuestionSelection.setItems(secretQuestionOptions);
        roleSelection.setItems(roleSelectionOptions);
        accountStatusSelection.setItems(accountStatusOptions);
        employeeObj employee = adminUpdateUserPopupModel.getUserData(Datastore.getInstance().getEmployeeIDUpdateUser());
        Datastore.getInstance().setCurrentUsername(employee.getPassword());
        firstNameInput.setText(employee.getFirstName());
        lastNameInput.setText(employee.getLastName());
        usernameInput.setText(employee.getUsername());
        passwordInput.setText(employee.getPassword());
        secretQuestionSelection.setValue(employee.getSecretQuestion());
        secretQuestionAns.setText(employee.getAnsSecretQuestion());
        roleSelection.setValue(employee.getRole());
        accountStatusSelection.setValue(employee.getAccountStatus());
    }

    public void handleExitBtn(){
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }

    public void handleUpdateUserBtn() throws SQLException {
        // checks if all fields are filled in
        if (checkUserFields(firstNameInput.getText(), lastNameInput.getText(), roleSelection, usernameInput.getText(),
                passwordInput.getText(), secretQuestionSelection, secretQuestionAns.getText(), accountStatusSelection.getValue())){
            int employeeID = Datastore.getInstance().getEmployeeIDUpdateUser();
            String firstname = firstNameInput.getText();
            String lastname = lastNameInput.getText();
            String role = roleSelection.getValue();
            String username = usernameInput.getText();
            String password = passwordInput.getText();
            String secretQuestion = secretQuestionSelection.getValue();
            String ansSecretQuestion = secretQuestionAns.getText();
            String accountStatus = accountStatusSelection.getValue();
            //checks if username is unique
            if (adminUpdateUserPopupModel.isUsernameUnique(username,Datastore.getInstance().getCurrentUsername())){
                String title = "Update User Details";
                String issue = "Are you sure you wish to make these changes?";
                String instructions = "Please select YES to proceed or No to cancel";
                if(util.confirmationAlert(title,issue,instructions)){
                    adminUpdateUserPopupModel.updateUserData(employeeID,firstname,lastname,role,username,password,secretQuestion,
                            ansSecretQuestion,accountStatus);
                    handleExitBtn();
                    String title1 = "Update User Details";
                    String issue1 = "User Details Successfully Updated";
                    String instructions1 = "Please select OK to continue";
                    util.successAlert(title1,issue1,instructions1);
                }
            } else {
                String title = "Update User Details";
                String issue = "Username already taken";
                String instructions = "Please try another username";
                util.errorAlert(title,issue,instructions);
            }
        } else {
            String title = "Update User Details";
            String issue = "Not all fields are filled in";
            String instructions = "Please ensure all fields are filled in";
            util.errorAlert(title,issue,instructions);
        }
    }

    // checks whether all the registration fields are filled in
    public boolean checkUserFields(String firstName, String lastName,ComboBox<String> role, String username, String password, ComboBox<String> secretQuestion,
                                   String ansSecretQuestion, String accountStatus){
        boolean result = true;
        if (firstName.trim().isEmpty() || lastName.trim().isEmpty() || username.trim().isEmpty() || password.trim().isEmpty()
                || secretQuestion.getSelectionModel().isEmpty() || ansSecretQuestion.trim().isEmpty() || role.getSelectionModel().isEmpty()
                    || accountStatus.trim().isEmpty()){
            result = false;
        }
        return result;
    }

}
