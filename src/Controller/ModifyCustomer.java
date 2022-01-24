package Controller;

import DAO.CustomersDAO;
import DAO.DivisionsDAO;
import Model.Customers;
import Model.FirstLevelDivisions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Modify customer controller
 */
public class ModifyCustomer implements Initializable {
    private static String fxmlPath;
    @FXML public ComboBox<String> customerCity;
    @FXML public TextField customerID, customerName, customerAddress, customerPostalCode, customerZip, customerCountry, customerPhone;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DivisionsDAO.getAllDivisions();
        setCity();
        int country_ID = 0;
        for(FirstLevelDivisions Division: DivisionsDAO.divisionsList) {
            if (MainScreen.getCustomerToModify().getDivision_ID() == Division.getDivision_ID()) {
                country_ID = Division.getCountry_ID();
            }
        }
        switch (country_ID) {
            case (1):
                customerCountry.setText("US");
                break;
            case (2):
                customerCountry.setText("UK");
                break;
            case (3):
                customerCountry.setText("Canada");
                break;
        }
    }

    /**
     * sets country combo box's
     * @param event
     */
    public void setCountry(ActionEvent event) {
        int country_ID = 0;
        for(FirstLevelDivisions Division: DivisionsDAO.divisionsList) {
            if (Division.getDivision() == customerCity.getValue()) {
                country_ID = Division.getCountry_ID();
            }
        }
        switch (country_ID) {
            case (1):
                customerCountry.setText("US");
                break;
            case (2):
                customerCountry.setText("UK");
                break;
            case (3):
                customerCountry.setText("Canada");
                break;
        }
    }

    /**
     * sets city combo box's
     */
    public void setCity() {
        Customers customer = MainScreen.getCustomerToModify();
        int Customer_ID = customer.getCustomer_ID();
        String Customer_Name = customer.getCustomer_Name();
        for(FirstLevelDivisions Division: DivisionsDAO.divisionsList) {
            customerCity.getItems().add(Division.getDivision());
            if(Division.getDivision_ID() == customer.getDivision_ID()) {
                customerCity.setValue(Division.getDivision());
            }
        }
        customerID.setText(Integer.toString(Customer_ID));
        customerName.setText(Customer_Name);
        customerAddress.setText(customer.getAddress());
        customerPostalCode.setText(customer.getPostal_Code());
        customerZip.setText(customer.getPostal_Code());
        customerPhone.setText(customer.getPhone());
    }

    /**
     * Updates customer if form fields are valid
     * @param event
     * @throws SQLException
     */
    @FXML
    public void updateCustomer(ActionEvent event) throws SQLException {
        if(checkBlank() == false) {
            int customer_city = 0;
            for(FirstLevelDivisions Division: DivisionsDAO.divisionsList) {
                if(Division.getDivision() == customerCity.getValue()) {
                    customer_city = Division.getDivision_ID();
                } else {

                }
            }
            int customer_ID = Integer.parseInt(customerID.getText());
            CustomersDAO.modifyCustomers(customerName.getText(),customerAddress.getText(), customerPostalCode.getText(), customerPhone.getText(),customer_city, customer_ID );
            exitPage(event);
        }
    }
    /**
     * checks if form fields are blank
     * @return
     */
    public boolean checkBlank() {
        if(
                customerName.getText().isEmpty() ||
                        customerAddress.getText().isEmpty() ||
                        customerPostalCode.getText().isEmpty() ||
                        customerPhone.getText().isEmpty() ||
                        customerCity.getValue() == null

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
