package Entities;
import Abstracts.DetailedUser;

public class Artist extends DetailedUser {
  private String nickname;

  public Artist(String fullname, String username, String password, String nickname) {
    super(fullname, username, password);
  }

  public String getNickname() {
    return nickname;
  }
  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  @Override
  public UserRole getRole() {
    return UserRole.Artist;
  }
  
}
