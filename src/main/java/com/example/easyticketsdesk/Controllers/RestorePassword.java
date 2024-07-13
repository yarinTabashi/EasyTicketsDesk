package com.example.easyticketsdesk.Controllers;
import com.example.easyticketsdesk.RequestsUtility;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;

public class RestorePassword {
    private String currentJWT;
    @FXML
    private MainSign mainScreenController;

    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField repasswordField;
    @FXML
    private Button restore_btn;
    @FXML
    private Label warning_label;

    public void restoreBtnClicked(MouseEvent mouseEvent) {
        if (checkMatching()){
            RequestsUtility.updatePassword(currentJWT, passwordField.getText());
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

    public void setMainScreenController(MainSign mainSign) {
        this.mainScreenController = mainScreenController;
    }

    public void setCurrentJWT(String currentJWT){
        this.currentJWT = currentJWT;
    }
}
