package Entities;
import Abstracts.User;

public class Artist extends User {

  public Artist(String username, String password) {
    super(username, password);
  }

  @Override
  public UserRole getRole() {
    return UserRole.Artist;
  }
  
}
