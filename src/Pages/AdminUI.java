package Pages;

import java.util.Arrays;
import java.util.List;

import Abstracts.DetailedUser;
import Abstracts.User;
import Entities.Admin;
import Entities.Playlist;
import Entities.Song;
import Enums.UserRole;
import Managers.PlaylistManager;
import Managers.SongManager;
import Managers.UserManager;
import Services.CommonService;
import Services.InputReaderService;
import Services.OutputService;

public class AdminUI {
  static boolean stopInnerMenu = false;

  public static List<String> getOptions() {
    stopInnerMenu = false;
    List<String> options = Arrays.asList(
      "1. Show User Actions",
      "2. Show Song Actions"
    );
    return options;
  }
  
  public static List<Runnable> getActions() {
    List<Runnable> actions = Arrays.asList(
      () -> showUserActions(),
      () -> showSongActions()
    );
    return actions;
  }

  // ------------ User Actions ------------------

  public static void showUserActions() {
    stopInnerMenu = false;
    List<String> options = Arrays.asList(
      "1. Print Users",
      "2. Remove a User",
      "3. Ban a Users",
      "4. Un-Ban a Users",
      "5. Add a Users",
      "6. <- Go Back"
    );
    List<Runnable> actions = Arrays.asList(
      () -> printUsers(),
      () -> removeUser(getUserId("Enter user ID you mind to remove: ")),
      () -> banUser(getUserId("Enter user ID you mind to un-ban: ")),
      () -> unBanUser(getUserId("Enter user ID you mind to ban: ")),
      () -> addUser(),
      () -> { stopInnerMenu = true; }
    );
    while (!stopInnerMenu) {
      CommonService.menuHandler(options, actions);
    }
  }

  public static void printUsers() {
    stopInnerMenu = false;
    System.out.println("With what filters you like to sort?");
    List<String> options = Arrays.asList(
      "1. Sort Alphabetically",
      "2. Print User By ID",
      "3. Search Users By Name",
      "4. Sort By Role (Artists First)",
      "5. Most Active Listeners",
      "6. Most Listened Artist",
      "7. Only Banned Users",
      "8. <- Go Back"
    );
    List<Runnable> actions = Arrays.asList(
      () -> OutputService.printUsers(UserManager.getUsersSortedAlphabetically(), true),
      () -> OutputService.printUsers(Arrays.asList(UserManager.getUserById(getUserId("Enter user ID to print: "))), true),
      () -> OutputService.printUsers(UserManager.filterUsersByName((InputReaderService.getString("Enter user name to search: ", null))), true),
      () -> sortUsersByRole(),
      () -> OutputService.printDetailedUsers(UserManager.getUsersSortedByPlayTime(UserRole.Listener)),
      () -> OutputService.printDetailedUsers(UserManager.getUsersSortedByPlayTime(UserRole.Artist)),
      () -> OutputService.printUsers(UserManager.getBannedUsers(), true),
      () -> { stopInnerMenu = true; }
    );
    while (!stopInnerMenu) {
      CommonService.menuHandler(options, actions);
    }
  }

  public static void sortUsersByRole() {
    List<User> filteredUsers = UserManager.getUserByRole(UserRole.Artist);
    filteredUsers.addAll(UserManager.getUserByRole(UserRole.Listener));
    OutputService.printUsers(filteredUsers, true);
  }

  public static void removeUser(int id) {
    User user = UserManager.getUserById(id);
    if (user instanceof Admin) {
      System.out.println("Can not ban an admin!");
    } else {
      String sure = InputReaderService.getString("Are you sure you want to remove this user? (y/n): ", Arrays.asList("y", "n"));
      if (sure.equals("y")) {
        System.out.println("User has been removed!");
        UserManager.removeUserById(id);
      } else {
        System.out.println("User removal canceled!");
      }
    }
  }

