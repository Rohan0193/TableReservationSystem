package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.model.registerModel;
import main.model.util;

public class registerController {
    public registerModel registerModel = new registerModel();

    @FXML
    private Button registerBtn;
    @FXML
    private TextField firstNameInput;
    @FXML
    private TextField lastNameInput;
    @FXML
    private TextField usernameInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private ComboBox<String> secretQuestionSelection;
    @FXML
    private TextField secretQuestionAns;
    @FXML
    private Label isConnected;
    @FXML
    private Button backBtn;
    @FXML
    private ComboBox<String> roleSelection;

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
        if (registerModel.isDbConnected()){
            isConnected.setText("Connected");
        }else{
            isConnected.setText("Not Connected");
        }
    }

    public void handleRegisterBtn() throws Exception {
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
                    String title = "Register New User";
                    String issue = "New user successfully registered";
                    String instructions = "Please login by pressing OK to continue";
                    handleBackBtn();
                    util.successAlert(title,issue,instructions);
                } else {
                    String title = "Register New User";
                    String issue = "Username already taken";
                    String instructions = "Please enter another username";
                    util.errorAlert(title,issue,instructions);
                }
            } else {
                String title = "Register New User";
                String issue = "Passwords do not match";
                String instructions = "Please re-enter password fields and ensure they match";
                util.errorAlert(title,issue,instructions);
            }
        }
        else {
            String title = "Register New User";
            String issue = "Empty Fields";
            String instructions = "Please fill in all fields before proceeding";
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

    // handle back btn to go from register back to login without registering
    public void handleBackBtn() throws Exception {
        String page = "main/ui/login.fxml";
        util.changeScenes(backBtn, page);
    }
}
