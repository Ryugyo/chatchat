package utoronto.cs.cscc01.database;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import utoronto.cs.cscc01.security.PasswordHelpers;
import org.powermock.core.classloader.annotations.PrepareForTest;


@RunWith( PowerMockRunner.class )
public class AuthenticateClientTest {

  
  @Before
  public void setUp() throws Exception {
    // mock the static method
    PowerMockito.mockStatic(DatabaseDriver.class);
    PowerMockito.mockStatic(PasswordHelpers.class);
    PowerMockito.when(DatabaseDriver.getPassword("xxx@chatchat.com")).thenReturn("yin/Sm6F6p1CbPOA0heSew==");
    PowerMockito.when(PasswordHelpers.comparePassword(DatabaseDriver.getPassword("xxx@chatchat.com"), "psw")).thenReturn(true);
  }


  @PrepareForTest({ DatabaseDriver.class, AuthenticateUser.class, PasswordHelpers.class })
  @Test
  public void testClientLoginSuccess() {
    boolean result = AuthenticateUser.loginClient("xxx@chatchat.com", "psw");
    assertEquals(true, result);
  }
  
  @PrepareForTest({ DatabaseDriver.class, AuthenticateUser.class, PasswordHelpers.class })
  @Test
  public void testClientLoginFalis() {
    boolean result = AuthenticateUser.loginClient("xxx@chatchat.com", "notpsw");
    assertEquals(false, result);
  }

}
