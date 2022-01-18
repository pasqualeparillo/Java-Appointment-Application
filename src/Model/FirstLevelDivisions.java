package Model;

/**
 * FirstLevelDivisions Class
 */
public class FirstLevelDivisions {
    private int Division_ID;
    private String Division;
    private int Country_ID;

    /**
     * FirstLevelDivisions constructor
     */
    public FirstLevelDivisions(int division_ID, String division, int country_ID) {
        Division_ID = division_ID;
        Division = division;
        Country_ID = country_ID;
    }

    /**
     * Division_ID getter
     */
    public int getDivision_ID() {
        return Division_ID;
    }

    /**
     * Division_ID setter
     * @param division_ID
     */
    public void setDivision_ID(int division_ID) {
        Division_ID = division_ID;
    }

    /**
     * Division getter
     */
    public String getDivision() {
        return Division;
    }
    /**
     * Division setter
     * @param division
     */
    public void setDivision(String division) {
        Division = division;
    }
    /**
     * country_id getter
     */
    public int getCountry_ID() {
        return Country_ID;
    }
    /**
     * country_id getter
     * @param country_ID
     */
    public void setCountry_ID(int country_ID) {
        Country_ID = country_ID;
    }

}
