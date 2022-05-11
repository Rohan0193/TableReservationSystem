package main.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class util {

    public static void changeScenes(Button btn, String fxmlPath){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(util.class.getClassLoader().getResource(fxmlPath)));

            Stage window = (Stage) btn.getScene().getWindow();
            window.setScene(new Scene(root, 1000, 800));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void displayPopup(Button btn, String fxmlPath, int width, int height){
        try{
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(Objects.requireNonNull(util.class.getClassLoader().getResource(fxmlPath)));
            stage.setScene(new Scene(root, width, height));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(btn.getScene().getWindow());
            stage.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static boolean confirmationAlert(String title, String issue, String instructions) {
        boolean status = false;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
        alert.setTitle(title);
        alert.setHeaderText(issue);
        alert.setContentText(instructions);

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.YES)
        {
            status = true;
        }
        else
        {
            alert.close();
        }
        return status;
    }

    public static void errorAlert(String title, String issue, String instructions){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(issue);
        alert.setContentText(instructions);
        alert.show();
    }

    public static void successAlert(String title, String issue, String instructions){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Image image = new Image(String.valueOf(util.class.getResource("/img/successicon.png")));
        ImageView imageview = new ImageView(image);
        imageview.setFitHeight(48);
        imageview.setFitWidth(48);
        alert.getDialogPane().setGraphic(imageview);
        alert.setTitle(title);
        alert.setHeaderText(issue);
        alert.setContentText(instructions);
        alert.show();
    }

    public static boolean warningAlert(String title, String issue, String instructions) {
        boolean status = false;
        Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.NEXT, ButtonType.CANCEL);
        alert.setTitle(title);
        alert.setHeaderText(issue);
        alert.setContentText(instructions);

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.NEXT)
        {
            status = true;
        }
        else
        {
            alert.close();
        }
        return status;
    }

}
