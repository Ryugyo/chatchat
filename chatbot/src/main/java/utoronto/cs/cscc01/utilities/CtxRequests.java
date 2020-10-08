package utoronto.cs.cscc01.utilities;

import io.javalin.http.Context;

public class CtxRequests {

  public static String getUserEmail(Context context) {
    return context.formParam("email");
  }

  public static String getUserPassword(Context context) {
    return context.formParam("psw");
  }

  public static String getUserName(Context context) {
    return context.formParam("name");
  }

  public static String getUserAge(Context context) {
    return context.formParam("age");
  }

  public static String getUserJob(Context context) {
    return context.formParam("job");
  }

  public static String getFileUploadName(Context context) {
    return context.formParam("file");
  }

  public static String getCurrentUser(Context context) {
    return context.sessionAttribute("current_client");
  }

  public static String getCurrentUserEmail(Context context) {
    return context.sessionAttribute("email");
  }

  public static String getCurrentUserPassword(Context context) {
    return context.sessionAttribute("psw");
  }

  public static String getCurrentUserName(Context context) {
    return context.sessionAttribute("name");
  }

  public static String getCurrentUserAge(Context context) {
    return context.sessionAttribute("age");
  }

  public static String getCurrentUserJob(Context context) {
    return context.sessionAttribute("job");
  }

  public static String getCurrentAdmin(Context context) {
    return context.sessionAttribute("current_admin");
  }

  public static String getLoginRedirect(Context context) {
    return context.sessionAttribute("login_redirect");
  }
  
  public static void removeSessionAttr(Context context) {
	  // String loginRedirect = context.sessionAttribute("loginRedirect");
    // context.sessionAttribute("loginRedirect", null);

    context.sessionAttribute("name", null);
    context.sessionAttribute("age", null);
    context.sessionAttribute("job", null);
    context.sessionAttribute("email", null);
    context.sessionAttribute("psw", null);
    context.sessionAttribute("current_user", null);
    context.sessionAttribute("current_admin", null);
    context.sessionAttribute("loggedin", false);


    // return loginRedirect;

  }

  public static boolean deleteLogoutAttr(Context context) {
    String leave = context.sessionAttribute("logout");
    context.sessionAttribute("leave", null);
    return leave != null;
  }

  public static String deleteLoginRedirectAttr(Context ctx) {
    String loginRedirect = ctx.sessionAttribute("login_redirect");
    ctx.sessionAttribute("login_redirect", null);
    return loginRedirect;
  }

  public static String getUserFeedback(Context context) {
    return context.formParam("comment");
  }

  public static String getReview(Context context) {
    return context.sessionAttribute("feedback_list");
  }

}
