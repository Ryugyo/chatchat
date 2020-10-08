package utoronto.cs.cscc01.security;

import org.jasypt.util.text.BasicTextEncryptor;

public class PasswordHelpers {
  
  //the following method will encrypted the given psw
  public static String pswEncryptor(String psw) {
    BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
    textEncryptor.setPasswordCharArray("xxuuydsj87XYZWE".toCharArray());
    String myEncryptedPSW = textEncryptor.encrypt(psw);
    return myEncryptedPSW;
  }
  
  //compare the password with encrypted password in database;
  public static boolean comparePassword(String dbPassword, String enteredPassword) {
    BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
    textEncryptor.setPasswordCharArray("xxuuydsj87XYZWE".toCharArray());
    String myDecryptedPSW = textEncryptor.decrypt(dbPassword);
    return myDecryptedPSW.equals(enteredPassword);
  }
  
}
