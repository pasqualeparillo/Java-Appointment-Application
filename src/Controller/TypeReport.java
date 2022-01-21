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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            typeTable.setItems(AppointmentDAO.getAppointmentByType(5));
            countColumn.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

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
