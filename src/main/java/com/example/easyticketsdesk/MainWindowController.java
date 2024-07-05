package com.example.easyticketsdesk;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindowController {
    @FXML
    private Label menu_explore_btn;

    @FXML
    private Label menu_my_tickets_btn;

    @FXML
    void explore_clicked(MouseEvent event) {
    }

    @FXML
    void my_tickets_clicked(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("myorders.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Get the stage information
            Stage stage = (Stage) menu_my_tickets_btn.getScene().getWindow();

            // Switch scene (move to the signup window)
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}