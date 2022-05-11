package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.model.*;

import java.sql.SQLException;

public class employeeUpdateDetailsController {
    // Note: this class uses some methods found in the below classes...
    adminUpdateUserPopupModel adminUpdateUserModel = new adminUpdateUserPopupModel();
    employeeMenuModel employeeMenuModel = new employeeMenuModel();

    @FXML
    private TextField firstNameInput;
    @FXML
    private TextField lastNameInput;
    @FXML
    private TextField usernameInput;
    @FXML
    private TextField secretQuestionAns;
    @FXML
    private ComboBox<String> secretQuestionSelection;
    @FXML
    private Button exitBtn, updateUserBtn;

    // create list of options for the secretQuestion combo box
    ObservableList<String> secretQuestionOptions = FXCollections.observableArrayList("What is the name of your favourite Pet?",
            "Where did you go to high school/college?", "What city were you born in?", "What is your favourite movie?",
            "What is your mother’s maiden name?");

    @FXML
    private void initialize() throws SQLException {
        secretQuestionSelection.setItems(secretQuestionOptions);
        employeeObj employee = adminUpdateUserModel.getUserData(Datastore.getInstance().getEmployeeID());
        firstNameInput.setText(employee.getFirstName());
        lastNameInput.setText(employee.getLastName());
        usernameInput.setText(employee.getUsername());
        secretQuestionSelection.setValue(employee.getSecretQuestion());
        secretQuestionAns.setText(employee.getAnsSecretQuestion());
    }

    public void handleUpdateUserBtn() throws SQLException {
        String title = "Update User Details";
        String issue = "Are you sure you wish to make these changes?";
        String instructions = "Select YES to proceed or NO to cancel";
        if (util.confirmationAlert(title,issue,instructions)){
            if (checkUserFields(firstNameInput.getText(), lastNameInput.getText(), usernameInput.getText(),
                    secretQuestionSelection, secretQuestionAns.getText())){
                int employeeID = Datastore.getInstance().getEmployeeID();
                String firstName = firstNameInput.getText();
                String lastName = lastNameInput.getText();
                String username = usernameInput.getText();
                String secretQuestion = secretQuestionSelection.getValue();
                String ansSecretQuestion = secretQuestionAns.getText();
                if (adminUpdateUserModel.isUsernameUnique(username,Datastore.datastore.getUsername())){
                    employeeMenuModel.updateUserData(employeeID,firstName,lastName,username,secretQuestion,ansSecretQuestion);
                    String title1 = "Update User Details";
                    String issue1 = "Details updated successfully";
                    String instructions1 = "Press OK to continue";
                    util.successAlert(title1,issue1,instructions1);
                    Stage stage = (Stage) exitBtn.getScene().getWindow();
                    stage.close();
                }
                else {
                    String title1 = "Update User Details";
                    String issue1 = "Username is already taken";
                    String instructions1 = "Please choose another username";
                    util.errorAlert(title1,issue1,instructions1);
                }
            }
            else {
                String title1 = "Update User Details";
                String issue1 = "Some fields are empty";
                String instructions1 = "Please fill in all fields before proceeding";
                util.errorAlert(title1,issue1,instructions1);
            }
        }
    }

    public void handleExitBtn(){
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }

    public boolean checkUserFields(String firstName, String lastName, String username, ComboBox<String> secretQuestion,
                                   String ansSecretQuestion){
        boolean result = true;
        if (firstName.trim().isEmpty() || lastName.trim().isEmpty() || username.trim().isEmpty()
                || secretQuestion.getSelectionModel().isEmpty() || ansSecretQuestion.trim().isEmpty()){
            result = false;
        }
        return result;
    }
}
