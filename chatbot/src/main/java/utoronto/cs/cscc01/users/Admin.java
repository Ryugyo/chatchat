package utoronto.cs.cscc01.users;

public class Admin{

  String name;
  String email;
  int age;
  String password;
  

  public Admin(String name, int age, String email, String password) {
    this.name = name;
//    this.age = age;
    this.email = email;
    this.password = password;
    //need to have a database to store roles id to classify different type of user
    //this.setEntityId(0);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getPassword() {
    return password;
  }


}
