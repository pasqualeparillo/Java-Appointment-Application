package Controller;

import DAO.AppointmentDAO;
import Model.Appointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 * Past Appointments report controller
 */
public class PastAppointments implements Initializable {
    private static String fxmlPath;
    public static ObservableList<Appointments> pastAppointments = FXCollections.observableArrayList();
    @FXML
    private TableView<Appointments> appointmentsTable;
    @FXML private TableColumn<Appointments, Integer> Appointment_ID;
    @FXML private TableColumn<Appointments, Integer> Appointment_Customer_ID;
    @FXML private TableColumn<Appointments, Integer> Appointment_User_ID;
    @FXML private TableColumn<Appointments, Integer> Appointment_Contact_ID;
    @FXML private TableColumn<Appointments, String> Appointment_Title;
    @FXML private TableColumn<Appointments, String> Appointment_Description;
    @FXML private TableColumn<Appointments, String> Appointment_Location;
    @FXML private TableColumn<Appointments, String> Appointment_Type;
    @FXML private TableColumn<Appointments, String> Appointment_Start;
    @FXML private TableColumn<Appointments, String> Appointment_End;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pastAppointments.clear();
        try {
            populatePastAppointments();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Populates past appointments list and sets table
     * @throws SQLException
     */
    public void populatePastAppointments() throws SQLException {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        LocalDateTime fullDateNow = LocalDateTime.of(date, time);
        for(Appointments Appointment : AppointmentDAO.getAllAppointments()){
            if(Appointment.getStart().toLocalDateTime().isBefore(fullDateNow)){
                pastAppointments.add(Appointment);
            }
        }
        setAppointmentTable();
    }

    /**
     * Sets appointment table
     * @throws SQLException
     */
    private void setAppointmentTable() throws SQLException {
        AppointmentDAO.getAllAppointments();
        appointmentsTable.setItems(pastAppointments);
        Appointment_ID.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
        Appointment_Title.setCellValueFactory(new PropertyValueFactory<>("Title"));
        Appointment_Description.setCellValueFactory(new PropertyValueFactory<>("Description"));
        Appointment_Location.setCellValueFactory(new PropertyValueFactory<>("Location"));
        Appointment_Contact_ID.setCellValueFactory(new PropertyValueFactory<>("Contact_ID"));
        Appointment_Type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        Appointment_Start.setCellValueFactory(new PropertyValueFactory<>("Start"));
        Appointment_End.setCellValueFactory(new PropertyValueFactory<>("End"));
        Appointment_Customer_ID.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        Appointment_User_ID.setCellValueFactory(new PropertyValueFactory<>("User_ID"));
    }
    /**
     * Returns to mainscreen page
     * @param event
     */

    @FXML
    private void exitPage(ActionEvent event) {
        fxmlPath = "/View/MainScreen.fxml";
        switchScene(event, "Appointment Management");
    }
    /**
     * Helper used to switch scenes to fxmlpath variable
     * @param event
     * @param title
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
}