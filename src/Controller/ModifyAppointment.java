package Controller;

import DAO.AppointmentDAO;
import DAO.CustomersDAO;
import Database.JDBC;
import Model.Appointments;
import Model.Customers;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.*;
import java.util.ResourceBundle;

public class ModifyAppointment implements Initializable {
    private static String fxmlPath;
    // set time variables
    private final LocalTime businessOpen = LocalTime.of(8, 0);
    private final LocalTime businessClose = LocalTime.of(22, 0);
    private LocalDateTime startLocalDate;
    private LocalDateTime endLocalDate;
    private String Customer_Name;

    @FXML private TextField appID, appUserName, appCustomerID, appTitle, appDescription, appLocation, appUserID, appType;
    @FXML public ComboBox<LocalTime> appointmentStartTime;
    @FXML public ComboBox<LocalTime> appointmentEndTime;
    @FXML public DatePicker appointmentDate;

    @FXML
    private void exitPage(ActionEvent event) {
        fxmlPath = "/View/MainScreen.fxml";
        switchScene(event, "Appointment Management");
    }

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalTime busOpen = businessOpen;
        LocalTime endTime = businessClose.minusMinutes(15);
        //while before business close create 15 minute increment of times & add it to start.
        while (busOpen.isBefore(endTime.plusSeconds(1))){
            appointmentStartTime.getItems().add(busOpen);
            busOpen = busOpen.plusMinutes(15);
        }
        //set time to be the next available appointment
        LocalTime nextAppointment = businessOpen.plusMinutes(15);
        LocalTime busClose = businessClose;
        //add 15 minute time increments before close time after the initial appointment
        while(nextAppointment.isBefore(busClose.plusMinutes(15))){
            appointmentEndTime.getItems().add(nextAppointment);
            nextAppointment = nextAppointment.plusMinutes(15);
        }
            appID.setText(Integer.toString(MainScreen.getAppointmentToModify().getAppointment_ID()));
            appCustomerID.setText(Integer.toString(MainScreen.getAppointmentToModify().getCustomer_ID()));
            appTitle.setText(MainScreen.getAppointmentToModify().getTitle());
            appDescription.setText(MainScreen.getAppointmentToModify().getDescription());
            appLocation.setText(MainScreen.getAppointmentToModify().getLocation());
            appType.setText(MainScreen.getAppointmentToModify().getType());
            appUserID.setText(Integer.toString(MainScreen.getAppointmentToModify().getUser_ID()));
            appCustomerID.setText(Integer.toString(MainScreen.getAppointmentToModify().getCustomer_ID()));

            LocalDateTime dateStart = MainScreen.getAppointmentToModify().getStart().toLocalDateTime();
            LocalDateTime dateEnd = MainScreen.getAppointmentToModify().getEnd().toLocalDateTime();
            LocalDate date = dateStart.toLocalDate();
            LocalTime timeStart = dateStart.toLocalTime();
            LocalTime timeEnd = dateEnd.toLocalTime();

            //Update appointment values
            appointmentDate.setValue(date);
            appointmentStartTime.setValue(timeStart);
            appointmentEndTime.setValue(timeEnd);

            for(Customers C: CustomersDAO.getAllCustomers()) {
                if(MainScreen.getAppointmentToModify().getAppointment_ID() == C.getCustomer_ID()) {
                    appUserName.setText(C.getCustomer_Name());
                    Customer_Name = C.getCustomer_Name();
                }
            }
    }
    @FXML
    private void updateAppointment(ActionEvent event) throws SQLException {
        LocalDate appDate = appointmentDate.getValue();
        LocalTime start = appointmentStartTime.getValue();
        LocalTime end = appointmentEndTime.getValue();
        startLocalDate = LocalDateTime.of(appDate, start);
        endLocalDate = LocalDateTime.of(appDate, end);
        //set user time zone

        ZoneId userTimeZone = ZoneId.systemDefault();
        ZoneId easternTimeZone = ZoneId.of("US/Eastern");

        LocalTime dateTimeStartEST = ZonedDateTime.of(startLocalDate, userTimeZone).withZoneSameInstant(easternTimeZone).toLocalDateTime().toLocalTime();
        LocalTime dateTimeEndEST = ZonedDateTime.of(endLocalDate, userTimeZone).withZoneSameInstant(easternTimeZone).toLocalDateTime().toLocalTime();
        validateAppointment(event, dateTimeStartEST, dateTimeEndEST);

    }
    private void validateAppointment(ActionEvent event, LocalTime start, LocalTime end) throws SQLException {
        if(checkOverlap() == true) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Your selected time conflicts with an appointment");
            alert.setContentText("Please select a different time");
            alert.showAndWait();
            return;
        } else if(start.isAfter(end) || end.equals(start)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Selected appointment start time is after or equal to end time.");
            alert.setContentText("Please select different appointment start and/or end time slot.");
            alert.showAndWait();
            return;
        } else if(start.isBefore(businessOpen)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Your selected start time is outside business hours");
            alert.setContentText("Please select a different start time");
            alert.showAndWait();
            return;
        } else if(end.isAfter(businessClose)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Your selected end time is outside business hours");
            alert.setContentText("Please select a different end time");
            alert.showAndWait();
            return;
        } else {
            Appointments appointment = MainScreen.getAppointmentToModify();
            int Appointment_ID = appointment.getAppointment_ID();
            int User_ID = Integer.parseInt(appUserID.getText());
            String Title = appTitle.getText();
            String Description = appDescription.getText();
            String Location = appLocation.getText();
            String Type = appType.getText();
            int Customer_ID = appointment.getCustomer_ID();
            AppointmentDAO.updateAppointment(Appointment_ID, Title, Description, Location, Type, startLocalDate, endLocalDate, Customer_ID,User_ID,   Customer_Name);
            fxmlPath = "/View/MainScreen.fxml";
            exitPage(event);
        }

    }
    public boolean checkOverlap() throws SQLException {
        ResultSet appointments = AppointmentDAO.getByCustomer(MainScreen.getAppointmentToModify().getCustomer_ID());
        while (appointments.next()) {
            int Appointment_ID = appointments.getInt("Appointment_ID");
            Timestamp Start = appointments.getTimestamp("Start");
            Timestamp End = appointments.getTimestamp("End");
            if(MainScreen.getAppointmentToModify().getAppointment_ID() != Appointment_ID) {
                if(startLocalDate.isAfter(Start.toLocalDateTime()) && endLocalDate.isBefore(End.toLocalDateTime())) {
                    return true;
                } else if(endLocalDate.isAfter(Start.toLocalDateTime()) && endLocalDate.isBefore(End.toLocalDateTime())) {
                    return true;
                } else if(startLocalDate.isBefore(Start.toLocalDateTime()) && endLocalDate.isAfter(End.toLocalDateTime())) {
                    return true;
                } else
                    return false;
            }
        }
        return false;
    }
}
