package main.controller;

// NOTE THIS CONTROLLER CLASS USES THE REGISTERMODEL AS IT HAS THE EXACT SAME FUNCTION AND LOGIC: REGISTERING A NEW USER

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.model.registerModel;
import main.model.util;

public class adminAddUserPopupController {
    registerModel registerModel = new registerModel();

    @FXML
    private TextField firstNameInput;
    @FXML
    private TextField lastNameInput;
    @FXML
    private TextField usernameInput;
    @FXML
    private TextField passwordInput;
    @FXML
    private TextField confirmPassword;
    @FXML
    private TextField secretQuestionAns;
    @FXML
    private ComboBox<String> secretQuestionSelection;
    @FXML
    private ComboBox<String> roleSelection;
    @FXML
    private Button addUserBtn, exitBtn;

    // create list of options for the secretQuestion combo box
    ObservableList<String> secretQuestionOptions = FXCollections.observableArrayList("What is the name of your favourite Pet?",
            "Where did you go to high school/college?", "What city were you born in?", "What is your favourite movie?",
            "What is your mother’s maiden name?");

    // create list of options for the roleSelection combo box
    ObservableList<String> roleSelectionOptions = FXCollections.observableArrayList("Employee",
            "Admin");

    // drop down menu method for the secretQuestion combo box
    @FXML
    private void initialize() {
        secretQuestionSelection.setItems(secretQuestionOptions);
        roleSelection.setItems(roleSelectionOptions);
    }

    public void handleAddUserBtn() {
        //check that all user fields have been filled in
        if (checkUserFields(firstNameInput.getText(), lastNameInput.getText(), usernameInput.getText(), passwordInput.getText(),
                secretQuestionSelection, secretQuestionAns.getText(), roleSelection)) {
            //check that password and reentered password match
            if (passwordInput.getText().equals(confirmPassword.getText())) {
                String firstName = firstNameInput.getText();
                String lastName = lastNameInput.getText();
                String username = usernameInput.getText();
                String password = passwordInput.getText();
                String secretQuestion = secretQuestionSelection.getValue();
                String ansSecretQuestion = secretQuestionAns.getText();
                String role = roleSelection.getValue();
                //check that username is not already taken
                if (registerModel.isUsernameUnique(username)){
                    registerModel.registerUser(firstName, lastName, role, username, password, secretQuestion, ansSecretQuestion);
                    String title = "Add User";
                    String issue = "User successfully registered and added";
                    String instructions = "Select OK to continue";
                    handleExitBtn();
                    util.successAlert(title,issue,instructions);
                } else {
                    String title = "Add User";
                    String issue = "Username already taken";
                    String instructions = "Please try another username";
                    util.errorAlert(title,issue,instructions);
                }
            } else {
                String title = "Add User";
                String issue = "Password does not match";
                String instructions = "Please re-enter passwords to ensure they match";
                util.errorAlert(title,issue,instructions);
            }
        }
        else {
            String title = "Add User";
            String issue = "Not all fields are filled in";
            String instructions = "Please ensure all fields are filled in";
            util.errorAlert(title,issue,instructions);
        }
    }

    // checks whether all the registration fields are filled in
    public boolean checkUserFields(String firstName, String lastName, String username, String password, ComboBox<String> secretQuestion,
                                   String ansSecretQuestion, ComboBox<String> role){
        boolean result = true;
        if (firstName.trim().isEmpty() || lastName.trim().isEmpty() || username.trim().isEmpty() || password.trim().isEmpty()
                || secretQuestion.getSelectionModel().isEmpty() || ansSecretQuestion.trim().isEmpty() || role.getSelectionModel().isEmpty()){
            result = false;
        }
        return result;
    }

    public void handleExitBtn(){
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }

}
