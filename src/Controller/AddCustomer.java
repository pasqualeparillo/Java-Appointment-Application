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
import java.util.ResourceBundle;

/**
 * add customer controller
 */
public class AddCustomer implements Initializable {
    private static String fxmlPath;
    @FXML public ComboBox<String> customerCity;
    @FXML public TextField customerCountry, customerName, customerAddress, customerPostalCode, customerPhone;
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

    /**
     * sets the country combo box based on the selected city.
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
     * adds cities to city combo box
     */
    public void setCity() {
        for(FirstLevelDivisions Division: DivisionsDAO.divisionsList) {
            customerCity.getItems().add(Division.getDivision());
        }
    }

    /**
     * checks form validation and adds customer via customerdao sql command
     * @param event
     */
    public void addCustomer(ActionEvent event) {
        int cDivision = 0;
        for(int i=0; i < DivisionsDAO.divisionsList.size(); i++) {
            if(customerCity.getValue() == DivisionsDAO.divisionsList.get(i).getDivision()) {
                cDivision = DivisionsDAO.divisionsList.get(i).getDivision_ID();
            }
        }
        String cName = customerName.getText();
        String cAddress = customerAddress.getText();
        String cPostal = customerPostalCode.getText();
        String cPhone = customerPhone.getText();
        if(checkBlank() == false) {
            CustomersDAO.addCustomer(cName, cAddress, cPostal, cPhone, cDivision);
            fxmlPath = "/View/MainScreen.fxml";
            exitPage(event);
        }
    }

    /**
     * checks if form fields are blank.
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DivisionsDAO.getAllDivisions();
        setCity();
    }
}
