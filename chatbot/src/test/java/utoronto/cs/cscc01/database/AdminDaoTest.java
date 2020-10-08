package utoronto.cs.cscc01.database;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;

@RunWith( PowerMockRunner.class )
public class AdminDaoTest {

  @Before
  public void setUp() throws Exception {
    PowerMockito.mockStatic(DatabaseDriver.class);
    ArrayList<ArrayList<String>> feedbacks= new ArrayList<ArrayList<String>>();
    ArrayList<String> row = new ArrayList<String>();
    row.add("email");
    row.add("name");
    row.add("feedback");
    row.add("time");
    feedbacks.add(row);
    PowerMockito.when(DatabaseDriver.getFeedBack()).thenReturn(feedbacks);
  }

  @PrepareForTest({ DatabaseDriver.class })
  @Test
  public void testviewAllFeedback() {
    ArrayList<Feedback> result = AdminDao.viewAllFeedback();
    assertEquals("email", result.get(0).getEmail());
    assertEquals("name", result.get(0).getName());
    assertEquals("feedback", result.get(0).getFeedback());
    assertEquals("time", result.get(0).getTime());
  }

}
