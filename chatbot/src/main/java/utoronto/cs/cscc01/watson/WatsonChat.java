package utoronto.cs.cscc01.watson;

import java.io.IOException;
import org.apache.lucene.queryparser.classic.ParseException;

import com.ibm.cloud.sdk.core.service.security.IamOptions;
import com.ibm.watson.assistant.v2.Assistant;
import com.ibm.watson.assistant.v2.model.CreateSessionOptions;
import com.ibm.watson.assistant.v2.model.DeleteSessionOptions;
import com.ibm.watson.assistant.v2.model.MessageInput;
import com.ibm.watson.assistant.v2.model.MessageOptions;
import com.ibm.watson.assistant.v2.model.MessageResponse;
import com.ibm.watson.assistant.v2.model.SessionResponse;

public class WatsonChat {

  private static IamOptions iam = null;
  private static Assistant assistant;
  private static String assistId;
  private static CreateSessionOptions options;
  private static SessionResponse response;
  private static String sessionId;

  public String watsonAssist(String query) throws IOException, ParseException {
    iam = new IamOptions.Builder()
            .apiKey("Qd_gBln3Nw0Su2jixFyKJImO-FLUyYXeH_Nm6szOmkcw").build();
    assistant = new Assistant("2019-02-28", iam);
    assistId = "256742ce-0065-4f75-895e-1e9f1e6cd0f0";

    // create a session with the Assistant
    options = new CreateSessionOptions.Builder(assistId).build();
    response = assistant.createSession(options).execute().getResult();
    sessionId = response.getSessionId();
    // question and answer session with Watson Assistant
    MessageInput msg = new MessageInput.Builder().text(query).build();
    MessageOptions msgOptions = new MessageOptions.Builder(assistId, sessionId).input(msg).build();

    // get the answer from Watson Assistant
    MessageResponse msgResponse = assistant.message(msgOptions).execute().getResult();
    String answer = msgResponse.getOutput().getGeneric().get(0).getText();

    // close session
    DeleteSessionOptions endSession = new DeleteSessionOptions.Builder(assistId, sessionId).build();
    assistant.deleteSession(endSession).execute();

    // there can be more than one message output, so use loop to get them all
    return answer;

    // done with Watson Assistant; close session
    /* DeleteSessionOptions endSession = new DeleteSessionOptions.Builder(assistId, sessionId).build();
    assistant.deleteSession(endSession).execute(); */
  }

}
