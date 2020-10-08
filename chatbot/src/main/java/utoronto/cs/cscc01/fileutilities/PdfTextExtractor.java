package utoronto.cs.cscc01.fileutilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

//import org.apache.
public class PdfTextExtractor implements TextExtractorStrategy {

  @Override
  public void extract(String fn) {
    
    try {
      // Save original out stream.
      PrintStream originalOut = System.out;
      // Save original err stream.
      PrintStream originalErr = System.err;
      PrintStream out = new PrintStream(new FileOutputStream("upload/corpus.txt"));
      System.setOut(out);
      PDDocument doc = PDDocument.load(new File(fn));
      PDFTextStripper tStripper = new PDFTextStripper();
      
      String fileIntxt = tStripper.getText(doc);
      //split the pdf by white space
      String lines[] = fileIntxt.split("\\r?\\n");
      for(String line : lines) {
        System.out.println(line);
      }
      System.setOut(originalOut);
      System.setErr(originalErr);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
  }
}
