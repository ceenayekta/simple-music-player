package Controllers;
import Abstracts.User;
import Services.AuthService;
import Services.InputReaderService;

public class LoginController {
  
  public static void run(Runnable callback) {
    if (AuthService.isLoggedIn()) {
      callback.run();
    } else {
      String username = InputReaderService.getString("Enter username: ", null);
      String password = InputReaderService.getString("Enter password: ", null);
      try {
        User user = AuthService.validateCredentials(username, password);
        AuthService.login(user);
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
      run(callback);
    }
  }

}
