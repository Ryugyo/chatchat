package utoronto.cs.cscc01.database;

import utoronto.cs.cscc01.users.Client;
import java.sql.SQLException;
import java.util.ArrayList;
//import java.text.SimpleDateFormat;
//import java.util.Date;
import utoronto.cs.cscc01.security.PasswordHelpers;


public class ClientDbDriver {

  //takes in the new psw and new client object
  public static boolean newClient(Client client, String psw) {
    String encryptedPSW = PasswordHelpers.pswEncryptor(psw);
    boolean result = DatabaseDriver.insertNewUser(client.getEmail(), client.getName(), 
        encryptedPSW, client.getOccupation(), client.getAge());
    return result;
  }
  
  public static boolean setPassword(Client client, String psw) throws SQLException {
    String actualPswd = DatabaseDriver.getPassword(client.getEmail());
    boolean result = PasswordHelpers.comparePassword(actualPswd, psw);
    return result;
  }
  
  public static boolean addFeedback(Client client, Feedback feedback) {
    //Date currDate = new Date();
    //SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    boolean result = DatabaseDriver.insertFeedback(client.getEmail(), client.getName(), feedback.getFeedback(), feedback.getTime());
    return result;
  }
  
  public static void saveHistory(String name, String email, SearchHistory history, boolean chatchat) {
    //the input is from chatchat
    if (chatchat) {
      DatabaseDriver.insertChatHistory(history.getContent(), "ChatChat", email, history.getFormattedDate());
    } else {
      DatabaseDriver.insertChatHistory(history.getContent(), name, email, history.getFormattedDate());
    }
  }
  
  public static ArrayList<SearchHistory> getSearchHistory(String email) {
    ArrayList<SearchHistory> results = new ArrayList<SearchHistory> ();
    try {
      ArrayList<ArrayList<String>> searchHistory = DatabaseDriver.getChatHistory(email);
      for (ArrayList<String> row:searchHistory) {
        SearchHistory temp = new SearchHistory(row.get(0), row.get(1), row.get(2), row.get(3));
        results.add(temp);
      }
    } catch(SQLException e) {
      
    }
    return results;
  }



  public static boolean checkClientExist(String email) {
    boolean exists = false;
    if (email != null) {
      try {
        ArrayList<ArrayList<String>> result = DatabaseDriver.getUser(email);
        if(!result.isEmpty()) {
          exists = true;
        }
      } catch (SQLException e) {
        //TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    return exists;
  }
    

}
