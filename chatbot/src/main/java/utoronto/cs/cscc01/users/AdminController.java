package utoronto.cs.cscc01.users;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import com.google.gson.Gson;

import io.javalin.core.util.FileUtil;
import io.javalin.http.Handler;
import utoronto.cs.cscc01.crawler.WebCrawlingDFI;
import utoronto.cs.cscc01.fileutilities.FileSwitchBox;
import utoronto.cs.cscc01.utilities.DirectoryFilesDisplay;
import utoronto.cs.cscc01.utilities.Filecontent;
import utoronto.cs.cscc01.utilities.MvController;
import utoronto.cs.cscc01.utilities.WebTemplates;
import utoronto.cs.cscc01.utilities.webCrawlerDisplay;

public class AdminController {

  /*
   * Handler to display Admin Home page.
   */
  public static Handler displayAdminPage = ctx -> {
    Map<String, Object> model = MvController.commonModel(ctx);
    ctx.render(WebTemplates.WebFiles.ADMIN, model);
  };

  /*
   * Handler to display Crawler page.
   * Crawler page not a new page, just an activation button
   * to start the back-end web crawler.
   */
  public static Handler crawler = ctx -> {
    Map<String, Object> model = MvController.commonModel(ctx);
    // run web crawler
    System.out.println("Start Crawling");
    boolean crawlSuccess = WebCrawlingDFI.crawler();
    if (!crawlSuccess) {
     model.put("crawl-successful", false);
      System.out.println("crawling failed");
    } else {
      model.put("crawl-successful", true);
      System.out.println("crawling complete");
    }
    //filename, content
    ArrayList<Filecontent> crawls = webCrawlerDisplay.getAllResult();
    String json = new Gson().toJson(crawls);
    model.put("crawls", json);

    ctx.render(WebTemplates.WebFiles.WEB_CRAWLER, model);
  };

  /*
   * Handler to display the File Upload page.
   */
  public static Handler displayFileUploadPage = ctx -> {
    Map<String, Object> model = MvController.commonModel(ctx);
    ctx.sessionAttribute("file_upload_failed", false);
    ctx.sessionAttribute("file_upload_success", false);

    ArrayList<String> files = DirectoryFilesDisplay.listOfFiles("./upload");
    String json = new Gson().toJson(files);
    System.out.println("==="+json);
    System.out.println(files.toString());
    model.put("files", json);

    ctx.render(WebTemplates.WebFiles.FILE_UPLOAD, model);
  };

  /*
   * Handler to upload a file to the File Upload page.
   */
  public static Handler uploadFilePost = ctx -> {
    Map<String, Object> model = MvController.commonModel(ctx);
    // store uploaded files under upload directory
    ctx.uploadedFiles("file").forEach(file -> {
    	String fname = file.getFilename();
      if (fname.contains(".doc") || fname.contains(".docx") ||fname.contains(".txt") || fname.contains(".pdf")) {
        ctx.sessionAttribute("file_upload_success", true);
        ctx.sessionAttribute("file_upload_failed", false);
        FileUtil.streamToFile(file.getContent(), "upload/" + file.getFilename());
        ctx.res.setCharacterEncoding("utf-8");
        if (fname.toLowerCase().contains("corpus")) {
          try {
            FileSwitchBox.fileSwitch("upload/" + fname);
            } catch (IOException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
        }
      } else {
        ctx.sessionAttribute("file_upload_success", false);
        ctx.sessionAttribute("file_upload_failed", true);
      }
      // model.put("file", getFileUploadName(ctx));
      // ctx.render(WebTemplates.WebFiles.FILE_UPLOAD, model);
    });
    
    ArrayList<String> files = DirectoryFilesDisplay.listOfFiles("./upload");
    String json = new Gson().toJson(files);
    System.out.println("==="+json);
    System.out.println(files.toString());
    model.put("files", json);
    
    ctx.render(WebTemplates.WebFiles.FILE_UPLOAD, model);

  };

  
  public static Handler displayCrawlerPage = ctx -> {
    Map<String, Object> model = MvController.commonModel(ctx);

    //filename, content
    ArrayList<Filecontent> crawls = webCrawlerDisplay.getAllResult();
    String json = new Gson().toJson(crawls);
    model.put("crawls", json);

    ctx.render(WebTemplates.WebFiles.WEB_CRAWLER, model);
  };

}
