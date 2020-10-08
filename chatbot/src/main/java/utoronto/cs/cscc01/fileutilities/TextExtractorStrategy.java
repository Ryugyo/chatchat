package utoronto.cs.cscc01.fileutilities;

import java.io.IOException;

public interface TextExtractorStrategy {

  public void extract(String fn) throws IOException;

}
