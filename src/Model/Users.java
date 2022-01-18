package Model;

/**
 * Users class
 */
public class Users {
    private int User_ID;
    private String User_Name;
    private String Password;
    /**
     * Users Contructor
     */
    public Users(int user_ID, String user_Name, String password) {
        User_ID = user_ID;
        User_Name = user_Name;
        Password = password;
    }

    /**
     * user ID getter
     */
    public int getUser_ID() {
        return User_ID;
    }
    /**
     * user ID setter
     * @param user_ID
     */
    public void setUser_ID(int user_ID) {
        User_ID = user_ID;
    }
    /**
     * username getter
     */
    public String getUser_Name() {
        return User_Name;
    }
    /**
     * username setter
     * @param user_Name
     */
    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }
    /**
     * user password getter
     */
    public String getPassword() {
        return Password;
    }
    /**
     * user password setter
     * @param password
     */
    public void setPassword(String password) {
        Password = password;
    }
}
