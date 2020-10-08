package utoronto.cs.cscc01.crawler;

import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CorpusReader {
  //read the text file 
  List<String> questions;
  List<String> answers;

  public void corpusCrawlingFP(String fn, List<String> questions, List<String> answers) {
    this.questions = questions;
    this.answers = answers;
    //q stands for index for questions array
    //a stands for index for answers array
    int q = 0, a = 0;
    
    //initiate BufferedReader to read the file
    BufferedReader reader;

    try {
      reader = new BufferedReader(new FileReader(fn));
      
      //check if the file start from the first question
      fileChecker(reader);
      String line = reader.readLine();
      
      //add the first question to questions
      questions.add(q, line);
      line = reader.readLine();
      
      while (line != null) {
        //if it is the question line
        if (line.trim().matches("^[1-9][0-9]*\\. .*\\?*")) {
          q += 1; a += 1;
          questions.add(q, line);
          line = reader.readLine();
        //if it is the mark for new updating
        } else if (line.trim().matches("^[A-Z][a-z]{3} [1-9][0-9]*\\, (20)[0-9][0-9]")) {
          //skip the line where it is marks for new updating
          line = reader.readLine();
        }
        //else will be seen as answer
        else {
          if (answers.size()-a == 1) {
            //when there's existing holder for the answers
            String temp = answers.get(a) + line;
            answers.set(a, temp);
          } else { //initiate a new holder
            answers.add(line);
          }
          line = reader.readLine();
        }
      }
    } catch (IOException e){
      e.printStackTrace();
    }
  }
  
  //make sure the program read the file from first question
  private void fileChecker(BufferedReader reader) throws IOException {
    String line = reader.readLine();
    //continue reading the file until it reach its first question
    while (line != null) {
      if (!line.trim().matches("^[1-9][0-9]*\\. .*\\?*")) {
        line = reader.readLine();
        break;
      }
    }
  }
}
