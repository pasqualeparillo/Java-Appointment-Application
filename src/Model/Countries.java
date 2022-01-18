package Model;

/**
 * Countries Class
 */
public class Countries {
    private int Country_ID;
    private String Country_Name;

    /**
     * Countries contructor
     */
    public Countries(int country_ID, String country_Name) {
        Country_ID = country_ID;
        Country_Name = country_Name;
    }

    /**
     * Country_id getter
     */
    public int getCountry_ID() {
        return Country_ID;
    }
    /**
     * Country_id setter
     * @param country_ID
     */
    public void setCountry_ID(int country_ID) {
        Country_ID = country_ID;
    }
    /**
     * Country_name getter
     */
    public String getCountry_Name() {
        return Country_Name;
    }
    /**
     * Country_name setter
     * @param country_Name
     */
    public void setCountry_Name(String country_Name) {
        Country_Name = country_Name;
    }
}
