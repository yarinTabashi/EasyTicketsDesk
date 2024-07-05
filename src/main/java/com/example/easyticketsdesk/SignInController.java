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
import javafx.stage.Stage;

import java.io.IOException;

public class SignInController {
    @FXML
    private TextField email_field;

    @FXML
    private Label forgot_password_label;

    @FXML
    private PasswordField password_field;

    @FXML
    private Button signin_btn;

    @FXML
    private Button signup_btn;

    @FXML
    void forgotPasswordClicked(MouseEvent event) {

    }

    @FXML
    void signInBtnClicked(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainwindow.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Get the stage information
            Stage stage = (Stage) signin_btn.getScene().getWindow();

            // Switch scene (move to the signup window)
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void signUpClicked(MouseEvent event) {
        // Load the FXML file for the signup window
        FXMLLoader loader = new FXMLLoader(getClass().getResource("signup.fxml"));
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
