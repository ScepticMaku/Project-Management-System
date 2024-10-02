package authentication;

import main.config;
import java.util.*;
import users.*;
import java.sql.*;
import password.securePassword;
import ui.project;

public class Login extends config {
    Scanner sc = new Scanner(System.in);
    Admin admin = new Admin();
    securePassword passw = new securePassword();
    
    int id;
    String user, pass, role;
    String locatedUser, locatedPass, locatedRole;
    
    public void loginCredentials(){
        
            System.out.println("\nLogin:");
            System.out.print("Enter username: ");
            user = sc.next();

            System.out.print("Enter password: ");
            pass = sc.next();
            
            locateUser(user, pass);
    }
    
    public void locateUser(String username, String password){
        try{
            PreparedStatement state = connectDB().prepareStatement("SELECT user_id, username, password_hash, role FROM user");
            ResultSet result = state.executeQuery();
            
            locatedUser = result.getString("username");
            locatedPass = result.getString("password_hash");
            locatedRole = result.getString("role");
            id = result.getInt("user_id");
            
            result.close();
            
            if(passw.passwordHashing(password).equals(locatedPass) && username.equals(locatedUser)){
                if(locatedRole.equals("admin")){
                    
                    role = "admin";
                    admin.displayInterface(id, role);
                }
            } else{
                System.out.println("Invalid Credentials.\n");
            }
        } catch(SQLException e){
            System.out.println("Error: "+e.getMessage());
        }
    }
}
