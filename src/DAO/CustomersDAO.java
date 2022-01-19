package DAO;

import Database.JDBC;
import Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomersDAO {
    public static ObservableList<Customers> customersList = FXCollections.observableArrayList();

    public static ObservableList<Customers> getAllCustomers() {
        getCustomers();
        return customersList;
    }

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
