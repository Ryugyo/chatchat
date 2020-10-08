package utoronto.cs.cscc01.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseDriver {
  /**
   * This will connect to existing database, or create it if it's not there.
   * 
   * @return the database connection.
   */
  public static Connection connectOrCreateDataBase() {
    Connection connection = null;
    try {
      Class.forName("org.sqlite.JDBC");
      connection = DriverManager.getConnection("jdbc:sqlite:usermgmt.db");

    } catch (Exception e) {
      System.out.println("Something went wrong with your connection! see below details: ");
      e.printStackTrace();
    }

    return connection;
  }

  /**
   * This will initialize the database, or throw a ConnectionFailedException.
   * 
   * @param connection the database you'd like to write the tables to.
   * @return the connection you passed in, to allow you to continue.
   * @throws ConnectionFailedException If the tables couldn't be initialized, throw
   */
  public static Connection initialize(Connection connection) throws SQLException {
    if (!initializeDatabase(connection)) {
      // throw new SQLException();
    }
    return connection;
  }

  //clear the database and renew the connection
  protected static Connection reInitialize() throws SQLException {
    if (clearDatabase()) {
      Connection connection = connectOrCreateDataBase();
      return initialize(connection);
    } else {
      throw new SQLException();
    }
  }


  protected static boolean initializeDatabase(Connection connection) {
    Statement statement = null;

    if(!haveTables()) {
    try {
      statement = connection.createStatement();

      String sql =
          "CREATE TABLE USERS" + "(EMAIL TEXT PRIMARY KEY NOT NULL," + "NAME TEXT NOT NULL," + "PASSWORD CHAR(64) NOT NULL,"
              + "OCCUPATION TEXT NOT NULL," + "AGE INTERGER NOT NULL)";
      statement.executeUpdate(sql);

      sql = "CREATE TABLE FEEDBACK" + "(EMAIL TEXT NOT NULL," + "NAME VARCHAR(30) NOT NULL," + "MESSAGE TEXT NOT NULL," + "TIME DATETIME NOT NULL," + "PRIMARY KEY(EMAIL, TIME))";
      statement.executeUpdate(sql);


      sql = "CREATE TABLE CHATHISTORY" + "(EMAIL TEXT NOT NULL," + "NAME TEXT NOT NULL," + "MESSAGE TEXT NOT NULL," + "TIME DATETIME NOT NULL,"
              + "PRIMARY KEY(EMAIL, TIME, NAME))";
      statement.executeUpdate(sql);
      System.out.println("done");

      statement.close();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    }
    return false;
  }

  protected static boolean clearDatabase() {
    Path path = Paths.get("usermgmt.db");
    try {
      Files.deleteIfExists(path);
      return true;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }
  
/////////////////////////////////////////////SELECT METHOD///////////////////////////////////////////////////////////

  protected static ArrayList<ArrayList<String>> getUser(String email) throws SQLException {
    String sql = "SELECT NAME, EMAIL, OCCUPATION, AGE FROM USERS WHERE EMAIL = ?";
    Connection connection = DatabaseDriver.connectOrCreateDataBase();
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setString(1, email);
    ResultSet results = preparedStatement.executeQuery();
    ArrayList<ArrayList<String>> userinfo = rsToList(results);
    results.close();
    connection.close();
    return userinfo;
  }
  
  protected static ArrayList<ArrayList<String>> getFeedBack() throws SQLException {
    String sql = "SELECT * FROM FEEDBACK";
    Connection connection = DatabaseDriver.connectOrCreateDataBase();
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    ResultSet results = preparedStatement.executeQuery();
    ArrayList<ArrayList<String>> getfeedback = rsToList(results);
    results.close();
    connection.close();
    return getfeedback;
  }
  
  protected static ArrayList<ArrayList<String>> getChatHistory(String email) throws SQLException {
    String sql = "SELECT * FROM CHATHISTORY WHERE EMAIL = ?";
    Connection connection = DatabaseDriver.connectOrCreateDataBase();
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setString(1, email);
    ResultSet results = preparedStatement.executeQuery();
    ArrayList<ArrayList<String>> getHistory = rsToList(results);

    results.close();
    connection.close();
    return getHistory;
  }
  
  protected static String getPassword(String email) throws SQLException {
    String sql = "SELECT PASSWORD FROM USERS WHERE EMAIL = ?";
    Connection connection = DatabaseDriver.connectOrCreateDataBase();
    PreparedStatement preparedStatemet = connection.prepareStatement(sql);
    preparedStatemet.setString(1, email);
    ResultSet results = preparedStatemet.executeQuery();
    String password = results.getString(1);
    results.close();
    connection.close();
    return password;
  }
  
/////////////////////////////////////////////UPDATE METHOD///////////////////////////////////////////////////////////

  protected static boolean updateUserProfile(ArrayList<String> columns, ArrayList<String> values, String email) {
    String sql1 = "UPDATE USER SET ";
    String sql2 = " WHERE EMAIL = ?";
    int index = 0;
    for(index = 0; index < columns.size()-1; index++) {
      sql1 = sql1.concat(columns.get(index) + " = '" + values.get(index) + "',");
    }
    sql1 = sql1.concat(columns.get(index) + " = '" + values.get(index) + "'");
    sql1 = sql1.concat(sql2);
    try {
      Connection connection = DatabaseDriver.connectOrCreateDataBase();
      PreparedStatement preparedStatement = connection.prepareStatement(sql1);
      preparedStatement.setString(1, email);
      preparedStatement.executeUpdate();
      connection.close();
    }catch(SQLException e) {
      e.printStackTrace();
    }
    return true;
  }

///////////////////////////////////////////INSERT METHOD////////////////////////////////////////////////////////////
  protected static boolean insertNewUser(String email, String name, String password, String occupation, int age) {
    String sql = "INSERT INTO USERS(EMAIL, NAME, PASSWORD, OCCUPATION, AGE) VALUES(?,?,?,?,?)";
    try {
      Connection connection = DatabaseDriver.connectOrCreateDataBase();
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, email);
      preparedStatement.setString(2, name);
      preparedStatement.setString(3, password);
      preparedStatement.setString(4, occupation);
      preparedStatement.setInt(5, age);
      preparedStatement.executeUpdate();
      connection.close();
    }catch(SQLException e) {
      e.printStackTrace();
    }
    return true;
  }

  protected static boolean insertChatHistory(String message, String name, String email, String time) {
    String sql = "INSERT INTO CHATHISTORY(MESSAGE, NAME, EMAIL, TIME) VALUES(?,?,?,?)";
    try {
      Connection connection = DatabaseDriver.connectOrCreateDataBase();
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, message);
      preparedStatement.setString(2, name);
      preparedStatement.setString(3, email);
      preparedStatement.setString(4, time);
      preparedStatement.executeUpdate();
      connection.close();
      } catch(SQLException e) {
        e.printStackTrace();
      }
    return true;
  }

  protected static boolean insertFeedback(String message, String name, String email, String time) {
    String sql = "INSERT INTO FEEDBACK(EMAIL, NAME, MESSAGE, TIME) VALUES(?,?,?,?)";
    try {
      Connection connection = DatabaseDriver.connectOrCreateDataBase();
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, message);
      preparedStatement.setString(2, name);
      preparedStatement.setString(3, email);
      preparedStatement.setString(4, time);
      preparedStatement.executeUpdate();
      connection.close();
    } catch(SQLException e) {
      e.printStackTrace();
    }
    return true;
  }

