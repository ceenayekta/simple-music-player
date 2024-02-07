package Entities;
import Abstracts.User;

public class Admin extends User {
  
  public Admin(String username, String password) {
    super(username, password);
  }

  @Override
  public UserRole getRole() {
    return UserRole.Admin;
  }
}
