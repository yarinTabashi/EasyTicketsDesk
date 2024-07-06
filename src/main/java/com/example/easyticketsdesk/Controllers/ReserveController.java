package com.example.easyticketsdesk.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ReserveController {
    @FXML
    private Label event_name_label;

    @FXML
    private Label location_and_venue_label;

    @FXML
    private Label menu_explore;

    @FXML
    private Label menu_my_pref;

    @FXML
    private Label menu_my_tickets;

    @FXML
    private Button reserve_btn;

    @FXML
    private Label selected_desc_label;

    @FXML
    void menu_explore_clicked(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/easyticketsdesk/gui-fxml/mainwindow.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Get the stage information
            Stage stage = (Stage) menu_explore.getScene().getWindow();

            // Switch scene (move to the signup window)
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void menu_my_pref_clicked(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/easyticketsdesk/gui-fxml/mypreferences.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Get the stage information
            Stage stage = (Stage) menu_my_pref.getScene().getWindow();

            // Switch scene (move to the signup window)
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void menu_my_tickets_clicked(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/easyticketsdesk/gui-fxml/myorders.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Get the stage information
            Stage stage = (Stage) menu_my_tickets.getScene().getWindow();

            // Switch scene (move to the signup window)
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void reserve_cliked(MouseEvent event) {
    }
}
