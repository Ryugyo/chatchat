package utoronto.cs.cscc01.utilities;

public class WebTemplates {

  public static class WebRoutes {
    public static final String HOME = "/home";
    // LOGIN_CLIENT -> LOGIN_USER
    public static final String LOGIN_USER = "/user_login";
    public static final String LOGIN_ADMIN = "/admin_login";
    public static final String PROFILE = "/profile";
    // CHAT_HISTORY -> HISTORY
    public static final String HISTORY = "/history";
    // FEEDBACK_PAGE -> REVIEWS
    public static final String REVIEWS = "/view_feedback";
    // PROVIDE_FEEDBACK -> GIVE_FEEDBACK
    public static final String GIVE_FEEDBACK = "/feedback";
    public static final String REGISTER = "/signup";

    /* UNREGISTERED */
    public static final String CRAWLER = "/crawler";
    public static final String CHAT = "/chat";

    /* NEWLY ADDED */
    public static final String CHAT_DFI = "/chat_dfi";
    public static final String CHAT_EDU = "/chat_edu";
    public static final String FILE_UPLOAD = "/file_upload";
    public static final String ADMIN = "/admin";
    public static final String USER = "/user";
    public static final String WEB_CRAWLER = "/admin_crawler";

  }

  public static class WebFiles {
    // Renaming public->public as all .html files are stored under /public
    public static final String HOME = "/public/index.html";
    public static final String LOGIN_USER = "/public/user_login.html";
    public static final String LOGIN_ADMIN = "/public/admin_login.html";
    public static final String PROFILE = "/public/profile.html";
    public static final String REVIEWS = "/public/admin_feedback.html";
    public static final String GIVE_FEEDBACK = "/public/user_feedback.html";
    public static final String HISTORY = "/public/history.html";
    // SIGN_UP -> REGISTER
    public static final String REGISTER = "/public/signup.html";

    /* UNREGISTERED */
    public static final String CRAWLER = "/public/crawler.html";
    public static final String CHAT = "/public/chat.html";

    /* NEWLY ADDED */
    public static final String CHAT_DFI = "/public/chat_dfi.html";
    public static final String CHAT_EDU = "/public/chat_education.html";
    public static final String FILE_UPLOAD = "/public/file_upload.html";
    public static final String ADMIN = "/public/admin.html";
    public static final String USER = "/public/user.html";
    public static final String WEB_CRAWLER = "/public/webCrawler.html";

  }

}