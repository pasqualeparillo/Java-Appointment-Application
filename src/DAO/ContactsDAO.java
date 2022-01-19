package DAO;

import Database.JDBC;
import Model.Contacts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactsDAO {
    public static ObservableList<Contacts> contactsList = FXCollections.observableArrayList();

    public static ObservableList<Contacts> getAllContacts() {
        getContacts();
        return contactsList;
    }

    public static void getContacts() {
        try {
            contactsList.clear();
            String sql = "SELECT * FROM Contacts";
            PreparedStatement sqlQuery = JDBC.getConnection().prepareStatement(sql);
            ResultSet sqlQueryResult = sqlQuery.executeQuery();
            while(sqlQueryResult.next()) {
                int Contact_ID = sqlQueryResult.getInt("Appointment_ID");
                String Contact_Name = sqlQueryResult.getString("Contact_Name");
                String Email = sqlQueryResult.getString("Email");
                Contacts contacts = new Contacts(Contact_ID, Contact_Name,Email);
                contactsList.add(contacts);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