  public static void banUser(int id) {
    User user = UserManager.getUserById(id);
    if (user instanceof Admin) {
      System.out.println("Can not ban an admin!");
    } else if (user instanceof DetailedUser) {
      DetailedUser detailedUser = (DetailedUser)user;
      if (detailedUser.isBanned()) {
        System.out.println("User is already banned!");
      } else {
        detailedUser.setBanned(true);
        System.out.println("User has been banned!");
      }
    }
  }

  public static void unBanUser(int id) {
    User user = UserManager.getUserById(id);
    if (user instanceof DetailedUser) {
      DetailedUser detailedUser = (DetailedUser)user;
      if (detailedUser.isBanned()) {
        detailedUser.setBanned(false);
        System.out.println("User un-banned!");
      } else {
        System.out.println("User is not banned!");
      }
    }
  }

  public static void addUser() {
    DetailedUser newUser = OutputService.signupWizard();
    System.out.println(newUser.getFullname() + " successfully created!");
  }

  public static int getUserId(String message) {
    List<Object> possibleIds = CommonService.getAllIdsOfEntity(UserManager.getAllUsers());
    int userId = Integer.parseInt(InputReaderService.getString(message, possibleIds));
    return userId;
  }

  // ------------ Song Actions ------------------

  public static void showSongActions() {
    stopInnerMenu = false;
    List<String> options = Arrays.asList(
      "1. Print Songs",
      "2. Print Most Famous Songs",
      "3. Print Playlists",
      "4. Print Most Famous Playlists",
      "5. Ban a Song",
      "6. Ban a Playlist",
      "7. Un-Ban a Song",
      "8. Un-Ban a Playlist",
      "9. <- Go Back"
    );
    List<Runnable> actions = Arrays.asList(
      () -> OutputService.printSongs(SongManager.getAllSongs(), true),
      () -> OutputService.printSongs(SongManager.sortSongsByPlayCount(false), true),
      () -> OutputService.printPlaylists(PlaylistManager.getAllPlaylists(), true),
      () -> OutputService.printPlaylists(PlaylistManager.sortPlayListsByPlayCount(false), true),
      () -> banSong(getSongId("Enter song ID you mind to ban: "), true),
      () -> banPlayList(getPlaylistId("Enter playlist ID you mind to ban: "), false),
      () -> banSong(getSongId("Enter song ID you mind to ban: "), true),
      () -> banPlayList(getPlaylistId("Enter playlist ID you mind to ban: "), false),
      () -> { stopInnerMenu = true; }
    );
    while (!stopInnerMenu) {
      CommonService.menuHandler(options, actions);
    }
  }

  public static int getSongId(String message) {
    List<Object> possibleIds = CommonService.getAllIdsOfEntity(SongManager.getAllSongs());
    int songId = Integer.parseInt(InputReaderService.getString(message, possibleIds));
    return songId;
  }

  public static int getPlaylistId(String message) {
    List<Object> possibleIds = CommonService.getAllIdsOfEntity(PlaylistManager.getAllPlaylists());
    int playlistId = Integer.parseInt(InputReaderService.getString(message, possibleIds));
    return playlistId;
  }

  public static void banSong(int id, boolean ban) {
    Song song = SongManager.getSongById(id);
    if (song.isBanned()) {
      if (ban) {
        System.out.println("Song is already banned!");
      } else {
        song.setBanned(false);
        System.out.println("Song has been un-banned!");
      }
    } else {
      if (ban) {
        song.setBanned(true);
        System.out.println("Song has been banned!");
      } else {
        System.out.println("Song is not banned!");
      }
    }
  }

  public static void banPlayList(int id, boolean ban) {
    Playlist playlist = PlaylistManager.getPlaylistById(id);
    if (playlist.isBanned()) {
      if (ban) {
        System.out.println("PlayList is already banned!");
      } else {
        playlist.setBanned(false);
        System.out.println("PlayList has been un-banned!");
      }
    } else {
      if (ban) {
        playlist.setBanned(true);
        System.out.println("PlayList has been banned!");
      } else {
        System.out.println("PlayList is not banned!");
      }
    }
  }

}
