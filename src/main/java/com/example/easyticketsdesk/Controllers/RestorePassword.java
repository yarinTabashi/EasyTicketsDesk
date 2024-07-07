package com.example.easyticketsdesk.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;

public class RestorePassword {
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField repasswordField;
    @FXML
    private Button restore_btn;

    public void restoreBtnClicked(MouseEvent mouseEvent) {
        System.out.println("restore clicked");
    }
}
