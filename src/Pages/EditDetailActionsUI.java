package Pages;

import java.util.Arrays;
import java.util.List;

import Abstracts.DetailedUser;
import Services.AuthService;
import Services.InputReaderService;

public class EditDetailActionsUI {
  private static DetailedUser detailedUser = (DetailedUser) AuthService.getUser();

  public static List<String> getOptions() {
    List<String> options = Arrays.asList(
      "1. Edit Username",
      "2. Edit Password",
      "3. Edit Bio"
    );
    return options;
  }
  
  public static List<Runnable> getActions() {
    List<Runnable> actions = Arrays.asList(
      () -> updateUsername(),
      () -> updatePassword(),
      () -> detailedUser.setBio(InputReaderService.getString("Enter you new bio: ", null))
    );
    return actions;
  }

  public static void updateUsername() {
    try {
      String newUserName = InputReaderService.getString("Enter your new username: ", null);
      AuthService.updateUsername(detailedUser, newUserName);
      System.out.println("Username has been updated");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public static void updatePassword() {
    try {
      String currentPassword = InputReaderService.getString("Enter current password: ", null);
      String newPassword = InputReaderService.getString("Enter new password: ", null);
      String confirmPassword = InputReaderService.getString("Confirm your new password: ", null);
      if (confirmPassword.equals(newPassword)) {
        AuthService.updatePassword(detailedUser, currentPassword, newPassword);
        System.out.println("Password has been updated");
      } else {
        System.out.println("Confirm did not match. try");
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
