package utoronto.cs.cscc01.indexer;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;

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

import utoronto.cs.cscc01.crawler.CorpusDatabase;

public class CorpusIndexer {

  private static StandardAnalyzer stdAnalyzer = null;
  private static FSDirectory directory = null;
  private static String txtFolder = "upload/";

  /**
   * Parse the query, get the result from the index made from uploaded
   * files that were crawled on.
   * @param query - the user's query
   * @return answer - the answer to the user's query
   * @throws ParseException - if query is null or empty
   */
  public static String parseQuery(String query) throws ParseException {
    StringBuilder answer = null;
    if (query == null || query.equals("")) {
      throw new ParseException("Query must not be blank or null");
    }
    try {
    // parse the query
    Query qpc = new QueryParser("question", stdAnalyzer).parse(query);
    DirectoryReader indexReader = DirectoryReader.open(directory);
    IndexSearcher seeker = new IndexSearcher(indexReader);

    answer = new StringBuilder();
    TopDocs tDocs;
      tDocs = seeker.search(qpc, 10);
      ScoreDoc[] hits = tDocs.scoreDocs;
      if (hits.length <= 2) {
        answer.append("no results");
      } else {
        Document doc = seeker.doc(hits[0].doc);
        answer.append(doc.get("answer") + "\n");
      }
    } catch (IOException e) {
      System.out.println("Sorry, I did not understand your query.");
      e.printStackTrace();
    }
    return answer.toString();
  }

  /**
   * Add a new document to the index, or update an existing document.
   * @param writer - the IndexWriter
   * @param qAndA - the question and the respective answer
   */
  public void newDoc(IndexWriter writer, HashMap<String, String> qAndA) {
    // add the question and answer in separate fields in a document
    Document doc = new Document();
    String question = qAndA.keySet().iterator().next();
    String answer = qAndA.get(question);
    doc.add(new TextField("question", question, Field.Store.YES));
    doc.add(new StringField("answer", answer, Field.Store.YES));

    // if indexed document already exists, replace it
    try {
      writer.updateDocument(new Term("answer", answer), doc);
    } catch (IOException e) {
      System.out.println("Could not store information in the index.");
      e.printStackTrace();
    }
  }

  /**
   * Index the corpus file according to the contents of
   * question and answer by looking to the Corpus Database.
   * @throws IOException - if the index folder destination is not found
   */
  public void index() throws IOException {
    // set up analyzers and open the directory to be indexed
    stdAnalyzer = new StandardAnalyzer();
    directory = FSDirectory.open(Paths.get(txtFolder));
    if (directory == null) {
      throw new IOException("Check corpus path; it may not be valid");
    }
    IndexWriterConfig conf = new IndexWriterConfig(stdAnalyzer);

    IndexWriter writer = new IndexWriter(directory, conf);
    // get the questions and answers from the corpus
    CorpusDatabase corpusDb = new CorpusDatabase();
    Iterator<HashMap<String, String>> corpusItr = corpusDb.iterator();
    // index the corpus
    while (corpusItr.hasNext()) {
      newDoc(writer, corpusItr.next());
    }
    writer.close();
  }

}
