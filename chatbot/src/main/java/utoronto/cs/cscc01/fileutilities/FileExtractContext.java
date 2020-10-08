package utoronto.cs.cscc01.fileutilities;

import java.io.IOException;

public class FileExtractContext {

  private TextExtractorStrategy strategy;

  public FileExtractContext(TextExtractorStrategy strategy) {
    this.strategy = strategy;
  }

  public void executeExtractor(String fn) {
    try {
      strategy.extract(fn);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}
