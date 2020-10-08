package utoronto.cs.cscc01.users;

import java.sql.SQLException;

import utoronto.cs.cscc01.database.AuthenticateUser;
import utoronto.cs.cscc01.database.ClientDbDriver;

public class Client {
  
  String occupation;
  String name;
  int age;
  String email;
  
  public Client(String name, int age, String email, String occupation, boolean Authenticate) {
    this.name = name;
    this.age = age;
    this.email = email;
    this.occupation = occupation;
  }
  
  //whether the Client is authenticate or not
  public boolean isAuthenticate(String psw) {
    return AuthenticateUser.loginClient(this.email, psw);
  }

  //method for client to reset their password
  public boolean setPassword(String psw) throws SQLException {
    return ClientDbDriver.setPassword(this, psw);
  }  

  public String getOccupation() {
    return occupation;
  }

  public void setOccupation(String occupation) {
    this.occupation = occupation;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

}
