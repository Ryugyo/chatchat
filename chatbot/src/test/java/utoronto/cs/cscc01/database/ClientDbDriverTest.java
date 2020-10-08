package utoronto.cs.cscc01.database;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;

@RunWith( PowerMockRunner.class )
public class ClientDbDriverTest {

  @Before
  public void setUp() throws Exception {
    PowerMockito.mockStatic(DatabaseDriver.class);
    ArrayList<ArrayList<String>> searchHistory = new ArrayList<ArrayList<String>> ();
    ArrayList<String> row = new ArrayList<String>();
    row.add("email");
    row.add("name");
    row.add("message");
    row.add("time");
    searchHistory.add(row);
    PowerMockito.when(DatabaseDriver.getChatHistory(Mockito.anyString())).thenReturn(searchHistory);
  }

  @PrepareForTest({ DatabaseDriver.class })
  @Test
  public void testGetSearchHistory() {
    ArrayList<SearchHistory> result = ClientDbDriver.getSearchHistory("email");
    assertEquals("email", result.get(0).getEmail());
    assertEquals("name", result.get(0).getName());
    assertEquals("message", result.get(0).getContent());
    assertEquals("time", result.get(0).getFormattedDate());
  }

}
