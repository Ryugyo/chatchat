package utoronto.cs.cscc01.database;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;


@RunWith(PowerMockRunner.class)
@PrepareForTest( DatabaseDriver.class )
public class dbDriverPrivateMethodTest {

  
  @Mock
  ResultSet mockResultSet;
  
  @Mock
  ResultSetMetaData mockMetaData;
  
  @Before
  public void setUp() throws Exception {
    when(mockResultSet.getString(1)).thenReturn("Apartment");
    when(mockResultSet.getString(2)).thenReturn("$1,120.00");
    when(mockResultSet.getString(3)).thenReturn("112 highway");
    when(mockResultSet.getString(4)).thenReturn("bedroom 1");
    when(mockResultSet.getString(5)).thenReturn("www.kijiji.com/c123456");
    when(mockResultSet.getMetaData()).thenReturn(mockMetaData);
    when(mockMetaData.getColumnCount()).thenReturn(5);
    when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
  }

  @Test
  public void rsToListTest(){
    try {
      //DatabaseDriver newdb = new DatabaseDriver();
      ArrayList<ArrayList<String>> temp = Whitebox.invokeMethod(DatabaseDriver.class, "rsToList", mockResultSet);
      assertEquals(temp.get(0).get(0), "Apartment");
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}
