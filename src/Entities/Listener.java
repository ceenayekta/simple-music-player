package Entities;
import Abstracts.DetailedUser;

public class Listener extends DetailedUser {

  public Listener(String fullname, String username, String password) {
    super(fullname, username, password);
  }

  @Override
  public UserRole getRole() {
    return UserRole.Artist;
  }
  
}
