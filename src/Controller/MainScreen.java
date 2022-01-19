package Controller;

import DAO.AppointmentDAO;
import DAO.CustomersDAO;
import Model.Appointments;
import Model.Customers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MainScreen implements Initializable {
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

    private void setAppointmentTable() {
        AppointmentDAO.getAppointments();
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setAppointmentTable();
        setCustomersTable();
    }
}
