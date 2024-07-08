package com.example.easyticketsdesk.Controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import java.io.IOException;

public class MainWindowController {
    @FXML
    private BorderPane border_pane;

    // Menu buttons
    @FXML
    private Label explore_btn;
    @FXML
    private Label preferences_btn;
    @FXML
    private Label orders_btn;
    @FXML
    private Label search_btn;
    @FXML
    private ImageView edit_profile_icon;
    private String currentJWT;

    @FXML
    public void initialize() {
        activateAndInactivate(explore_btn);
        load_dashboard();
    }

    @FXML
    void explore_clicked(MouseEvent event) {
        activateAndInactivate(explore_btn);
        load_dashboard();
    }

    public void orders_clicked(MouseEvent mouseEvent) {
        activateAndInactivate(orders_btn);
        load_orders();
    }

    public void preferences_clicked(MouseEvent mouseEvent) {
        activateAndInactivate(preferences_btn);
        load_preferences();
    }


    public void activateAndInactivate(Label currentBtn){
        // Remove "active" class from all buttons
        explore_btn.getStyleClass().remove("active");
        preferences_btn.getStyleClass().remove("active");
        orders_btn.getStyleClass().remove("active");
        search_btn.getStyleClass().remove("active");

        // Add "active" class to the current button
        currentBtn.getStyleClass().add("active");
    }

    public void search_clicked(MouseEvent mouseEvent) {
        activateAndInactivate(search_btn);
        load_search();
    }

    public void load_search(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/easyticketsdesk/gui-fxml/search.fxml"));
            Parent signInRoot = loader.load();

            // Set the right content of border_pane to signInRoot
            border_pane.setRight(signInRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load_dashboard(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/easyticketsdesk/gui-fxml/dashboard.fxml"));
            Parent signInRoot = loader.load();

            // Set the right content of border_pane to signInRoot
            border_pane.setRight(signInRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load_orders(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/easyticketsdesk/gui-fxml/orders.fxml"));
            Parent signInRoot = loader.load();

            // Set the right content of border_pane to signInRoot
            border_pane.setRight(signInRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load_preferences(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/easyticketsdesk/gui-fxml/preferences.fxml"));
            Parent preferencesRoot = loader.load();

            PreferencesController preferencesController = loader.getController();
            preferencesController.setMainScreenController(this);

            // Set the right content of border_pane to signInRoot
            border_pane.setRight(preferencesRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SetJwt(String token){
        this.currentJWT = token;
    }

    public String GetJwt(){
        return this.currentJWT;
    }

}