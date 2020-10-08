package utoronto.cs.cscc01.test.indexer;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.Before;
import org.junit.Test;

import utoronto.cs.cscc01.indexer.CorpusIndexer;

public class TestCorpusIndexer {

  @Before
  public void runIndexer() {
    CorpusIndexer ci = new CorpusIndexer();
    try {
      ci.index();
    } catch (IOException e) {
      // if something bad happens, print stack trace
      e.printStackTrace();
    }
  }

  @Test
  public void testCorpusParseQuery() throws ParseException {
    String answer = CorpusIndexer.parseQuery("innovation canada");
    assertEquals(true, answer.contains("innovation.canada.ca"));
  }

  @Test(expected = ParseException.class)
  public void testCorpusBlankQuery() throws ParseException {
    CorpusIndexer.parseQuery("");
  }

  @Test(expected = ParseException.class)
  public void testCorpusNullQuery() throws ParseException {
    CorpusIndexer.parseQuery(null);
  }

  @Test
  public void testNoResult() throws ParseException {
    String answer = CorpusIndexer.parseQuery("tina yang");
    assertEquals("no results", answer);
  }

}
