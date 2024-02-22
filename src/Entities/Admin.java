package Entities;
import Abstracts.User;
import Enums.UserRole;

public class Admin extends User {
  
  public Admin(String fullname, String username, String password) {
    super(fullname, username, password);
  }

  @Override
  public UserRole getRole() {
    return UserRole.Admin;
  }
}
