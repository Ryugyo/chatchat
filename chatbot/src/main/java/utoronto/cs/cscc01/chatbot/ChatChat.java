package utoronto.cs.cscc01.chatbot;

import java.sql.Connection;
import java.sql.SQLException;

import io.javalin.Javalin;
import utoronto.cs.cscc01.database.AdminDao;
import utoronto.cs.cscc01.database.DatabaseDriver;

import utoronto.cs.cscc01.indexer.DfiController;
import utoronto.cs.cscc01.security.LoginController;
import utoronto.cs.cscc01.users.AdminController;
import utoronto.cs.cscc01.users.ClientController;
import utoronto.cs.cscc01.users.FeedbackPageController;
import utoronto.cs.cscc01.utilities.WebTemplates;
import utoronto.cs.cscc01.watson.WatsonController;

public class ChatChat {

  public static AdminDao admins;
  public static Connection c;

  public static void main(String[] args) {

    admins = new AdminDao();
    try {
      c = DatabaseDriver.connectOrCreateDataBase();
      DatabaseDriver.initialize(c);
    } catch (SQLException e) {
      // exception if connection fails
      e.printStackTrace();
      System.out.println("Could not initialize the database.");
    }

    Javalin chatchat = Javalin.create(config -> {
        config.addStaticFiles("/public");
      }
    ).start(8081);

    chatchat.routes(() -> {
      // chatchat.before(FeedbackPageController.checkClientAccess);
      // chatchat.before(FeedbackPageController.checkAdminAccess);

      // -SERVE- HOME, INDEX PAGE
      chatchat.get(WebTemplates.WebRoutes.HOME, LoginController.displayHomePage);
      chatchat.get("/", LoginController.displayHomePage);

      // -SERVE- CLIENT, ADMIN - LOGIN/SIGNUP 
      chatchat.get(WebTemplates.WebRoutes.REGISTER, LoginController.displayRegisterPage);
      chatchat.get(WebTemplates.WebRoutes.LOGIN_USER, LoginController.displayClientLoginPage);
      chatchat.get(WebTemplates.WebRoutes.LOGIN_ADMIN, LoginController.displayAdminLoginPage);
      chatchat.get(WebTemplates.WebRoutes.USER, ClientController.displayUserPage);
      chatchat.get(WebTemplates.WebRoutes.ADMIN, AdminController.displayAdminPage);


      // -SERVE- CHAT PAGE
      chatchat.get(WebTemplates.WebRoutes.CHAT_EDU, WatsonController.displayChatPage);
      chatchat.get(WebTemplates.WebRoutes.CHAT_DFI, DfiController.displayChatPage);
      
      // -SERVE- FEEDBACK, REVIEWS PAGE
      chatchat.get(WebTemplates.WebRoutes.REVIEWS, FeedbackPageController.displayReviewsPage);
      chatchat.get(WebTemplates.WebRoutes.GIVE_FEEDBACK, FeedbackPageController.displayGiveFeedback);

      
      chatchat.get(WebTemplates.WebRoutes.HISTORY, ClientController.displayChatHistory);

      chatchat.get(WebTemplates.WebRoutes.PROFILE, ClientController.displayProfilePage);
      
      chatchat.get(WebTemplates.WebRoutes.FILE_UPLOAD, AdminController.displayFileUploadPage);
      chatchat.get(WebTemplates.WebRoutes.WEB_CRAWLER, AdminController.displayCrawlerPage);

      // POST CLIENT, ADMIN
      chatchat.post(WebTemplates.WebRoutes.REGISTER, LoginController.addNewClient);    
      chatchat.post(WebTemplates.WebRoutes.USER, LoginController.clientLoginPost);
      chatchat.post(WebTemplates.WebRoutes.ADMIN, LoginController.adminLoginPost);

      // POST FEEDBACK
      chatchat.post(WebTemplates.WebRoutes.GIVE_FEEDBACK, FeedbackPageController.clientPostFeedback);
      
      // crawler
      chatchat.post(WebTemplates.WebRoutes.FILE_UPLOAD, AdminController.uploadFilePost);
      chatchat.post(WebTemplates.WebRoutes.WEB_CRAWLER, AdminController.crawler);
      
      chatchat.ws("/chat", DfiController.dfiChatPage);
      chatchat.ws("/watsonchat", WatsonController.watsonChatPage);
    });
  }

}
