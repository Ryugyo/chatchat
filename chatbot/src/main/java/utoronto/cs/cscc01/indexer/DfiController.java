package utoronto.cs.cscc01.indexer;

import static j2html.TagCreator.article;
import static j2html.TagCreator.div;
import static j2html.TagCreator.attrs;
import static j2html.TagCreator.b;
import static j2html.TagCreator.p;
import static j2html.TagCreator.span;
import static utoronto.cs.cscc01.utilities.CtxRequests.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import com.google.gson.JsonObject;

import io.javalin.http.Handler;
import io.javalin.websocket.WsContext;
import io.javalin.websocket.WsHandler;
import utoronto.cs.cscc01.database.ClientDbDriver;
import utoronto.cs.cscc01.database.SearchHistory;
import utoronto.cs.cscc01.utilities.MvController;
import utoronto.cs.cscc01.utilities.WebTemplates;

public class DfiController {

  public static String userEmail = "no email";
  public static String userName = "User";

  public static String defaultEmail = "no email";
  public static String defaultName = "User";

  /*
   * Handler to display the DFI chat page
   */
  public static Handler displayChatPage = ctx -> {
    Map<String, Object> model = MvController.commonModel(ctx);
    if (getCurrentUserName(ctx) != null) {
      userName = getCurrentUserName(ctx);
      userEmail = getCurrentUserEmail(ctx);
    } else {
      userName = defaultName;
      userEmail = defaultEmail;
    }
    CorpusIndexer index = new CorpusIndexer();
    index.index();
    WebIndexer webIndex = new WebIndexer();
    webIndex.index();
    ctx.sessionAttribute("answer", null);
    ctx.render(WebTemplates.WebFiles.CHAT_DFI, model);
  };

  /*
   * Handler to render the index's response to a query
   */
  public static Consumer<WsHandler> dfiChatPage = ws -> {
    ws.onConnect(ctx -> {
      sendMessage(userName, "joined the chat [DFI]", ctx);
      String msg = "joined the chat [DFI]";
      SearchHistory history = new SearchHistory(userEmail, userName, msg);
      ClientDbDriver.saveHistory(userName, userEmail, history, false);
    });
    ws.onMessage(ctx -> {
      SearchHistory userHistory;
      try {
        String result = CorpusIndexer.parseQuery(ctx.message());
        String webResult = WebIndexer.parseQuery(ctx.message());
        sendMessage(userName, ctx.message(), ctx);
        // put message in message history
        userHistory = new SearchHistory(userEmail, userName, ctx.message());
        ClientDbDriver.saveHistory(userName, userEmail, userHistory, false);
        // TimeUnit.SECONDS.sleep(1);
        if (result.equals("no results")) {
          sendMessage("ChatChat", webResult, ctx);
          SearchHistory chatHistory = new SearchHistory(userEmail, "ChatChat", webResult);
          ClientDbDriver.saveHistory(userName, userEmail, chatHistory, true);
        } else {
          sendMessage("ChatChat", result, ctx);
          SearchHistory chatHistory = new SearchHistory(userEmail, "ChatChat", result);
          ClientDbDriver.saveHistory(userName, userEmail, chatHistory, true);
        }
        
      } catch (Exception e) {
        sendMessage(userName, ctx.message(), ctx);
        sendMessage("ChatChat", "Sorry I cannot find any matches", ctx);
        userHistory = new SearchHistory(userEmail, userName, ctx.message());
        ClientDbDriver.saveHistory(userName, userEmail, userHistory, false);
        userHistory = new SearchHistory(userEmail, userName, "Sorry I cannot find any matches");
        ClientDbDriver.saveHistory(userName, userEmail, userHistory, true);
      }

    });
  };

  /**
   * Create the message to be sent to the front end DFI chat page
   * @param sender - the name of the message sender
   * @param message - the message
   * @param ctx - the Context
   */
  private static void sendMessage(String sender, String message, WsContext ctx) {
    JsonObject element = new JsonObject();
    if (sender == "ChatChat") {
      element.addProperty("userMessage", displayLoading());
      element.addProperty("answer", false);
      ctx.send(element.toString());
      try {
        TimeUnit.SECONDS.sleep(2);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      element.addProperty("userMessage", createResponseHtmlMessage(sender, message));
      element.addProperty("answer", true);
    } else {
      element.addProperty("userMessage", createUserHtmlMessage(sender, message));
      element.addProperty("answer", false);
    }
    element.addProperty("username", sender);
    ctx.send(element.toString());
  }

  /**
   * Form the html script for displaying the user's question and answer in a bubble.
   * @param sender - the sender's name
   * @param message - the sender's message
   * @return the html article
   */
  private static String createUserHtmlMessage(String sender, String message) {
    return div(
          b(sender + "."),
          span(attrs(".timestamp"), new SimpleDateFormat("HH:mm:ss").format(new Date())),
          p(message)
        ).withId("userMsg").render();
  }

  /**
   * Form the html script for displaying the bot's question and answer in a bubble.
   * @param sender - the bot
   * @param message - the bot's response
   * @return the html article
   */
  private static String createResponseHtmlMessage(String sender, String message) {
      return article(
          b(sender + "."),
          span(attrs(".timestamp"), new SimpleDateFormat("HH:mm:ss").format(new Date())),
          p(message)
      ).render();
  }

  /**
   * Display a loading animation for to show that the bot is working on
   * a response.
   * @return the animation
   */
  private static String displayLoading() {
    return div(
      div(
        div().withClass("obj"),
        div().withClass("obj"),
        div().withClass("obj"),
        div().withClass("obj"),
        div().withClass("obj"),
        div().withClass("obj"),
        div().withClass("obj"),
        div().withClass("obj")
      ).withClass("loading")
    ).withClass("load_container").render();
  }
}
