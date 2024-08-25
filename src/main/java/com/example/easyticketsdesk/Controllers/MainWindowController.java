package com.example.easyticketsdesk.Controllers;
import com.example.easyticketsdesk.Entities.Event;
import com.example.easyticketsdesk.Entities.UserProfile;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalTime;

public class MainWindowController {
    @FXML
    private BorderPane border_pane; // Container for dynamically replacing the right pane
    @FXML
    private Label name_label, email_label; // Profile data
    @FXML
    private Label explore_btn, preferences_btn, orders_btn, search_btn; // Menu "buttons"
    @FXML
    private ImageView edit_profile_icon;
    private ImageView logout_icon;

    // Additional necessary information
    private UserProfile userProfile;

    @FXML
    public void initialize() {
        activateAndInactivate(explore_btn);
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
            Parent searchRoot = loader.load();

            SearchController searchController = loader.getController();
            searchController.setMainWindowController(this);
            searchController.initializeScreen();

            // Set the right content of border_pane to signInRoot
            border_pane.setRight(searchRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load_dashboard(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/easyticketsdesk/gui-fxml/dashboard.fxml"));
            Parent dashboardRoot = loader.load();

            DashboardController dashboardController = loader.getController();
            dashboardController.setMainScreenController(this);
            dashboardController.set_upcoming_events();

            // Set the right content of border_pane to signInRoot
            border_pane.setRight(dashboardRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load_orders(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/easyticketsdesk/gui-fxml/orders.fxml"));
            Parent ordersRoot = loader.load();

            MyOrdersController ordersController = loader.getController();
            ordersController.initialize(this);

            // Set the right content of border_pane to signInRoot
            border_pane.setRight(ordersRoot);
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
            preferencesController.initializeComponents();

            // Set the right content of border_pane to signInRoot
            border_pane.setRight(preferencesRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getJwt(){
        return this.userProfile.getJwt();
    }

    public void SetUserData(UserProfile userProfile) {
        this.userProfile = userProfile;
        this.name_label.setText(userProfile.getFirstName() + " " + userProfile.getLastName());
        this.email_label.setText(userProfile.getEmail());
    }

    public void load_seats_screen(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/easyticketsdesk/gui-fxml/seats.fxml"));
            Parent seatsRoot = loader.load();

            SeatsController seatsController = loader.getController();
            seatsController.initialize(this, event);

            // Set the right content of border_pane to signInRoot
            border_pane.setRight(seatsRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load_edit_profile_screen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/easyticketsdesk/gui-fxml/edit_profile.fxml"));
            Parent editProfileRoot = loader.load();

            EditProfileController editProfileController = loader.getController();
            editProfileController.initialize(this, this.userProfile);

            // Set the right content of border_pane to signInRoot
            border_pane.setRight(editProfileRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getWelcomeText(){
        String firstName = this.userProfile.getFirstName();
        LocalTime currentTime = LocalTime.now();

        if (currentTime.isBefore(LocalTime.NOON)) {
            return "Good morning, " + firstName + "!";
        } else if (currentTime.isBefore(LocalTime.of(18, 0))) {
            return "Good afternoon, " + firstName + "!";
        } else {
            return "Good evening, " + firstName + "!";
        }
    }

    public void edit_profile_clicked(MouseEvent mouseEvent) {
        this.load_edit_profile_screen();
    }

    // Load the login screen and switch the scene
    private void switchToSignIn() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/easyticketsdesk/gui-fxml/main_sign.fxml"));
            Parent root = loader.load();

            // Access the controller instance
            MainSign controller = loader.getController();

            Scene scene = new Scene(root);
            Stage stage = (Stage) explore_btn.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void logout_clicked(MouseEvent mouseEvent) {
        this.switchToSignIn();
    }
}