package utoronto.cs.cscc01.watson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import com.google.gson.JsonObject;
import com.ibm.cloud.sdk.core.service.security.IamOptions;
import com.ibm.watson.assistant.v2.Assistant;
import com.ibm.watson.assistant.v2.model.CreateSessionOptions;
import com.ibm.watson.assistant.v2.model.DeleteSessionOptions;
import com.ibm.watson.assistant.v2.model.DialogRuntimeResponseGeneric;
import com.ibm.watson.assistant.v2.model.MessageInput;
import com.ibm.watson.assistant.v2.model.MessageOptions;
import com.ibm.watson.assistant.v2.model.MessageResponse;
import com.ibm.watson.assistant.v2.model.SessionResponse;

import io.javalin.http.Handler;
import io.javalin.websocket.WsContext;
import io.javalin.websocket.WsHandler;
import utoronto.cs.cscc01.database.ClientDbDriver;
import utoronto.cs.cscc01.database.SearchHistory;
import utoronto.cs.cscc01.utilities.WebTemplates;

import static j2html.TagCreator.article;
import static j2html.TagCreator.attrs;
import static j2html.TagCreator.b;
import static j2html.TagCreator.div;
import static j2html.TagCreator.p;
import static j2html.TagCreator.span;
import static utoronto.cs.cscc01.utilities.CtxRequests.getCurrentUserEmail;
import static utoronto.cs.cscc01.utilities.CtxRequests.getCurrentUserName;

public class WatsonController {

  private static IamOptions iam = null;
  private static Assistant assistant = null;
  private static String assistId;
  private static CreateSessionOptions options = null;
  private static SessionResponse response = null;
  private static String sessionId;
  public static String userEmail = "no email";
  public static String userName = "user";

  public static String defaultEmail = "no email";
  public static String defaultName = "User";

  /*
   * Handler to display the Watson (Education) chat page
   */
  public static Handler displayChatPage = ctx -> {
    ctx.sessionAttribute("answer", null);
    if (getCurrentUserName(ctx) != null) {
        userName = getCurrentUserName(ctx);
        userEmail = getCurrentUserEmail(ctx);
    } else {
      userName = defaultName;
      userEmail = defaultEmail;
    }
    ctx.render(WebTemplates.WebFiles.CHAT_EDU);
  };

  /*
   * Handler to render Watson's response to a query
   */
  public static Consumer<WsHandler> watsonChatPage = ws -> {
    ws.onConnect(ctx -> {
      sendMessage(userName, "joined the chat [EDU]", ctx);
      String msg = "joined the chat [EDU]";
      SearchHistory history = new SearchHistory(userEmail, userName, msg);
      ClientDbDriver.saveHistory(userName, userEmail, history, false);
      
      iam = new IamOptions.Builder()
              .apiKey("Qd_gBln3Nw0Su2jixFyKJImO-FLUyYXeH_Nm6szOmkcw").build();
      assistant = new Assistant("2019-02-28", iam);
      assistId = "256742ce-0065-4f75-895e-1e9f1e6cd0f0";

      // create a session with the Assistant
      options = new CreateSessionOptions.Builder(assistId).build();
      response = assistant.createSession(options).execute().getResult();
      sessionId = response.getSessionId();
    });
    ws.onMessage(ctx -> {
      SearchHistory userHistory;

      try {
        // Still doesn't work: might need to put Watson code directly in here??
        MessageInput msg = new MessageInput.Builder().text(ctx.message()).build();
        MessageOptions msgOptions = new MessageOptions.Builder(assistId, sessionId).input(msg).build();

        // get the answer from Watson Assistant
        MessageResponse msgResponse = assistant.message(msgOptions).execute().getResult();
        List<DialogRuntimeResponseGeneric> runResponse = msgResponse.getOutput().getGeneric();

        sendMessage(userName, ctx.message(), ctx);
        userHistory = new SearchHistory(userEmail, userName, ctx.message());
        ClientDbDriver.saveHistory(userName, userEmail, userHistory, false);

        for (int i = 0; i < runResponse.size(); i++) {
          String answer = msgResponse.getOutput().getGeneric().get(i).getText();
          System.out.println(answer);
          sendMessage("ChatChat", answer, ctx);
          SearchHistory chatHistory = new SearchHistory(userEmail, "ChatChat", answer);
          ClientDbDriver.saveHistory(userName, userEmail, chatHistory, true);
        }
      } catch (Exception e) {
        e.printStackTrace();
        sendMessage(userName, ctx.message(), ctx);
        sendMessage("ChatChat", "Sorry I cannot find any matches", ctx);
        SearchHistory chatHistory = new SearchHistory(userEmail, "ChatChat", ctx.message());
        ClientDbDriver.saveHistory(userName, userEmail, chatHistory, true);
      }
    });
    ws.onClose(ctx -> {
      // close session
      DeleteSessionOptions endSession = new DeleteSessionOptions.Builder(assistId, sessionId).build();
      assistant.deleteSession(endSession).execute();
    });
  };

  /**
   * Create the message to be sent to the front end Education chat page
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
