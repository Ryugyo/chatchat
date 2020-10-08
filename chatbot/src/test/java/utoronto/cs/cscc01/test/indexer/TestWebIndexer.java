package utoronto.cs.cscc01.test.indexer;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.Before;
import org.junit.Test;

import utoronto.cs.cscc01.indexer.WebIndexer;

public class TestWebIndexer {

  @Before
  public void runIndexer() {
    WebIndexer wi = new WebIndexer();
    try {
      wi.index();
    } catch (IOException e) {
      // if something bad happens, print the stack trace
      e.printStackTrace();
    }
  }

  @Test
  public void testParseQuery() throws ParseException {
    String result = WebIndexer.parseQuery("Tina Yang");
    assertEquals(true, result.contains("Tina Yang"));
  }

  @Test
  public void testNoResult() throws ParseException {
    String result = WebIndexer.parseQuery("ghyuew");
    assertEquals("No result", result);
  }

  @Test(expected = ParseException.class)
  public void testCorpusBlankQuery() throws ParseException {
    WebIndexer.parseQuery("");
  }

  @Test(expected = ParseException.class)
  public void testCorpusNullQuery() throws ParseException {
    WebIndexer.parseQuery(null);
  }

}
