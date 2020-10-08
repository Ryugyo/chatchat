package utoronto.cs.cscc01.crawler;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class WebCrawlingDFI {

  /**
   * Crawl the company's web site
   * @return result - boolean of if the crawler sucessfully crawl the result
   */
  public static boolean crawler() {
    Document doc;
    // Save original out stream.
    PrintStream originalOut = System.out;
    // Save original err stream.
    PrintStream originalErr = System.err;
    boolean result = true;
    try{
      doc = Jsoup.connect("https://www.digitalfinanceinstitute.org/").get();
      //Select all the Elements from Navigation Bar store in bar
      Elements bar = doc.select("ul#menu-dfi-menu a");
      //iterate through each element in Navigation Bar
      for (Element block : bar) {
        //test if the link have search word
          //results += 1;
          String text = block.text();
          if (!text.contains("Home") && !text.contains("Our People") && !text.contains("About Us") 
              && !text.contains("What we do") && !text.contains("Partnerships & Associations") && !text.contains("Events Calendar")) {
            
            //output the contents to accordance file
            //WebCrawlingDFI.outputFile(text);
            //System.out.printf("text : %s%n", text);

            Document naviBar = Jsoup.connect(block.attr("href")).get();
            Elements articles = naviBar.select("div#content article h1 a");
            for(Element article:articles) {
              String title = article.text().replace("?", "");
              WebCrawlingDFI.outputFile(title);
              //System.out.println(article.text());
              //System.out.println(article.attr("href"));
              WebCrawlingDFI a = new WebCrawlingDFI();
              a.printParagraphs(article, "div.entry-content");
            }
          } else if(text.contains("Our People")) {
            //simply print the name of partner
            //WebCrawlingDFI.outputFile(text);
            //System.out.printf("Contents from %s:%n", text);
            Document op = Jsoup.connect(block.attr("href")).get();
            Elements people = op.select("div#content article h1 a");
            for (Element person:people) {
              String titile = person.text();
              WebCrawlingDFI.outputFile(titile);
              //System.out.printf("%s%n", person.text());
              WebCrawlingDFI a = new WebCrawlingDFI();
              a.printParagraphs(person, "div.entry-content");
            }
          } else if(text.contains("What we do")) {
            //print the header and paragraph
            WebCrawlingDFI.outputFile(text);
            //System.out.printf("Contents from %s:%n", text);
            WebCrawlingDFI a = new WebCrawlingDFI();
            a.printParagraphs(block, "div.entry-content");
          } else if (text.contains("Partnership")) {
            WebCrawlingDFI.outputFile(text);
            //System.out.printf("Contents from %s:%n",text);;
            WebCrawlingDFI a = new WebCrawlingDFI();
            a.printParagraphs(block, "div.entry-content");
          } else {continue;}
          
      }
      System.setOut(originalOut);
      System.setErr(originalErr);
      
    } catch (IOException e) {
      result = false;
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return result;
  }
  private void printParagraphs(Element element, String className) throws IOException {
    // from the web page scrape paragraphs within the url
    Document html = Jsoup.connect(element.attr("href")).get();
    Elements paragraphs = html.select(className);
    for(Element p:paragraphs) {
      System.out.println(p.text());
    }
  }
  
  private static void outputFile(String pageName) throws FileNotFoundException {
    //output the file to accordance file system
    String fn = "./Crawler/" + pageName + ".txt";
    PrintStream out = new PrintStream(new FileOutputStream(fn));
    System.setOut(out);
  }
}