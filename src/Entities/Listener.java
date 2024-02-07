package Entities;
import Abstracts.User;

public class Listener extends User {

  public Listener(String username, String password) {
    super(username, password);
  }

  @Override
  public UserRole getRole() {
    return UserRole.Artist;
  }
  
}
