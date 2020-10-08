package utoronto.cs.cscc01.database;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import utoronto.cs.cscc01.security.PasswordHelpers;
import utoronto.cs.cscc01.users.Admin;
import org.powermock.core.classloader.annotations.PrepareForTest;


@RunWith( PowerMockRunner.class )
public class AuthenticateAdminTest {
  
  @Mock
  Admin mockAdmin;

  @Before
  public void setUpLoginAdmin() throws Exception {
    PowerMockito.mockStatic(PasswordHelpers.class);
    PowerMockito.mockStatic(AdminDao.class);
    mockAdmin = new Admin("Lily", 18, "hina@chatchat.com", "Nngw/jIcZfjlTS+Rmr1kGFy/9/N2I6Wc");
    PowerMockito.when(AdminDao.getAdmin("hina@chatchat.com")).thenReturn(mockAdmin);
    PowerMockito.when(PasswordHelpers.comparePassword(mockAdmin.getPassword(), "password")).thenReturn(true);
  }

  @PrepareForTest({ AdminDao.class, PasswordHelpers.class, AuthenticateUser.class, DatabaseDriver.class })
  @Test
  public void testAdminLoginSuccess() {
    //AdminDao temp = new AdminDao();
    boolean result = AuthenticateUser.loginAdmin("hina@chatchat.com", "password");
    assertEquals(true, result);
  }

  @PrepareForTest({ AdminDao.class, PasswordHelpers.class, AuthenticateUser.class, DatabaseDriver.class })
  @Test
  public void testAdminLoginFail() {
    //AdminDao temp = new AdminDao();
    boolean result = AuthenticateUser.loginAdmin("hina@chatchat.com", "notpassword");
    assertEquals(false, result);
  }
}
