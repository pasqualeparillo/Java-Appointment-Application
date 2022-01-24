package Controller;

import DAO.AppointmentDAO;
import DAO.ContactsDAO;
import DAO.CustomersDAO;
import Database.JDBC;
import Model.Appointments;
import Model.Contacts;
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

/**
 * ModifyAppointment controller
 */
public class ModifyAppointment implements Initializable {
    private static String fxmlPath;
    // set time variables
    private final LocalTime businessOpen = LocalTime.of(8, 0);
    private final LocalTime businessClose = LocalTime.of(22, 0);
    private LocalDateTime startLocalDate;
    private LocalDateTime endLocalDate;

    @FXML private TextField appID, appCustomerID, appTitle, appDescription, appLocation, appUserID, appType, appUserName;
    @FXML public ComboBox<LocalTime> appointmentStartTime;
    @FXML public ComboBox<LocalTime> appointmentEndTime;
    @FXML public ComboBox<String> contactList;
    @FXML public DatePicker appointmentDate;

    /**
     * Exits page & returns to main page.
     * @param event
     */
    @FXML
    private void exitPage(ActionEvent event) {
        fxmlPath = "/View/MainScreen.fxml";
        switchScene(event, "Appointment Management");
    }

    /**
     * Scene switching helper function
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

    /**
     * Sets current form as previously selected appointment, process's time and sets combo boxes for business available times.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CustomersDAO.getAllCustomers();
        ContactsDAO.getAllContacts();
        String customer_Name = "";
        for(Customers customer: CustomersDAO.getAllCustomers()) {
            if(MainScreen.getAppointmentToModify().getCustomer_ID() == customer.getCustomer_ID()) {
                customer_Name = customer.getCustomer_Name();
            }
        }
        for(Contacts c: ContactsDAO.getAllContacts()) {
            if(MainScreen.getAppointmentToModify().getContact_ID() == c.getContact_ID()) {
                String contact_ID = Integer.toString(c.getContact_ID());
                c.getContact_Name();
                contactList.setValue(contact_ID);
            }
            contactList.getItems().add(Integer.toString(c.getContact_ID()));
        }
        //NEED TO SET appUserName to customer name of appointment
        appUserName.setText(customer_Name);
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

            LocalDateTime dateStart = MainScreen.getAppointmentToModify().getStart().toLocalDateTime();
            LocalDateTime dateEnd = MainScreen.getAppointmentToModify().getEnd().toLocalDateTime();
            LocalDate date = dateStart.toLocalDate();
            LocalTime timeStart = dateStart.toLocalTime();
            LocalTime timeEnd = dateEnd.toLocalTime();

            //Update appointment values
            appointmentDate.setValue(date);
            appointmentStartTime.setValue(timeStart);
            appointmentEndTime.setValue(timeEnd);
    }

    /**
     * Updates appointment if form validation pass's'
     * @param event
     * @throws SQLException
     */
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

    /**
     * Checks if form fields are valid.
     * @param event
     * @param start
     * @param end
     * @throws SQLException
     */
    private void validateAppointment(ActionEvent event, LocalTime start, LocalTime end) throws SQLException {
        if(checkBlank() == false) {
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
                AppointmentDAO.updateAppointment(Appointment_ID, Title, Description, Location, Type, startLocalDate, endLocalDate, Customer_ID,User_ID, appUserName.getText(), Integer.parseInt(contactList.getValue()));
                fxmlPath = "/View/MainScreen.fxml";
                exitPage(event);
            }
        }
    }

    /**
     * checks if appointments overlap
     * @return
     * @throws SQLException
     */
    public boolean checkOverlap() throws SQLException {
        ResultSet appointments = AppointmentDAO.getByCustomer(MainScreen.getAppointmentToModify().getCustomer_ID());
        while (appointments.next()) {
            int Appointment_ID = appointments.getInt("Appointment_ID");
            Timestamp Start = appointments.getTimestamp("Start");
            Timestamp End = appointments.getTimestamp("End");
            if (MainScreen.getAppointmentToModify().getAppointment_ID() != Appointment_ID) {
                if (startLocalDate.isAfter(Start.toLocalDateTime()) && endLocalDate.isBefore(End.toLocalDateTime())) {
                    return true;
                } else if (endLocalDate.isAfter(Start.toLocalDateTime()) && endLocalDate.isBefore(End.toLocalDateTime())) {
                    return true;
                } else if (startLocalDate.isBefore(Start.toLocalDateTime()) && endLocalDate.isAfter(End.toLocalDateTime())) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * checks if form fields are blank
     * @return
     */
    public boolean checkBlank() {
        if(
                appDescription.getText().isEmpty() ||
                        appLocation.getText().isEmpty() ||
                        appTitle.getText().isEmpty() ||
                        appointmentEndTime.getValue() == null ||
                        appointmentStartTime.getValue() == null ||
                        appType.getText().isEmpty() ||
                        appointmentDate.getValue() == null


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
