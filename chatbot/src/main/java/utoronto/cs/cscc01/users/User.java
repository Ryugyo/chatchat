package utoronto.cs.cscc01.users;

public abstract class User {

  private String name;
  private int age;
  private String emailAdder;

  private boolean isAuthenticated;

  public User (String name, int age, String email, boolean Authenticate) {
    
    this.name = name;
    this.age = age;
    this.emailAdder = email;
    this.isAuthenticated = Authenticate;
  }


  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return this.age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public boolean isAuthenticated() {
    return isAuthenticated;
  }

  public void setAuthenticated() {
    this.isAuthenticated = true;
  }

  public String getEmailAdder() {
    return this.emailAdder;
  }

}
