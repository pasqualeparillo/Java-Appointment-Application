package DAO;

import Controller.MainScreen;
import Database.JDBC;
import Model.Appointments;
import Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

import static java.sql.Timestamp.valueOf;

public class AppointmentDAO {
    public static ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();
    public static ObservableList<Appointments> appointmentTypeList = FXCollections.observableArrayList();
    /**
     * returns all appointments
     */
    public static ObservableList<Appointments> getAllAppointments() throws SQLException {
        getAppointments();
        return appointmentsList;
    }
    public static ObservableList<Appointments> returnAppointmentsByWeek() throws SQLException {
        getAppointmentsByWeek();
        return appointmentsList;
    }
    public static ObservableList<Appointments> returnAppointmentsByMonth() throws SQLException {
        getAppointmentsByMonth();
        return appointmentsList;
    }

    /**
     * Gets & returns appointment by customer ID
     * @param Customer_ID
     */
    public static ResultSet getByCustomer(int Customer_ID) {
        String sql = String.format("SELECT * FROM appointments WHERE Customer_ID=%s.", Customer_ID);
        try {
            PreparedStatement sqlQuery = JDBC.getConnection().prepareStatement(sql);
            ResultSet sqlQueryResult = sqlQuery.executeQuery();
            return sqlQueryResult;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * removed selected appointment
     */
    public static ObservableList<Appointments> removeAppointment() throws SQLException {
        String sql = String.format("DELETE FROM appointments WHERE Appointment_ID=%s.", MainScreen.getAppointmentToModify().getAppointment_ID());
        try {
            PreparedStatement sqlQuery = JDBC.getConnection().prepareStatement(sql);
            sqlQuery.executeUpdate();
            return getAllAppointments();
        } catch (SQLException e) {
            e.printStackTrace();
            return getAllAppointments();
        }
    }
    public static void updateAppointment(int appointment_ID, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customer_ID, int user_ID, String customer_name) {
        try {
            String sql = String.format("UPDATE appointments set Title='%s', Description='%s', Location='%s', Type='%s', Start='%s', End='%s', Customer_ID=%s, User_ID=%s WHERE Appointment_ID=%s", title, description, location, type, start, end,
                    customer_ID, user_ID, appointment_ID);
            String customerSql = String.format("UPDATE customers set Customer_Name='%s' WHERE Customer_ID=%s", customer_name, customer_ID);
            JDBC.getConnection().prepareStatement(sql).execute();
            JDBC.getConnection().prepareStatement(customerSql).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * gets all appointments by month, set appointmentsList with result
     */
    public static void getAppointmentsByMonth() throws SQLException {
        String sql = "SELECT Appointment_ID, Title, Description, Location, contacts.Contact_ID, contacts.Contact_Name, Type, Start, End, customers.Customer_ID, User_ID " +
                "FROM appointments, contacts, customers WHERE appointments.Contact_ID=contacts.Contact_ID AND appointments.Customer_ID=customers.Customer_ID " +
                "AND month(Start) = month(now())";
        try {
            appointmentsList.clear();
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
    /**
     * gets all appointments by week, set appointmentsList with result
     */
    public static void getAppointmentsByWeek() throws SQLException {
         LocalDate now = LocalDate.now();
         LocalDate prevMonday = now.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
         LocalTime mondayMidnight = LocalTime.MIDNIGHT;
         LocalDateTime monMid = LocalDateTime.of(prevMonday, mondayMidnight);
         Timestamp timeValue = valueOf(monMid);
         String sql = String.format("SELECT Appointment_ID, Title, Description, Location, contacts.Contact_ID, contacts.Contact_Name, Type, Start, End, customers.Customer_ID, User_ID FROM appointments, contacts, customers WHERE appointments.Contact_ID=contacts.Contact_ID AND appointments.Customer_ID=customers.Customer_ID AND Start >= '%s' AND Start <= date_add('%s', interval 7 day);", timeValue, timeValue);
        try {
            appointmentsList.clear();
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
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * returns appointment by type & month
     *
     */
    public static ObservableList<Appointments> getAppointmentByType(int month) throws SQLException {
        try {
            String sql = String.format("SELECT COUNT(Appointment_ID), Type FROM appointments WHERE MONTH(Start)=%s GROUP BY Type", month);
            PreparedStatement sqlQuery = JDBC.getConnection().prepareStatement(sql);
            ResultSet sqlQueryResult = sqlQuery.executeQuery();
            while (sqlQueryResult.next()) {
                int appointment_ID = sqlQueryResult.getInt("COUNT(Appointment_ID)");
                String type = sqlQueryResult.getString("Type");
                Appointments appointment = new Appointments(appointment_ID, type);
                appointmentTypeList.add(appointment);

            }
            return appointmentTypeList;
        } catch (SQLException e) {

        }
        return null;
    }
    /**
     * gets all appointments -> adds them to appointmentsList
     */
    public static void getAppointments() throws SQLException {
        try {
            //SELECT * FROM client_schedule.appointments INNER JOIN client_schedule.customers ON client_schedule.appointments.Customer_ID=client_schedule.customers.Customer_ID;
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
