package com.example.easyticketsdesk.Controllers;
import com.example.easyticketsdesk.RequestsUtility;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import org.json.JSONException;
import org.json.JSONObject;

public class OtpController implements Initializable {
    private int timerSeconds = 60;
    private Timeline timer;
    public Button submit_btn;
    @FXML
    private Label warning_label;
    @FXML
    private Label timer_label;
    @FXML
    private MainSign mainScreenController;
    @FXML
    private TextField textField1;

    @FXML
    private TextField textField2;

    @FXML
    private TextField textField3;

    @FXML
    private TextField textField4;
    @FXML
    private TextField textField5;
    @FXML
    private TextField textField6;
    private String emailAddress; // The email of the user whose trying to sign in.
    private boolean isIntervalOver = false;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize method
        startTimer();
    }

    private void startTimer() {
        this.isIntervalOver = false;
        timer = new Timeline(new KeyFrame(
                javafx.util.Duration.seconds(1),
                event -> {
                    timerSeconds--;
                    timer_label.setText("Time remaining:\n " + timerSeconds + " seconds");

                    if (timerSeconds <= 0) {
                        timer.stop();
                        this.isIntervalOver = true;
                        timer_label.setText("Resend OTP");
                    }
                }
        ));
        timer.setCycleCount(Animation.INDEFINITE);
        timer.play();
    }

    public void setEmailAddressAndSend(String emailAddress){
        this.emailAddress = emailAddress;
        RequestsUtility.sendOTP(this.emailAddress);
    }

    public void setMainScreenController(MainSign mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    private void setupTextFields() {
        // Set event handlers for all text fields
        textField1.setOnKeyTyped(this::handleDigitInput);
        textField2.setOnKeyTyped(this::handleDigitInput);
        textField3.setOnKeyTyped(this::handleDigitInput);
        textField4.setOnKeyTyped(this::handleDigitInput);
        textField5.setOnKeyTyped(this::handleDigitInput);
        textField6.setOnKeyTyped(this::handleDigitInput);
    }

    @FXML
    private void handleDigitInput(KeyEvent event) {
        TextField source = (TextField) event.getSource();

        // Allow only one digit (0-9)
        if (!event.getCharacter().matches("[0-9]")) {
            event.consume();
            return;
        }

        // Move focus to the next text field
        switch (source.getId()) {
            case "textField1":
                textField2.requestFocus();
                break;
            case "textField2":
                textField3.requestFocus();
                break;
            case "textField3":
                textField4.requestFocus();
                break;
            case "textField4":
                textField5.requestFocus();
                // Focus stays on textField4, or do something else like submit
                break;
            case "textField5":
                textField6.requestFocus();
                // Focus stays on textField4, or do something else like submit
                break;
            default:
                break;
        }
    }

    public void submit_clicked(MouseEvent mouseEvent) {
        String otpString = textField1.getText() + textField2.getText() + textField3.getText() + textField4.getText() + textField5.getText() + textField6.getText();
        try {
            int otp = Integer.parseInt(otpString);
            JSONObject jsonObject = RequestsUtility.verifyOTP("yafa@gmail.com", otp);
            if (jsonObject == null){
                this.warning_label.setText("Invalid OTP. Try again");
            }
            else {
                System.out.println("OTP verification successful");
                this.mainScreenController.loadRestorePassword(jsonObject.getString("token"));
            }
        } catch (NumberFormatException e) {
            this.warning_label.setText("Invalid OTP format. Enter digits only.");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void resend_clicked(MouseEvent mouseEvent) {
        if  (isIntervalOver){
            RequestsUtility.sendOTP(this.emailAddress);
            startTimer();
        }
    }
}