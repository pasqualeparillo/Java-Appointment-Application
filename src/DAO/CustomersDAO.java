package DAO;

import Controller.MainScreen;
import Database.JDBC;
import Model.Appointments;
import Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * CustomersDAO handles all SQL calls relating to Customers
 */
public class CustomersDAO {
    public static ObservableList<Customers> customersList = FXCollections.observableArrayList();

    /**
     * gets all customers
     * @return
     */
    public static ObservableList<Customers> getAllCustomers() {
        getCustomers();
        return customersList;
    }

    /**
     * Handles modifying customers
     * @param customer_name
     * @param customer_address
     * @param postal_code
     * @param phone
     * @param division_id
     * @param customer_ID
     * @throws SQLException
     */
    public static void modifyCustomers(String customer_name, String customer_address, String postal_code, String phone, int division_id, int customer_ID) throws SQLException {
        String customerSql = String.format("UPDATE customers set Customer_Name='%s', Address='%s', Postal_Code='%s', Phone='%s', Division_ID=%s WHERE Customer_ID=%s", customer_name, customer_address, postal_code, phone, division_id, customer_ID);
        try {
            PreparedStatement sqlQuery = JDBC.getConnection().prepareStatement(customerSql);
            sqlQuery.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * handles adding customres
     * @param Customer_Name
     * @param Address
     * @param Postal_Code
     * @param Phone
     * @param Division_ID
     */
    public static void addCustomer(String Customer_Name,String  Address,String  Postal_Code,String  Phone, int Division_ID) {
        try {
            String sql = String.format("INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES ('%s', '%s', '%s', '%s', %s)", Customer_Name, Address, Postal_Code, Phone, Division_ID);
            JDBC.getConnection().prepareStatement(sql).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * handles removing customers
     * @return
     * @throws SQLException
     */
    public static ObservableList<Customers> removeCustomer() throws SQLException {
        String sql = String.format("DELETE FROM customers WHERE Customer_ID=%s.", MainScreen.getCustomerToModify().getCustomer_ID());
        try {
            PreparedStatement sqlQuery = JDBC.getConnection().prepareStatement(sql);
            sqlQuery.executeUpdate();
            return getAllCustomers();
        } catch (SQLException e) {
            e.printStackTrace();
            return getAllCustomers();
        }
    }

    /**
     * handles fetching all customers
     */
    public static void getCustomers() {
        try {
            customersList.clear();
            String sql = "SELECT * FROM Customers";
            PreparedStatement sqlQuery = JDBC.getConnection().prepareStatement(sql);
            ResultSet sqlQueryResult = sqlQuery.executeQuery();
            while(sqlQueryResult.next()) {
                int Customer_ID = sqlQueryResult.getInt("Customer_ID");
                String Customer_Name = sqlQueryResult.getString("Customer_Name");
                String Postal_Code = sqlQueryResult.getString("Postal_Code");
                String Address = sqlQueryResult.getString("Address");
                String Phone = sqlQueryResult.getString("Phone");
                int Division_ID = sqlQueryResult.getInt("Division_ID");
                Customers customers = new Customers(Customer_ID, Customer_Name,Address, Postal_Code, Phone, Division_ID);
                customersList.add(customers);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
