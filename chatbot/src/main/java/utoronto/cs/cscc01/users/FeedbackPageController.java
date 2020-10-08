package utoronto.cs.cscc01.users;

import java.util.ArrayList;
import java.util.Map;

import com.google.gson.Gson;

import io.javalin.http.Handler;
import utoronto.cs.cscc01.database.AdminDao;
import utoronto.cs.cscc01.database.ClientDbDriver;
import utoronto.cs.cscc01.database.Feedback;
import utoronto.cs.cscc01.utilities.MvController;
import utoronto.cs.cscc01.utilities.WebTemplates;

import static utoronto.cs.cscc01.utilities.CtxRequests.*;

public class FeedbackPageController {

  /*
   * Handler to make sure only admins can access the
   * reviews page.
   */
  public static Handler checkAdminAccess = ctx -> {
    if (!ctx.path().startsWith(WebTemplates.WebRoutes.REVIEWS)) {
        return;
    }
    if (ctx.sessionAttribute("current_admin") == null) {
        ctx.redirect(WebTemplates.WebFiles.LOGIN_ADMIN);
    }
  };

  /*
   * Handler to display Reviews page.
   */
  public static Handler displayReviewsPage = ctx -> {
    Map<String, Object> model = MvController.commonModel(ctx);
    ArrayList<Feedback> fbs = AdminDao.viewAllFeedback();
    String json = new Gson().toJson(fbs);
    model.put("fbs", json);
    ctx.render(WebTemplates.WebFiles.REVIEWS, model);
  };

  public static Handler adminPostReviews = ctx -> {
    Map<String, Object> model = MvController.commonModel(ctx);
    ctx.sessionAttribute("feedback_list");
    ctx.sessionAttribute("current_admin");
    ctx.render(WebTemplates.WebFiles.REVIEWS, model);
  };
  /*
   * Handler to make sure only a signed in user can access
   * the give feedback page.
   */
  // public static Handler checkClientAccess = ctx -> {
  //   if (!ctx.path().startsWith(WebTemplates.WebRoutes.GIVE_FEEDBACK)) {
  //     return;
  //   }
  //   if (ctx.sessionAttribute("current_client") == null) {
  //     ctx.sessionAttribute("login_redirect", ctx.path());
  //     ctx.redirect(WebTemplates.WebFiles.LOGIN_USER);
  //   }
  // };

  /*
   * Handler to show feedback page to client
   */
  public static Handler displayGiveFeedback = ctx -> {
    Map<String, Object> model = MvController.commonModel(ctx);
    // ctx.sessionAttribute("feedback_list", null);
    ctx.render(WebTemplates.WebFiles.GIVE_FEEDBACK, model);
  };

  /*
   * Handler to post reviews to the feedback page
   */
  public static Handler clientPostFeedback = ctx -> {
    Map<String, Object> model = MvController.commonModel(ctx);
    String name = getCurrentUserName(ctx);
    String age = getCurrentUserAge(ctx);
    String email = getCurrentUserEmail(ctx);
    String job = getCurrentUserJob(ctx);
    Feedback fb = new Feedback(email, name, getUserFeedback(ctx));
    Client c = new Client(name, Integer.parseInt(age), email, job, true);
    ClientDbDriver.addFeedback(c, fb);
    // model.put("comment", getUserFeedback(ctx));
    // ctx.sessionAttribute("feedback_list", getUserFeedback(ctx));
    model.put("fb_submitted", true);
    ctx.render(WebTemplates.WebFiles.GIVE_FEEDBACK, model);
  };

}
