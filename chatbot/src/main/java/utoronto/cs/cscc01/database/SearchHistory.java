package utoronto.cs.cscc01.database;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime; // Import the LocalDateTime class
import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter class
import java.util.Date;

public class SearchHistory {
    //public LocalDateTime myDateObj = LocalDateTime.now(); 
    //public DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
    
    private String email;
    private String content;
    private String formattedDate;
    private String name;
    
    //initialize search history with this when user save the history
    public SearchHistory(String email, String name, String content) {
      this.email = email;
      this.content = content;
      this.name = name;
      Date curr = new Date();
      SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
      this.formattedDate = formatter.format(curr);
    }
    
    public SearchHistory(String email, String name, String content, String time) {
        this.email = email;
        this.content = content;
        this.formattedDate = time;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
}
