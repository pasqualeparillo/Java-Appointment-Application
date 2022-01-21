package Controller;

import DAO.CustomersDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModifyCustomer implements Initializable {
    private static String fxmlPath;

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
        try {
            ResultSet customer = CustomersDAO.modifyCustomers();
            while (customer.next()) {
                int Customer_ID = customer.getInt("Customer_ID");
                String Customer_Name = customer.getString("Customer_Name");
                String Postal_Code = customer.getString("Postal_Code");
                String Address = customer.getString("Address");
                String Phone = customer.getString("Phone");
                int Division_ID = customer.getInt("Division_ID");
                System.out.println(Customer_Name + " " + Customer_ID + " " + Postal_Code);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
