package utoronto.cs.cscc01.database;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Feedback {

  private String feedback;
  private String name;
  private String email;
  private String time;
  
//initialize feedback with this constructor when user save the history
  public Feedback(String email, String name, String feedback) {
    this.feedback = feedback;
    this.name = name;
    this.email = email;
    Date curr = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    this.time = formatter.format(curr);
  }

  public Feedback(String email, String name,  String feedback, String time) {
    this.feedback = feedback;
    this.name = name;
    this.email = email;
    this.time = time;
  }

  public String getFeedback() {
    return feedback;
  }

  public void setFeedback(String feedback) {
    this.feedback = feedback;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }


}