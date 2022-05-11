package main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.model.resetPasswordModel;
import main.model.util;

import java.sql.SQLException;

public class resetPasswordController {
    public resetPasswordModel resetPasswordModel = new resetPasswordModel();

    @FXML
    private TextField usernameInput;
    @FXML
    private Button generateSecretQuestionBtn;
    @FXML
    private TextField displaySecretQuestion;
    @FXML
    private TextField ansSecretQInput;
    @FXML
    private Button resetPasswordBtn;
    @FXML
    private Label userMessage;
    @FXML
    private Button proceedBtn;
    @FXML
    private Label isConnected;
    @FXML
    private Button backBtn;

    // method to handle back button to go from resetPassword back to login Page
    public void handleBackBtn() throws Exception {
        String page = "main/ui/login.fxml";
        util.changeScenes(backBtn, page);
    }

    public void handleGenSecretQBtn() throws SQLException {
        String username = usernameInput.getText();
        String secretQuestion = resetPasswordModel.findSecretQuestion(username);

        if (secretQuestion.equals("invalid username")){
            userMessage.setText("Invalid username! Please try again");
        }
        else{
            displaySecretQuestion.setText(secretQuestion);
        }
    }

    public void handleResetPasswordBtn() throws SQLException {
        String answer = ansSecretQInput.getText();

        if (!answer.isEmpty()){
            if (resetPasswordModel.checkSecretQuestionAns(answer)){
                String newPassword = resetPasswordModel.generateNewPassword();
                resetPasswordModel.updatePasswordDatabase();
                userMessage.setText("SUCCESS! New Password: "+ newPassword);
                proceedBtn.setDisable(false);
            }
            else {
                userMessage.setText("ERROR: Secret Question Answer is incorrect! Please try again");
            }
        }
        else {
            userMessage.setText("Please fill out all fields");
        }
    }

    // proceed btn handler to go from resetPassword page back to login page once successfully reset
    public void handleProceedBtn() {
        String page = "main/ui/login.fxml";
        util.changeScenes(proceedBtn, page);
    }

}
