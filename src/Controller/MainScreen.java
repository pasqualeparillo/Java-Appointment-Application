package Controller;

import DAO.AppointmentDAO;
import DAO.CustomersDAO;
import Model.Appointments;
import Model.Customers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainScreen implements Initializable {

    private static Customers customerToModify;
    private static Appointments appointmentToModify;
    private static String fxmlPath;

    @FXML private TableView<Appointments> appointmentsTable;
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

    @FXML private TableView<Customers> customersTable;
    @FXML private TableColumn<Customers, Integer> Customer_ID;
    @FXML private TableColumn<Customers, String> Customer_Name;
    @FXML private TableColumn<Customers, String> Customer_Postal_Code;
    @FXML private TableColumn<Customers, String> Customer_Address;
    @FXML private TableColumn<Customers, String> Customer_Phone_Number;
    @FXML private TableColumn<Customers, Integer> Customer_Division_ID;
    /**
     * Set appointment table values
     * */
    private void setAppointmentTable() throws SQLException {
        appointmentsTable.setItems(AppointmentDAO.getAllAppointments());
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
     * Set customer table values
     * */
    private void setCustomersTable() {
        CustomersDAO.getCustomers();
        customersTable.setItems(CustomersDAO.getAllCustomers());
        Customer_ID.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        Customer_Name.setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));
        Customer_Postal_Code.setCellValueFactory(new PropertyValueFactory<>("Postal_Code"));
        Customer_Address.setCellValueFactory(new PropertyValueFactory<>("Address"));
        Customer_Phone_Number.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        Customer_Division_ID.setCellValueFactory(new PropertyValueFactory<>("Division_ID"));
    }
    /**
     * alerts if none selected, switches scene to modify appointment screen if one is selected.
     * @param actionEvent
     * */
    public void modifyAppointment(ActionEvent actionEvent) throws IOException {
        Appointments selectedItem = appointmentsTable.getSelectionModel().getSelectedItem();
        appointmentToModify = selectedItem;
        if(selectedItem != null) {
            fxmlPath = "/View/ModifyAppointment.fxml";
            switchScene(actionEvent, "Modify Product");
        } else {
            Alert alertConfirm = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
            alertConfirm.setTitle("No appointment selected");
            alertConfirm.setContentText("Please select an appointment");
            alertConfirm.showAndWait();
        }
    }
    /**
     * Deletes a selected appointment
     */
    public void deleteAppointment() throws SQLException {
        Appointments selectedItem = appointmentsTable.getSelectionModel().getSelectedItem();
        appointmentToModify = selectedItem;
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
        if(selectedItem != null) {
            alertConfirm.setTitle("Are you sure you would like to delete this ");
            alertConfirm.setContentText("Please confirm");
            Optional<ButtonType> result = alertConfirm.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                AppointmentDAO.removeAppointment();
            }
        } else {
            alertConfirm.setTitle("Either you did not select an appointment or none available");
            alertConfirm.setContentText("Please confirm");
            alertConfirm.showAndWait();
        }
    }
    /**
     * Deletes a selected appointment
     */
    public void deleteCustomer() throws SQLException {
        Customers selectedItem = customersTable.getSelectionModel().getSelectedItem();
        customerToModify = selectedItem;
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
        if(selectedItem != null) {
            ResultSet customerAppointments = AppointmentDAO.getByCustomer(selectedItem.getCustomer_ID());
            if(!customerAppointments.next() && selectedItem != null) {
                alertConfirm.setTitle("Are you sure you would like to delete this ");
                alertConfirm.setContentText("Please confirm");
                Optional<ButtonType> result = alertConfirm.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK) {
                    CustomersDAO.removeCustomer();
                }
            } else {
                alertConfirm.setTitle("Cannot Delete");
                alertConfirm.setContentText("The customer currently has an appointment");
                alertConfirm.showAndWait();
            }
        } else {
            alertConfirm.setTitle("Cannot Delete");
            alertConfirm.setContentText("You did not select an appointment");
            alertConfirm.showAndWait();
        }
    }
    /**
     * returns appointment to modify, used in other scenes
     * */
    public static Appointments getAppointmentToModify() {
        return appointmentToModify;
    }

    /**
     * alerts if none selected, switches scene to modify customer screen if one is selected.
     * @param actionEvent
     * */
    public void modifyCustomer(ActionEvent actionEvent) throws IOException {
        Customers selectedItem = customersTable.getSelectionModel().getSelectedItem();
        if(selectedItem != null) {
            customerToModify = selectedItem;
            fxmlPath = "/View/ModifyCustomer.fxml";
            switchScene(actionEvent, "Modify Customer");
        } else {
            Alert alertConfirm = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
            alertConfirm.setTitle("No customer selected");
            alertConfirm.setContentText("Please select an customer");
            alertConfirm.showAndWait();
        }
    }

    /**
     *
     * @param actionEvent
     * @throws IOException
     */
    public void viewReports(ActionEvent actionEvent) {
        fxmlPath = "/View/ReportScreen.fxml";
        switchScene(actionEvent, "View Reports");
    }

    /**
     * @param actionEvent
     */
    public void viewTypeReport(ActionEvent actionEvent) {
        fxmlPath = "/View/TypeReport.fxml";
        switchScene(actionEvent, "View Reports");
    }
    /**
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void addAppointment(ActionEvent actionEvent) {
        fxmlPath = "/View/AddAppointment.fxml";
        switchScene(actionEvent, "Add Appointment");
    }
    /**
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void addCustomer(ActionEvent actionEvent) {
        fxmlPath = "/View/AddCustomer.fxml";
        switchScene(actionEvent, "Add Customer");
    }
    /**
     * returns customer to modify, used in other scenes
     * */
    public static Customers getCustomerToModify() {
        return customerToModify;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setAppointmentTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setCustomersTable();
    }

    /**
     * Helper function to switch scenes
     * @param title
     * @param event
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
    @FXML
    public void exitProgram() {
        System.exit(0);
    }
}
