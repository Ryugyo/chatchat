package utoronto.cs.cscc01.database;

import static utoronto.cs.cscc01.database.AdminDao.*;
import java.sql.SQLException;
import java.util.ArrayList;

import utoronto.cs.cscc01.database.DatabaseDriver;
import utoronto.cs.cscc01.security.PasswordHelpers;
import utoronto.cs.cscc01.users.Admin;


public class AuthenticateUser {


  public static boolean loginClient(String email, String password) {
    boolean isAuthenticated = false;
    // if email and password are non-empty, verify credentials
    if (email != null && password != null) {
      // TODO: check that user exists and has correct password
      String actualPswd;
      try {
        actualPswd = DatabaseDriver.getPassword(email);
        if (actualPswd != null && PasswordHelpers.comparePassword(actualPswd, password)) {
          isAuthenticated = true;
        }
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        // e.printStackTrace();
        isAuthenticated = false;
      }
    }
    return isAuthenticated;
  }

  
  public static boolean loginAdmin(String email, String password) {
    boolean isAdminLoggedIn = false;
    if (email != null && password != null) {
      // check if user is in list of admins AND password matches, set to true
      Admin admin = getAdmin(email);
      if (admin != null) {
        if (admin.getEmail().equals(email)) {
          if (PasswordHelpers.comparePassword(getAdmin(email).getPassword(), password)) {
            isAdminLoggedIn = true;
          }
        }
      }
    }
    return isAdminLoggedIn;
  }
  
  // call the following functions only when the user's authenticated
  public static ArrayList<String> getUser (String email) {
	ArrayList<String> user = null;
	try {
		user = DatabaseDriver.getUser(email).get(0);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  return user;
  }
}
