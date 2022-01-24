package Utility;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * LoginLogger process's failed login attempts
 */
public class LoginLogger {
    public static String file_name = "logins.txt";
    public static void logUser(String User_Name, String Password) throws IOException {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        FileWriter writer = new FileWriter(file_name, true);
        PrintWriter writeFile = new PrintWriter(writer);
        writeFile.append( String.format("Username: %s has failed to login with password %s at %s", User_Name, Password, formatter.format(date)));
        writeFile.close();
    }
}
