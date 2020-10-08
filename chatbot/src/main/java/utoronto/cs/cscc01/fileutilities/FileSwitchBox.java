package utoronto.cs.cscc01.fileutilities;

import java.io.IOException;

public class FileSwitchBox {
//the class will take the file from user
  //change it to html format to crawl
  
  public static boolean fileSwitch(String fn) throws IOException {
    //file type
    //open the file
    //String fileType = fn.split(".")[1];
    boolean result = false;
    if(fn.contains("docx")) {
      //parse the docx file is
      FileExtractContext word = new FileExtractContext(new WordTextExtractor());
      word.executeExtractor(fn);
      result = true;
    } else if (fn.contains("pdf")){
      //parse the pdf file
      FileExtractContext pdf = new FileExtractContext(new PdfTextExtractor());
      pdf.executeExtractor(fn);
      result = true;
    } else if(fn.contains("txt")) {result = true;}
    
    return result;
  }

}
