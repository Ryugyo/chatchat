package utoronto.cs.cscc01.fileutilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class WordTextExtractor implements TextExtractorStrategy {

  @Override
  public void extract(String fn) throws IOException {
    //put the txt file at current directory
    // Save original out stream.
    PrintStream originalOut = System.out;
    // Save original err stream.
    PrintStream originalErr = System.err;
    
    //change the printstream to file corpus.txt
    PrintStream out = new PrintStream(new FileOutputStream("upload/corpus.txt"));
    System.setOut(out);
    //read the data from existing docx file
    XWPFDocument docx = new XWPFDocument(new FileInputStream(fn));
    
    //Extract text from docx file
    XWPFWordExtractor we = new XWPFWordExtractor(docx);
    String fileIntxt = we.getText();
    
    //split the line by new line character
    String lines[] = fileIntxt.split("\\r?\\n");
    for(String line : lines) {
      System.out.println(line);
    }
    //set the printstream back to default
    System.setOut(originalOut);
    System.setErr(originalErr);
    we.close();
  }

}