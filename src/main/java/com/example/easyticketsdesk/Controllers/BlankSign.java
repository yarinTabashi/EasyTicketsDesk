package com.example.easyticketsdesk.Controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import java.io.IOException;

public class BlankSign {
    @FXML
    private BorderPane border_pane;

    @FXML
    public void initialize() {
        // Load initial content, e.g., SignIn screen
        loadSignInScreen();
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

            // Access the controller to pass border_pane
            SignupController signUpController = loader.getController();
            signUpController.setMainScreenController(this);

            // Set the right content of border_pane to signInRoot
            border_pane.setRight(signUpRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadOTP() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/easyticketsdesk/gui-fxml/otpscreen.fxml"));
            Parent otpScreenRoot = loader.load();

            // Access the controller to pass border_pane
            OtpController otpScreenController = loader.getController();
            otpScreenController.setMainScreenController(this);

            // Set the right content of border_pane to signInRoot
            border_pane.setRight(otpScreenRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}