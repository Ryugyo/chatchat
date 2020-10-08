package utoronto.cs.cscc01.security;

import io.javalin.http.Handler;
import utoronto.cs.cscc01.database.AdminDao;
import utoronto.cs.cscc01.database.AuthenticateUser;
import utoronto.cs.cscc01.database.ClientDbDriver;
import utoronto.cs.cscc01.users.Admin;
import utoronto.cs.cscc01.users.Client;
import utoronto.cs.cscc01.utilities.MvController;
import utoronto.cs.cscc01.utilities.WebTemplates;

import static utoronto.cs.cscc01.utilities.CtxRequests.*;

import java.util.ArrayList;
import java.util.Map;

public class LoginController {

  //COMPLETED
  /* serve register page */
  public static Handler displayRegisterPage = ctx -> {
    Map<String, Object> model = MvController.commonModel(ctx);
    // model.put("logout", deleteLogoutAttr(ctx));
    // model.put("login_redirect", deleteLoginRedirectAttr(ctx));
    // ctx.sessionAttribute("current_client", null);

    ctx.render(WebTemplates.WebFiles.REGISTER, model);
  };

  
  //COMPLETED
  /* check and store new client info */
  public static Handler addNewClient = ctx -> {
    Map<String, Object> view = MvController.commonModel(ctx);
    // gather new user email, create new user in database
    String email = getUserEmail(ctx);
    String name = getUserName(ctx);
    String age = getUserAge(ctx);
    String job = getUserJob(ctx);
    if(ClientDbDriver.checkClientExist(email)) {
      ctx.sessionAttribute("signup_failed", true);
      ctx.redirect(WebTemplates.WebRoutes.REGISTER);
      ctx.render(WebTemplates.WebFiles.REGISTER, view);
    } else {
      ctx.sessionAttribute("signup_failed", false);
      ctx.sessionAttribute("loggedin", true);
      Client client = new Client(name, Integer.parseInt(age), email, job, true);
      // once user adds their information and submits, take them to their profile
      ClientDbDriver.newClient(client, getUserPassword(ctx));
      ctx.sessionAttribute("current_client", email);
      ctx.sessionAttribute("email", email);
      ctx.sessionAttribute("name", name);
      ctx.sessionAttribute("age", age);	
  //    ctx.sessionAttribute("psw", getUserPassword(ctx));
      ctx.sessionAttribute("job", job);
      ctx.render(WebTemplates.WebFiles.PROFILE, view);
    }   
  };

  //COMPLETED
  /* serve admin_login page */
  public static Handler displayAdminLoginPage = ctx -> {
    Map<String, Object> model = MvController.commonModel(ctx);
    // model.put("logout", deleteLogoutAttr(ctx));
    // model.put("login_redirect", deleteLoginRedirectAttr(ctx));
    ctx.render(WebTemplates.WebFiles.LOGIN_ADMIN, model);
  };

  //COMPLETED
  /*serve client_login page */
  public static Handler displayClientLoginPage = ctx -> {
    Map<String, Object> model = MvController.commonModel(ctx);
    // model.put("logout", deleteLogoutAttr(ctx));
    ctx.render(WebTemplates.WebFiles.LOGIN_USER, model);
  };


  //COMPLETED
  /* check and login admin */
  public static Handler adminLoginPost = ctx -> {
    Map<String, Object> view = MvController.commonModel(ctx);
    // System.out.println(getUserEmail(ctx) + ' ' + getUserPassword(ctx));
    if (AuthenticateUser.loginAdmin(getUserEmail(ctx), getUserPassword(ctx))) {
      System.out.println("===Admin logged in===");
      ctx.sessionAttribute("loggedin", true);
      String email = getUserEmail(ctx);
      Admin admin = AdminDao.getAdmin(email);
      String name = admin.getName();
      ctx.sessionAttribute("current_admin", email);
      ctx.sessionAttribute("name", name);
      ctx.sessionAttribute("admin_login_failed", false);
      ctx.render(WebTemplates.WebFiles.ADMIN, view);
    } else {
      System.out.println("===Admin failed in===");
      ctx.sessionAttribute("admin_login_failed", true);
      ctx.redirect(WebTemplates.WebRoutes.LOGIN_ADMIN);
      ctx.render(WebTemplates.WebFiles.LOGIN_ADMIN, view);
    }
  };


  //COMPLETED
  /* check and login client */
  public static Handler clientLoginPost = ctx -> {
    Map<String, Object> view = MvController.commonModel(ctx);
    if (AuthenticateUser.loginClient(getUserEmail(ctx), getUserPassword(ctx))) {
      ctx.sessionAttribute("loggedin", true);
      String email = getUserEmail(ctx);
      ctx.sessionAttribute("current_client", email);
      //NAME, EMAIL, OCCUPATION, AGE
      ArrayList<String> user = AuthenticateUser.getUser(email);
      String name = user.get(0);
      String job = user.get(2);
      String age = user.get(3);
      ctx.sessionAttribute("email", email);
      ctx.sessionAttribute("name", name);
      ctx.sessionAttribute("age", age);	
      ctx.sessionAttribute("job", job);
      ctx.sessionAttribute("client_login_failed", false);
      ctx.render(WebTemplates.WebFiles.USER, view);
    } else {
      ctx.sessionAttribute("client_login_failed", true);
      ctx.redirect(WebTemplates.WebRoutes.LOGIN_USER);
      ctx.render(WebTemplates.WebFiles.LOGIN_USER, view);
    }
  };

//  /*
//   * Handler to end user session upon logging out.
//   */
//  public static Handler logout = ctx -> {
//    // removeSessionAttr(ctx);
//    // ctx.sessionAttribute("logout", "true");
//    // ctx.redirect(WebTemplates.WebFiles.HOME);
//  };

  // COMPLETED
  /* serve home page */
  public static Handler displayHomePage = ctx -> {
    Map<String, Object> model = MvController.commonModel(ctx);
    // model.put("logout", true);
    removeSessionAttr(ctx);
    ctx.render(WebTemplates.WebFiles.HOME, model);
  };

}
