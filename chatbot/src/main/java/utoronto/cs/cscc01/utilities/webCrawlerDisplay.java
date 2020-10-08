package utoronto.cs.cscc01.utilities;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class webCrawlerDisplay {

  public static ArrayList<Filecontent> getAllResult() {
    ArrayList<Filecontent> result = new ArrayList<Filecontent> ();
    ArrayList<String> fn = DirectoryFilesDisplay.listOfFiles("./Crawler");
    for(String file:fn) {
      try {
        if(!file.contains("segments") & !file.contains("_") & !file.contains("write.lock") & !file.contains("Prime")) {
          File f=new File("./Crawler/" + file);
          FileReader fr = new FileReader(f);
          BufferedReader br = new BufferedReader(fr);
          String temp = br.readLine();
          //System.out.println(file);
          String[] c = temp.split(" ");
          String content = "";
          int i = 0;
          while(c[i] != null && i < 20) {
            content = content + " " + c[i];
            i += 1;
          }
          String filename = file.replace(".txt", "");
          Filecontent fc = new Filecontent(filename, content);
          result.add(fc);
         } else {continue;}
        }catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    return result;
  }
}
