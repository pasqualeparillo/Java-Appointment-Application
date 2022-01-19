package DAO;

import Database.JDBC;
import Model.Appointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class AppointmentDAO {
    public static ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();

    public static ObservableList<Appointments> getAllAppointments() {
        getAppointments();
        return appointmentsList;
    }

    public static void getAppointments() {
        try {
           appointmentsList.clear();
           String sql = "SELECT * FROM Appointments";
           PreparedStatement sqlQuery = JDBC.getConnection().prepareStatement(sql);
           ResultSet sqlQueryResult = sqlQuery.executeQuery();
           while(sqlQueryResult.next()) {
               int Appointment_ID = sqlQueryResult.getInt("Appointment_ID");
               int Customer_ID = sqlQueryResult.getInt("Customer_ID");
               int User_ID = sqlQueryResult.getInt("User_ID");
               int Contact_ID = sqlQueryResult.getInt("Contact_ID");
               String Title = sqlQueryResult.getString("Title");
               String Description = sqlQueryResult.getString("Description");
               String Location = sqlQueryResult.getString("Location");
               String Type = sqlQueryResult.getString("Type");
               Timestamp Start = sqlQueryResult.getTimestamp("Start");
               Timestamp End = sqlQueryResult.getTimestamp("End");
               Appointments appointments = new Appointments(Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID);
               appointmentsList.add(appointments);
           }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
