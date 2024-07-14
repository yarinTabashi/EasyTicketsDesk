package com.example.easyticketsdesk.Controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import java.io.IOException;

public class MainSign {
    @FXML
    private BorderPane border_pane;
    private String currentJWT, currentEmail;

    @FXML
    public void initialize() {
        loadSignInScreen();
    }

    public void setCurrentEmail(String currentEmail){
        this.currentEmail = currentEmail;
    }

    public void loadSignInScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/easyticketsdesk/gui-fxml/signin.fxml"));
            Parent signInRoot = loader.load();

            // Access the controller to pass border_pane
            SignInController signInController = loader.getController();
            signInController.setMainScreenController(this);

            // Set the right content of border_pane to signInRoot
            border_pane.setRight(signInRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSignUpScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/easyticketsdesk/gui-fxml/signup.fxml"));
            Parent signUpRoot = loader.load();

            SignupController signUpController = loader.getController();
            signUpController.initialize(this);

            // Set the right content of border_pane to signInRoot
            border_pane.setRight(signUpRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadOTP() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/easyticketsdesk/gui-fxml/otp.fxml"));
            Parent otpScreenRoot = loader.load();

            // Access the controller to pass border_pane
            OtpController otpScreenController = loader.getController();
            otpScreenController.setMainScreenController(this);
            otpScreenController.setEmailAddressAndSend(this.currentEmail);

            // Set the right content of border_pane to signInRoot
            border_pane.setRight(otpScreenRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadRestorePassword(String currentJWT){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/easyticketsdesk/gui-fxml/restore_password.fxml"));
            Parent otpScreenRoot = loader.load();

            // Access the controller to pass border_pane
            RestorePassword restoreScreenController = loader.getController();
            restoreScreenController.setMainScreenController(this);
            restoreScreenController.setCurrentJWT(currentJWT);

            // Set the right content of border_pane to signInRoot
            border_pane.setRight(otpScreenRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadForgotPassword(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/easyticketsdesk/gui-fxml/restore_password.fxml"));
            Parent restorePasswordRoot = loader.load();

            // Set the right content of border_pane to signInRoot
            border_pane.setRight(restorePasswordRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}