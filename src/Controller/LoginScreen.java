package Controller;

import DAO.AppointmentDAO;
import DAO.UsersDAO;
import Database.JDBC;
import Model.Appointments;
import Model.Users;
import Utility.LoginLogger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * Loginscreen controller
 */
public class LoginScreen implements Initializable {
    private String fxmlPath;
    @FXML private PasswordField passwordField;
    @FXML private TextField userNameField;
    public Button submitButton, exitButton;
    public Label userLocal, userNameLabel, userPasswordLabel, loginLabel;
    static String userName = "";
    static String Password = "";
    static String Login = "";
    static String errorTitle = "";
    static String errorHeader = "";
    static String errorText = "";
    static String Message = "";
    static String Language = "";
    ResourceBundle localSettings = ResourceBundle.getBundle("Properties/lang", Locale.getDefault());

    /**
     * Process's login, checks login credentials and changes scenes if they are correct
     * @param actionEvent
     * @throws SQLException
     */
    public void submitLogin(ActionEvent actionEvent) throws SQLException {
        try {
            UsersDAO.usersList.clear();
            if (!userNameField.getText().equals("admin") || !passwordField.getText().equals("admin")) {
                LoginLogger.logUser(userNameField.getText(), passwordField.getText());
                Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
                alertConfirm.setTitle(errorTitle);
                alertConfirm.setContentText(errorText);
                alertConfirm.showAndWait();
            } else {
                String sql = String.format("SELECT User_ID, User_Name, Password FROM users WHERE User_Name = '%s' AND Password = '%s'", userNameField.getText(), passwordField.getText());
                PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int userId = resultSet.getInt("User_ID");
                    String user_name = resultSet.getString("User_Name");
                    String passWord = resultSet.getString("Password");
                    Users user = new Users(userId, user_name, passWord);
                    UsersDAO.usersLoggedInList.add(user);
                }
                checkForUpcomingAppointment();
                fxmlPath = "/View/MainScreen.fxml";
                switchScene(actionEvent, "Appointment Manager");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * checks for upcoming appointments and prompts if one is upcoming within 15 minutes for the user logging in
     * @throws SQLException
     */
    public void checkForUpcomingAppointment() throws SQLException {
        for (Appointments Appointment : AppointmentDAO.getAllAppointments()) {
            LocalDate startTime = Appointment.getStart().toLocalDateTime().toLocalDate();
            LocalTime endTime = Appointment.getStart().toLocalDateTime().toLocalTime();
            ZoneId zone = ZoneId.of(TimeZone.getDefault().getID());
            ZonedDateTime futureAppointment = ZonedDateTime.of(startTime, endTime, zone).withZoneSameInstant(zone);
            LocalTime timeNow = LocalTime.now();
            LocalDate dateNow = LocalDate.now();
            LocalDateTime current = LocalDateTime.of(dateNow, timeNow);
            LocalTime timeLater = LocalTime.now().plusMinutes(15);
            LocalDateTime fullDateLater = LocalDateTime.of(dateNow, timeLater);
            if (futureAppointment.toLocalDateTime().isBefore(fullDateLater) && futureAppointment.toLocalDateTime().isAfter(current)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(errorTitle);
                alert.setContentText(errorText);
                alert.showAndWait();
            }
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

    /**
     * exits the program
     */
    public void exitProgram() {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            if (Locale.getDefault().getLanguage().equals("fr")) {
                userName = localSettings.getString("username");
                Password = localSettings.getString("password");
                Login = localSettings.getString("login");
                errorTitle = localSettings.getString("errorTitle");
                errorHeader = localSettings.getString("errorHeader");
                errorText = localSettings.getString("errorText");
                Message = localSettings.getString("message");
                Language = localSettings.getString("language");
                userLocal.setText(Language);
                userPasswordLabel.setText(Password);
                loginLabel.setText(Login);
                submitButton.setText(Login);
                userNameLabel.setText(userName);
            } else{
                Locale.getDefault().getLanguage().equals("en");
                userName = localSettings.getString("username");
                Password = localSettings.getString("password");
                Login = localSettings.getString("login");
                errorTitle = localSettings.getString("errorTitle");
                errorHeader = localSettings.getString("errorHeader");
                errorText = localSettings.getString("errorText");
                Message = localSettings.getString("message");
                Language = localSettings.getString("language");
                userLocal.setText(Language);
                userPasswordLabel.setText(Password);
                loginLabel.setText(Login);
                submitButton.setText(Login);
                userNameLabel.setText(userName);
            }
        } catch (MissingResourceException e) {

        }
    }
}
