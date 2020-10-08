package utoronto.cs.cscc01.utilities;

import java.io.File;
import java.util.ArrayList;

public class DirectoryFilesDisplay {

  /**
   * Get the list of files from the Uploaded files folder.
   * @param path - the path to the folder.
   * @return the list of files in the folder.
   */
  public static ArrayList<String> listOfFiles(String path) {
    ArrayList<String> result = new ArrayList<String>();
    File folder = new File(path);
    File[] listOfFiles = folder.listFiles();
    for (int i = 0; i < listOfFiles.length; i++) {
      if(listOfFiles[i].getName().contains(".doc") | listOfFiles[i].getName().contains(".pdf") | listOfFiles[i].getName().contains(".txt")) {
        result.add(listOfFiles[i].getName());
      }
    }
    return result;
  }
}
  

