package utoronto.cs.cscc01.indexer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

public class WebIndexer {

  private static StandardAnalyzer analyzer = null;
  private static FSDirectory dir = null;
  private static String txtFolder = "Crawler/";

  /**
   * Parse the user's query, which can be either a full sentence
   * or just a few key words. Obtain the answer from the index of
   * documents, which is the web crawler documents.
   * @param query - the user's query.
   * @return resultDoc - the answer to the query based on matches.
   * @throws ParseException - if query empty or null
   */
  public static String parseQuery(String query) throws ParseException {
    String resultDoc = null;
    if (query == null || query.equals("")) {
      throw new ParseException("Query must not be empty or null");
    }
    try {
      Query qp = new QueryParser("contents", analyzer).parse(query);
      DirectoryReader indexReader = DirectoryReader.open(dir);
      IndexSearcher seeker = new IndexSearcher(indexReader);

      // collect the results and print them
      StringBuilder strBuilder = new StringBuilder();
      TopDocs top = seeker.search(qp, 10);
      ScoreDoc[] scores = top.scoreDocs;
      // if no results, set result String to no result
      if (scores.length == 0) {
        resultDoc = "No result";
      } else {
        // otherwise, get the contents from the result document
        Document d = seeker.doc(scores[0].doc);
        if (d != null) {
          strBuilder.append(d.get("contents") + "\n");
          resultDoc = strBuilder.toString();
        }
      }
    } catch (IOException e) {
      System.out.println("Could not parse the query.");
      e.printStackTrace();
    }
    return resultDoc;
  }

  /**
   * Add a new document to the index.
   * @param writer - the IndexWriter
   * @param title - the title of the document
   * @param contents - the contents of the document
   */
  public void newDoc(IndexWriter writer, String title, String contents) {
    // using the fileReader, try to open the file to index its contents
    try {
      // add document to index with fields "title" and "contents"
      Document d = new Document();
      d.add(new StringField("title", title, Field.Store.YES));
      d.add(new TextField("contents", contents, Field.Store.YES));
      // if document already exists in the index, recreate to update it
      writer.updateDocument(new Term("title", title), d);
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("File could not be opened for indexing!");
      System.out.println("Check that the file exists and is not a folder!");
    }
  }

  /**
   * Index the data that comes from the Web Crawler text files.
   * @throws IOException - if a file is not found.
   */
  public void index() throws IOException {
    // set up analyzers and open the directory to be indexed
    analyzer = new StandardAnalyzer();
    dir = FSDirectory.open(Paths.get(txtFolder));
    if (dir == null) {
      throw new IOException("Check indexing path; it may not be valid");
    }
    IndexWriterConfig conf = new IndexWriterConfig(analyzer);
    // gather all the files in the directory
    File[] txtFiles = new File(txtFolder).listFiles();
    try {
      IndexWriter writer = new IndexWriter(dir, conf);
      // for every file, make a new document for it
      for (File f : txtFiles) {
        if (f.getName().endsWith(".txt")) {
          if (!f.getName().equals("corpus.txt")) {
            String contents = new String(Files.readAllBytes(Paths.get(txtFolder + f.getName())));
            newDoc(writer, f.getName(), contents);
          }
        }
      }
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}