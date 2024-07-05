package com.example.easyticketsdesk;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class SignupController {

    @FXML
    private Label already_exists;

    @FXML
    private TextField email_field;

    @FXML
    private TextField first_name_field;

    @FXML
    private TextField last_name_field;

    @FXML
    private Pane left_pane;

    @FXML
    private PasswordField password_field;

    @FXML
    private PasswordField register_repassword_field;

    @FXML
    private Button signup_btn;

    @FXML
    void already_exists_clicked(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("signin.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Get the stage information
            Stage stage = (Stage) already_exists.getScene().getWindow();

            // Switch scene (move to the signup window)
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void signup_clicked(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainwindow.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Get the stage information
            Stage stage = (Stage) signup_btn.getScene().getWindow();

            // Switch scene (move to the signup window)
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