///////////////////////////////////////////DELETE METHOD////////////////////////////////////////////////////////////
  
  // I don't know whether or not we should include this method but I have it just in case
  protected static boolean deleateFeedback(String email) {
    String sql = "DELETE FROM FEEDBACK WHERE EMAIL = ?";
    try {
      Connection connection = DatabaseDriver.connectOrCreateDataBase();
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, email);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return true;
  }

  protected static boolean deleateChatHistory(String email) {
    String sql = "DELETE FROM CHATHISTORY WHERE EMAIL = ?";
    try {
      Connection connection = DatabaseDriver.connectOrCreateDataBase();
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, email);
      preparedStatement.executeUpdate();
     } catch (SQLException e) {
       e.printStackTrace();
     }
    return true;
  }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  private static ArrayList<ArrayList<String>> rsToList(ResultSet rs) throws SQLException{
    ResultSetMetaData md = rs.getMetaData();
    int columns = md.getColumnCount();
    ArrayList<ArrayList<String>> result_list = new ArrayList<ArrayList<String>>();
    //int i = 0;
    try{
        while(rs.next()){
          ArrayList<String> row = new ArrayList<String>();
          for(int i = 1 ; i <= columns ; i++) {
            row.add(rs.getString(i));
          }
          result_list.add(row);
        }
    }catch (SQLException e) {
        System.err.println("Exception triggered during rsToList");
        e.printStackTrace();
    }
    return result_list;
}
  
  private static boolean haveTables() {
    String sql = "SELECT name FROM sqlite_master WHERE type ='table' AND name NOT LIKE 'sqlite_%'";
    boolean result = false;
    try {
      Connection connection = DatabaseDriver.connectOrCreateDataBase();
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      ResultSet results = preparedStatement.executeQuery();
      for(ArrayList<String> row:rsToList(results)) {
        if(row.get(0).equals("USERS") | row.get(0).equals("FEEDBACK") | row.get(0).equals("CHATHISTORY")) {result=true;} 
        else {result=false;}
      }
     } catch (SQLException e) {
       e.printStackTrace();
     }
    return result;
  }
}
