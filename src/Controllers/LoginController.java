package Controllers;
import Abstracts.User;
import Managers.UserManager;
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
        User user = UserManager.validateCredentials(username, password);
        AuthService.login(user);
      } catch (Exception e) {
        System.out.println(e.getMessage());
        System.out.println("Try again...");
      }
      run(callback);
    }
  }

}
