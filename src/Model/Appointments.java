package Model;

import java.sql.Timestamp;

/**
 * Appointment Class
 */
public class Appointments {
    private int Appointment_ID;
    private String Title;
    private String Description;
    private String Location;
    private String Type;
    private Timestamp Start;
    private Timestamp End;
    private int Customer_ID;
    private int User_ID;
    private int Contact_ID;

    /**
     * Appointment Contructor
     */
    public Appointments(int appointment_ID, String title, String description, String location, String type, Timestamp start, Timestamp end, int customer_ID, int user_ID, int contact_ID) {
        Appointment_ID = appointment_ID;
        Title = title;
        Description = description;
        Location = location;
        Type = type;
        Start = start;
        End = end;
        Customer_ID = customer_ID;
        User_ID = user_ID;
        Contact_ID = contact_ID;
    }
    /**
     * Appointment id getter
     */
    public int getAppointment_ID() {
        return Appointment_ID;
    }
    /**
     * Appointment setter
     * @param appointment_ID
     */
    public void setAppointment_ID(int appointment_ID) {
        Appointment_ID = appointment_ID;
    }
    /**
     * title getter
     */
    public String getTitle() {
        return Title;
    }
    /**
     * title setter
     * @param title
     */
    public void setTitle(String title) {
        Title = title;
    }
    /**
     * description getter
     */
    public String getDescription() {
        return Description;
    }
    /**
     * description setter
     * @param description
     */
    public void setDescription(String description) {
        Description = description;
    }
    /**
     * location getter
     */
    public String getLocation() {
        return Location;
    }
    /**
     * location setter
     * @param location
     */
    public void setLocation(String location) {
        Location = location;
    }
    /**
     * type getter
     */
    public String getType() {
        return Type;
    }
    /**
     * type setter
     * @param type
     */
    public void setType(String type) {
        Type = type;
    }
    /**
     * timestamp start getter
     */
    public Timestamp getStart() {
        return Start;
    }
    /**
     * timestamp start setter
     * @param start
     */
    public void setStart(Timestamp start) {
        Start = start;
    }
    /**
     * timestamp end setter
     */
    public Timestamp getEnd() {
        return End;
    }
    /**
     * timestamp end getter
     * @param end
     */
    public void setEnd(Timestamp end) {
        End = end;
    }
    /**
     * customer_id getter
     */
    public int getCustomer_ID() {
        return Customer_ID;
    }
    /**
     * customer_id setter
     * @param customer_ID
     */
    public void setCustomer_ID(int customer_ID) {
        Customer_ID = customer_ID;
    }
    /**
     * customer_id getter
     */
    public int getUser_ID() {
        return User_ID;
    }
    /**
     * user_id setter
     * @param user_ID
     */
    public void setUser_ID(int user_ID) {
        User_ID = user_ID;
    }
    /**
     * contact_id getter
     */
    public int getContact_ID() {
        return Contact_ID;
    }
    /**
     * contact_id setter
     * @param contact_ID
     */
    public void setContact_ID(int contact_ID) {
        Contact_ID = contact_ID;
    }

}
