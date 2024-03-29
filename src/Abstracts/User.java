package Abstracts;

import Enums.UserRole;

public abstract class User extends CommonProperties {
  private String fullname;
  private String username;
  private String password;
  
  public User(String fullname, String username, String password) {
    super();
    this.fullname = fullname;
    this.username = username;
    this.password = password;
  }

  public String getFullname() {
    return fullname;
  }
  
  public void setFullname(String fullname) {
    this.fullname = fullname;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public abstract UserRole getRole();
}
