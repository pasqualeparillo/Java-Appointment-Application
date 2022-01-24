package Controller;

import DAO.*;
import Model.Appointments;
import Model.Contacts;
import Model.Customers;
import Model.FirstLevelDivisions;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;

/**
 * AddAppointment Controller class
 */
public class AddAppointment implements Initializable {
    private static String fxmlPath;
    private static boolean overlapping;
    @FXML public ComboBox<String> customerList;
    @FXML public ComboBox<String> contactList;
    @FXML public DatePicker appointmentDate;
    @FXML public ComboBox<LocalTime> appointmentStartTime;
    @FXML public ComboBox<LocalTime> appointmentEndTime;
    @FXML public TextField appointmentTitle, appointmentDescription, appointmentType, appointmentLocation;
    private final LocalTime businessOpen = LocalTime.of(8, 0);
    private final LocalTime businessClose = LocalTime.of(22, 0);
    private LocalDateTime startLocalDate;
    private LocalDateTime endLocalDate;

    /**
     * ExitPage helper to change scenes
     * @param event
     */
    @FXML
    private void exitPage(ActionEvent event) {
        fxmlPath = "/View/MainScreen.fxml";
        switchScene(event, "Appointment Management");
    }
    /**
     * scene switching helper to change scenes
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(Customers c: CustomersDAO.getAllCustomers()) {
            String customer_ID = Integer.toString(c.getCustomer_ID());
            customerList.getItems().add(customer_ID);
        }
        for(Contacts c: ContactsDAO.getAllContacts()) {
            String contact_ID = Integer.toString(c.getContact_ID());
            contactList.getItems().add(contact_ID);
        }
        generateDateTime();
    }
    /**
     * Calculates time based on business close and open times and adds times to the combo boxes
     */
    public void generateDateTime() {
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
        while(nextAppointment.isBefore(busClose.plusMinutes(15))){
            appointmentEndTime.getItems().add(nextAppointment);
            nextAppointment = nextAppointment.plusMinutes(15);
        }
    }
    /**
     * Checks appointment is valid & adds addpointment via addappointdao sql command
     * @param event
     */
        @FXML
        private void addAppointment(ActionEvent event) throws SQLException {
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

    /**
     * Checks form validation & returns alert if anything is not filled out or overlaps.
     * @param event
     * @param start
     * @param end
     * @throws SQLException
     */
    private void validateAppointment(ActionEvent event, LocalTime start, LocalTime end) throws SQLException {
        if(checkBlank() == false) {
            checkOverlap();
            if(overlapping == true) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Your selected time conflicts with an appointment");
                alert.setContentText("Please select a different time");
                alert.showAndWait();
                return;
            } else if(start.isAfter(end) || end.equals(start)) {
                System.out.println("end");
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

                String Title = appointmentTitle.getText();
                String Description = appointmentDescription.getText();
                String Location = appointmentLocation.getText();
                String Type = appointmentType.getText();
                int Customer_ID = Integer.parseInt(customerList.getValue());
                int Contact_ID = Integer.parseInt(contactList.getValue());
                int User_ID = UsersDAO.usersLoggedInList.get(0).getUser_ID();
                AppointmentDAO.addAppointment(Title, Description, Location, Type, startLocalDate, endLocalDate, Customer_ID, User_ID, Contact_ID);
                fxmlPath = "/View/MainScreen.fxml";
                exitPage(event);
            }
        }
    }

    /**
     * checks if appointments overlap
     */
    public void checkOverlap()  {
        for (int i = 0; i < AppointmentDAO.appointmentsList.size(); i++) {
            Timestamp Start = AppointmentDAO.appointmentsList.get(i).getStart();
            Timestamp End = AppointmentDAO.appointmentsList.get(i).getEnd();
            if (startLocalDate.isAfter(Start.toLocalDateTime()) && endLocalDate.isBefore(End.toLocalDateTime())) {
                overlapping = true;
                break;
            } else if (endLocalDate.isAfter(Start.toLocalDateTime()) && endLocalDate.isBefore(End.toLocalDateTime())) {
                overlapping = true;
                break;
            } else if (startLocalDate.isBefore(Start.toLocalDateTime()) && endLocalDate.isAfter(End.toLocalDateTime())) {
                overlapping = true;
                break;
            } else {
                overlapping = false;
            }
        }
    }

    /**
     * checks if form fields are blank
     * @return
     */
    public boolean checkBlank() {
        if(
                appointmentDescription.getText().isEmpty() ||
                        appointmentLocation.getText().isEmpty() ||
                        appointmentTitle.getText().isEmpty() ||
                        appointmentEndTime.getValue() == null ||
                        appointmentStartTime.getValue() == null ||
                        appointmentType.getText().isEmpty() ||
                        appointmentDate.getValue() == null ||
                        contactList.getValue() == null ||
                        customerList.getValue() == null


        ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR!");
            alert.setHeaderText("Missing field");
            alert.setContentText("Please be sure to fill out all fields.");
            alert.showAndWait();
            return true;
        } else {
            return false;
        }
    }
}
