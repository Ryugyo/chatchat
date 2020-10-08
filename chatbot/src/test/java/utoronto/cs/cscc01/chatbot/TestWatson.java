package utoronto.cs.cscc01.chatbot;

import static org.junit.Assert.*;
import java.io.IOException;
import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.Before;
import org.junit.Test;

import utoronto.cs.cscc01.watson.WatsonChat;

public class TestWatson {

	private WatsonChat wc;
	private String question;
	private String answer;
	
	@Before
	public void TestConnection() {
		wc = new WatsonChat();
	}
	
	@Test
	public void TestNull() throws IOException, ParseException {
		question = "";
		answer = wc.watsonAssist(question);
		assertNotNull(answer);
	}
	
	@Test
	public void TestGreeting() throws IOException, ParseException {
		question = "How are you";
		answer = wc.watsonAssist(question);
		assertNotNull(answer);
	}
	
	@Test
	public void TestThanks() throws IOException, ParseException {
		question = "Thanks";
		answer = wc.watsonAssist(question);
		assertNotNull(answer);
	}
	
	@Test
	public void TestBye() throws IOException, ParseException {
		question = "Bye";
		answer = wc.watsonAssist(question);
		assertNotNull(answer);
	}
	
	@Test
	public void TestParise() throws IOException, ParseException {
		question = "You are smart";
		answer = wc.watsonAssist(question);
		assertNotNull(answer);
	}
	
	@Test
	public void TestComplain() throws IOException, ParseException {
		question = "Dumb";
		answer = wc.watsonAssist(question);
		assertNotNull(answer);
	}
	
	@Test
	public void TestName() throws IOException, ParseException {
		question = "What is your name";
		answer = wc.watsonAssist(question);
		assertNotNull(answer);
	}
	
	@Test
	public void TestBirthday() throws IOException, ParseException {
		question = "What is your birthday";
		answer = wc.watsonAssist(question);
		assertNotNull(answer);
	}
	
	@Test
	public void TestJob() throws IOException, ParseException {
		question = "What is your job";
		answer = wc.watsonAssist(question);
		assertNotNull(answer);
	}
	
	@Test
	public void TestAge() throws IOException, ParseException {
		question = "How old are you";
		answer = wc.watsonAssist(question);
		assertNotNull(answer);
	}
	
	@Test
	public void TestFinTech() throws IOException, ParseException {
		question = "Fintech";
		answer = wc.watsonAssist(question);
		assertNotNull(answer);
	}
	
	@Test
	public void TestBlockchain() throws IOException, ParseException {
		question = "BlockChain";
		answer = wc.watsonAssist(question);
		assertNotNull(answer);
	}
	
	@Test
	public void TestAI() throws IOException, ParseException {
		question = "Machine Learning";
		answer = wc.watsonAssist(question);
		assertNotNull(answer);
	}
	
	@Test
	public void TestFinish() throws IOException, ParseException {
		question = "Shut up";
		answer = wc.watsonAssist(question);
		assertNotNull(answer);
	}
}
