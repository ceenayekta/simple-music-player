package Abstracts;

public abstract class User extends CommonProperties {
  public enum UserRole {
    Admin,
    Artist,
    Listener,
  }

  private String username;
  private String password;
  private boolean isActive;
  
  public User(String username, String password) {
    super();
    this.username = username;
    this.password = password;
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

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean isActive) {
    this.isActive = isActive;
  }

  public abstract UserRole getRole();
}
