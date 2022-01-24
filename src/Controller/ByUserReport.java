package Controller;

import DAO.AppointmentDAO;
import DAO.ContactsDAO;
import DAO.UsersDAO;
import Model.Appointments;
import Model.Contacts;
import Model.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * ByUserReport Controller
 */
public class ByUserReport implements Initializable {
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
    @FXML private ComboBox<String> userList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       UsersDAO.getAllUsers();
        for(Users u: UsersDAO.getAllUsers()) {
            userList.setValue(u.getUser_Name());
            userList.getItems().add(u.getUser_Name());
            try {
                setAppointmentTable(u.getUser_ID());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * sets the user on combo box change
     * @param event
     * @throws SQLException
     */
    public void changeUser(ActionEvent event) throws SQLException {
        int User_ID = 0;
        String User_Name = userList.getValue();
        for(int i=0; i < UsersDAO.usersList.size(); i++) {
            if(UsersDAO.usersList.get(i).getUser_Name() == User_Name) {
                User_ID = UsersDAO.usersList.get(i).getUser_ID();
            }
        }
        setAppointmentTable(User_ID);
    }

    /**
     * sets appointment table to the selected users appointments
     * @param id
     * @throws SQLException
     */
    private void setAppointmentTable(int id) throws SQLException {
        appointmentsTable.setItems(AppointmentDAO.getByUser(id));
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
