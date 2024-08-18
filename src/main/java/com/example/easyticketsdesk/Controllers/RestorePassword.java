package com.example.easyticketsdesk.Controllers;
import com.example.easyticketsdesk.Entities.UserProfile;
import com.example.easyticketsdesk.RequestsUtility;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class RestorePassword {
    private String currentJWT;
    @FXML
    private MainSign mainSignController;
    @FXML
    private PasswordField passwordField, repasswordField;
    @FXML
    private Button restore_btn;
    @FXML
    private Label warning_label;

    public void setMainScreenController(MainSign mainSignController) {
        this.mainSignController = mainSignController;
    }
    public void setCurrentJWT(String currentJWT){
        this.currentJWT = currentJWT;
    }

    @FXML
    public void restoreBtnClicked(MouseEvent mouseEvent) {
        if (checkMatching()){
            boolean succeed = RequestsUtility.updatePassword(currentJWT, passwordField.getText());
            if (succeed){
                this.mainSignController.loadSignInScreen();
            }
            else {
                // Show Alert
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Password update failed");
                alert.setContentText("An error occurred while updating the password");
                alert.showAndWait();
            }
            mainSignController.loadSignInScreen();
        }
    }

    private boolean checkMatching(){
        if (this.passwordField.getText().equals(this.repasswordField.getText())){
            return true;
        }
        else {
            this.warning_label.setText("Passwords don't match");
            return false;
        }
    }
}
