package Model;

/**
 * Customers class
 */
public class Customers {
    private int Customer_ID;
    private String Customer_Name;
    private String Address;
    private String Postal_Code;
    private String Phone;
    private int Division_ID;
    /**
     * Customers constructor
     */
    public Customers(int customer_ID, String customer_Name, String address, String postal_Code, String phone, int division_ID) {
        Customer_ID = customer_ID;
        Customer_Name = customer_Name;
        Address = address;
        Postal_Code = postal_Code;
        Phone = phone;
        Division_ID = division_ID;
    }

    /**
     * Customers ID getter
     */
    public int getCustomer_ID() {
        return Customer_ID;
    }
    /**
     * Customers ID setter
     * @param customer_ID
     */
    public void setCustomer_ID(int customer_ID) {
        Customer_ID = customer_ID;
    }

    /**
     * Customers name getter
     */
    public String getCustomer_Name() {
        return Customer_Name;
    }

    /**
     * Customers name setter
     * @param customer_Name
     */
    public void setCustomer_Name(String customer_Name) {
        Customer_Name = customer_Name;
    }

    /**
     * Customers address getter
     */
    public String getAddress() {
        return Address;
    }

    /**
     * Customers address setter
     * @param address
     */
    public void setAddress(String address) {
        Address = address;
    }

    /**
     * Customers postal code getter
     */
    public String getPostal_Code() {
        return Postal_Code;
    }

    /**
     * Customers postal code setter
     * @param postal_Code
     */
    public void setPostal_Code(String postal_Code) {
        Postal_Code = postal_Code;
    }
    /**
     * Customers phone getter
     */
    public String getPhone() {
        return Phone;
    }

    /**
     * Customers phone setter
     * @param phone
     */
    public void setPhone(String phone) {
        Phone = phone;
    }

    /**
     * Customers division_id getter
     */
    public int getDivision_ID() {
        return Division_ID;
    }
    /**
     * Customers division_id setter
     * @param division_ID
     */
    public void setDivision_ID(int division_ID) {
        Division_ID = division_ID;
    }
}
