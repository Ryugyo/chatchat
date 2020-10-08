package utoronto.cs.cscc01.utilities;

import java.util.HashMap;
import java.util.Map;

import io.javalin.http.Context;
import static utoronto.cs.cscc01.utilities.CtxRequests.*;

public class MvController {

  /*
   * Hash Map containing the session attributes common to all pages.
   */
  public static Map<String, Object> commonModel(Context context) {
    Map<String, Object> view = new HashMap<>();
    view.put("current_client", getCurrentUser(context));
    view.put("current_admin", getCurrentAdmin(context));
    return view;
  }

}
