package DAO;

import Database.JDBC;
import Model.Countries;
import Model.FirstLevelDivisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DivisionsDAO {
    public static ObservableList<FirstLevelDivisions> divisionsList = FXCollections.observableArrayList();

    public static ObservableList<FirstLevelDivisions> getAllDivisions() {
        getDivisions();
        return divisionsList;
    }

    public static void getDivisions() {
        try {
            divisionsList.clear();
            String sql = "SELECT * FROM first_level_divisions";
            PreparedStatement sqlQuery = JDBC.getConnection().prepareStatement(sql);
            ResultSet sqlQueryResult = sqlQuery.executeQuery();
            while(sqlQueryResult.next()) {
                int Division_ID = sqlQueryResult.getInt("Division_ID");
                String Division = sqlQueryResult.getString("Division");
                int Country_ID = sqlQueryResult.getInt("Country_ID");
                FirstLevelDivisions divisions = new FirstLevelDivisions(Division_ID, Division, Country_ID);
                divisionsList.add(divisions);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
