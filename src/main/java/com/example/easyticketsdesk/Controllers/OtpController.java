package com.example.easyticketsdesk.Controllers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import java.net.URL;
import java.util.ResourceBundle;

// TODO: Fix behavior to restrict each text field to one digit and auto-focus to the next field on input.
public class OtpController implements Initializable {

    @FXML
    private TextField textField1;

    @FXML
    private TextField textField2;

    @FXML
    private TextField textField3;

    @FXML
    private TextField textField4;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize method
    }

    @FXML
    private void handleDigitInput(KeyEvent event) {
        TextField source = (TextField) event.getSource();
        if (source.getText().length() >= 1) {
            // If the current text field is already filled, consume the event
            event.consume();
            return;
        }

        // Allow only digits (0-9)
        String character = event.getCharacter();
        if (!character.matches("[0-9]")) {
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
                // Do something when the last text field is filled, e.g., submit
                break;
            default:
                break;
        }
    }
}