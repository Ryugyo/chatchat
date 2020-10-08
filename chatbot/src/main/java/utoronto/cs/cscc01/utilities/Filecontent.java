package utoronto.cs.cscc01.utilities;

public class Filecontent {

  String filename;
  String content;

  public Filecontent(String filename, String content) {
    this.filename = filename;
    this.content = content;
  }
  public String getFilename() {
    return filename;
  }
  public void setFilename(String filename) {
    this.filename = filename;
  }
  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }
  
}
