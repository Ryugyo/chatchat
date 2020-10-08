package utoronto.cs.cscc01.users;

import java.util.ArrayList;
import java.util.Map;

import com.google.gson.Gson;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import utoronto.cs.cscc01.database.ClientDbDriver;
import utoronto.cs.cscc01.database.SearchHistory;
import utoronto.cs.cscc01.utilities.MvController;
import utoronto.cs.cscc01.utilities.WebTemplates;
import static utoronto.cs.cscc01.utilities.CtxRequests.*;

public class ClientController {

  /*
   * Handler to display client home page.
   */
  public static Handler displayUserPage = ctx -> {
    Map<String, Object> model = MvController.commonModel(ctx);
    ctx.render(WebTemplates.WebFiles.USER, model);
  };

  /*
   * Handler to display user profile page.
   */
  public static Handler displayProfilePage = ctx -> {
    Map<String, Object> model = MvController.commonModel(ctx);
    ctx.render(WebTemplates.WebFiles.PROFILE, model);
  };

  /*
   * Handler to display the user's Chat History page.
   */
  public static Handler displayChatHistory = ctx -> {
    Map<String, Object> model = MvController.commonModel(ctx);
    String email = getCurrentUserEmail(ctx);
    ArrayList<SearchHistory> sh = ClientDbDriver.getSearchHistory(email);
    String json = new Gson().toJson(sh);
    //email, content, formattedDate, name

    model.put("history", json);
    ctx.render(WebTemplates.WebFiles.HISTORY, model);
  };


}
