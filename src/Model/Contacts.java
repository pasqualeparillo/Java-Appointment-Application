package Model;

/**
 * Contacts Class
 */
public class Contacts {
    private int Contact_ID;
    private String Contact_Name;
    private String Email;

    /**
     * Contacts constructor
     */
    public Contacts(int contact_ID, String contact_Name, String email) {
        Contact_ID = contact_ID;
        Contact_Name = contact_Name;
        Email = email;
    }
    /**
     * Contacts id getter
     */
    public int getContact_ID() {
        return Contact_ID;
    }
    /**
     * Contacts id setter
     * @param contact_ID
     */
    public void setContact_ID(int contact_ID) {
        Contact_ID = contact_ID;
    }
    /**
     * Contacts name getter
     */
    public String getContact_Name() {
        return Contact_Name;
    }
    /**
     * Contacts name setter
     * @param contact_Name
     */
    public void setContact_Name(String contact_Name) {
        Contact_Name = contact_Name;
    }
    /**
     * Contacts email getter
     */
    public String getEmail() {
        return Email;
    }
    /**
     * Contacts name setter
     * @param email
     */
    public void setEmail(String email) {
        Email = email;
    }
}
