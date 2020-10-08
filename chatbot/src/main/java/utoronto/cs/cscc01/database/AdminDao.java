package utoronto.cs.cscc01.database;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import utoronto.cs.cscc01.crawler.WebCrawlingDFI;
import utoronto.cs.cscc01.fileutilities.FileSwitchBox;
import utoronto.cs.cscc01.users.Admin;

public class AdminDao {

  public static ArrayList<Admin> adminList = new ArrayList<>();

  public AdminDao() {
    //get password
    adminList.add(new Admin("Hina", 18, "hina@chatchat.com", "rjoNwedWIkso236URmbJGA=="));
    adminList.add(new Admin("Stacey", 20, "stacey@chatchat.com", "EKUHsi8FE1hQ7N+L57z0fA=="));
    adminList.add(new Admin("Saskia", 12, "saskia@chatchat.com", "RQDjXMKZpcL7tRb1Y7N16w=="));
    adminList.add(new Admin("Jason", 21, "jason@chatchat.com", "r3r3x4VmXHnqFInUfzeEYA=="));
  }

  public static Admin getAdmin(String email) {
    Admin admin = null;
    for (Admin a : adminList) {
      if (a.getEmail().equals(email)) {
        admin = a;
      }
    }
    return admin;
  }
  
  public static ArrayList<Feedback> viewAllFeedback() {
    ArrayList<Feedback> result = new ArrayList<Feedback> ();
    try {
      ArrayList<ArrayList<String>> feedback = DatabaseDriver.getFeedBack();
      for(ArrayList<String> fb:feedback) {
        Feedback temp = new Feedback(fb.get(0), fb.get(1),  fb.get(2), fb.get(3));
        result.add(temp);
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return result;
  }
  
  //double check with saskia about how lucene take care of new corpus file
  public static boolean uploadCorpusFile (String fn) throws IOException {
    return FileSwitchBox.fileSwitch(fn);
  }

  public static boolean crawlingDFIPage() {
    if(WebCrawlingDFI.crawler()) {return true;}
    return false;
  }
}
