package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

import java.io.IOException;

public class LoginScreen {
    private String fxmlPath;

    @FXML private PasswordField passwordField;
    @FXML private TextField userNameField;



    public void submitLogin(ActionEvent actionEvent) {
        if(!userNameField.getText().equals("tomato") && !passwordField.getText().equals("tomato")) {
            System.out.println(userNameField.getText());
            System.out.println(passwordField.getText());
            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
            alertConfirm.setTitle("Username or password is incorrect ");
            alertConfirm.setContentText("Please try again");
            alertConfirm.showAndWait();
        } else {
            fxmlPath = "/View/MainScreen.fxml";
            switchScene(actionEvent, "Appointment Manager");
        }
    }

    /**
     * Helper function used in programs to close a scene and open another
     * @param event - an event handler passed to change scene
     */
    public void switchScene(ActionEvent event, String title)  {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exitProgram() {
        System.exit(0);
    }
}
