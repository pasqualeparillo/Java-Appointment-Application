package DAO;

import Database.JDBC;
import Model.FirstLevelDivisions;
import Model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * UsersDAO handles all SQL calls regarding Users
 */
public class UsersDAO {
    public static ObservableList<Users> usersList = FXCollections.observableArrayList();
    public static ObservableList<Users> usersLoggedInList = FXCollections.observableArrayList();

    /**
     * returns all users
     * @return
     */
    public static ObservableList<Users> getAllUsers() {
        getUsers();
        return usersList;
    }

    /**
     * gets all users
     */
    public static void getUsers() {
        try {
            usersList.clear();
            String sql = "SELECT * FROM Users";
            PreparedStatement sqlQuery = JDBC.getConnection().prepareStatement(sql);
            ResultSet sqlQueryResult = sqlQuery.executeQuery();
            while(sqlQueryResult.next()) {
                int User_ID = sqlQueryResult.getInt("User_ID");
                String User_Name = sqlQueryResult.getString("User_Name");
                String Password = sqlQueryResult.getString("Password");
                Users users = new Users(User_ID, User_Name, Password);
                usersList.add(users);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
