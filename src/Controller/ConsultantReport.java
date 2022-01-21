package Controller;

import DAO.AppointmentDAO;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConsultantReport implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            AppointmentDAO.getAppointmentByType(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
