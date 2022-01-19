package DAO;

import Database.JDBC;
import Model.Countries;
import Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountriesDAO {
    public static ObservableList<Countries> countriesList = FXCollections.observableArrayList();

    public static ObservableList<Countries> getAllCountries() {
        getCountries();
        return countriesList;
    }

    public static void getCountries() {
        try {
            countriesList.clear();
            String sql = "SELECT * FROM Countries";
            PreparedStatement sqlQuery = JDBC.getConnection().prepareStatement(sql);
            ResultSet sqlQueryResult = sqlQuery.executeQuery();
            while(sqlQueryResult.next()) {
                int Country_ID = sqlQueryResult.getInt("Country_ID");
                String Country = sqlQueryResult.getString("Country");
                Countries countries = new Countries(Country_ID, Country);
                countriesList.add(countries);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
