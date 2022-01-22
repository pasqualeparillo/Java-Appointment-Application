package Controller;

import DAO.AppointmentDAO;
import Model.Appointments;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TypeReport implements Initializable {
    public String fxmlPath;
    @FXML private TableView<Appointments> typeTable;
    @FXML private TableColumn<Appointments, Integer> countColumn;
    @FXML private TableColumn<Appointments, String> typeColumn;
    @FXML private ToggleGroup monthToggle;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeMonth();
    }
    @FXML
    public void changeMonth() {
        try {
            typeTable.getItems().clear();
            RadioButton selectedRadioButton = (RadioButton) monthToggle.getSelectedToggle();
            String monthToggleText = selectedRadioButton.getText();
            switch (monthToggleText) {
                case "January":
                    typeTable.refresh();
                    typeTable.setItems(AppointmentDAO.getAppointmentByType(1));
                    countColumn.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
                    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
                    break;
                case "February":
                    typeTable.refresh();
                    typeTable.setItems(AppointmentDAO.getAppointmentByType(2));
                    countColumn.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
                    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
                    break;
                case "March":
                    typeTable.refresh();
                    typeTable.setItems(AppointmentDAO.getAppointmentByType(3));
                    countColumn.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
                    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
                    break;
                case "April":
                    typeTable.refresh();
                    typeTable.setItems(AppointmentDAO.getAppointmentByType(4));
                    countColumn.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
                    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
                    break;
                case "May":
                    typeTable.refresh();
                    typeTable.setItems(AppointmentDAO.getAppointmentByType(5));
                    countColumn.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
                    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
                    break;
                case "June":
                    typeTable.refresh();
                    typeTable.setItems(AppointmentDAO.getAppointmentByType(6));
                    countColumn.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
                    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
                    break;
                case "July":
                    typeTable.refresh();
                    typeTable.setItems(AppointmentDAO.getAppointmentByType(7));
                    countColumn.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
                    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
                    break;
                case "August":
                    typeTable.refresh();
                    typeTable.setItems(AppointmentDAO.getAppointmentByType(8));
                    countColumn.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
                    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
                    break;
                case "September":
                    typeTable.refresh();
                    typeTable.setItems(AppointmentDAO.getAppointmentByType(9));
                    countColumn.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
                    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
                    break;
                case "October":
                    typeTable.refresh();
                    typeTable.setItems(AppointmentDAO.getAppointmentByType(10));
                    countColumn.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
                    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
                    break;
                case "November":
                    typeTable.refresh();
                    typeTable.setItems(AppointmentDAO.getAppointmentByType(11));
                    countColumn.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
                    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
                    break;
                case "December":
                    typeTable.refresh();
                    typeTable.setItems(AppointmentDAO.getAppointmentByType(12));
                    countColumn.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
                    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
                    break;
            }
       ;

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
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
