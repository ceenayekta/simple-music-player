package Pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Abstracts.DetailedUser;
import Entities.Artist;
import Entities.Listener;
import Entities.Song;
import Managers.PlaylistManager;
import Managers.SongManager;
import Managers.UserManager;
import Services.AuthService;
import Services.CommonService;
import Services.InputReaderService;
import Services.OutputService;
import Services.SongPlayerService;

public class ListenerUI {
  static boolean stopInnerMenu = false;
  private static Listener listener = (Listener) AuthService.getUser();

  public static List<String> getOptions() {
    stopInnerMenu = false;
    List<String> options = Arrays.asList(
      "1. Show Profile",
      "2. Edit Details",
      "3. Print Followers",
      "4. Print Followings",
      "5. Search",
      "6. Show Playlist Actions"
    );
    return options;
  }
  
  public static List<Runnable> getActions() {
    List<Runnable> actions = Arrays.asList(
      () -> OutputService.printProfile(listener),
      () -> showEditActions(),
      () -> OutputService.printDetailedUsers(listener.getFollowers()),
      () -> OutputService.printDetailedUsers(listener.getFollowings()),
      () -> showSearchActions(),
      () -> showPlaylistActions()
    );
    return actions;
  }

  public static int getUserId(String message) {
    List<Object> possibleIds = CommonService.getAllIdsOfEntity(UserManager.getNotBlockedUsers());
    int userId = Integer.parseInt(InputReaderService.getString(message, possibleIds));
    return userId;
  }

  // ------------ Edit Actions ------------------
  
  public static void showEditActions() {
    stopInnerMenu = false;
    List<String> options = new ArrayList<>(EditDetailActionsUI.getOptions());
    List<Runnable> actions = new ArrayList<>(EditDetailActionsUI.getActions());
    options.add("" + (options.size() + 1) + ". <- Go Back");
    actions.add(() -> stopInnerMenu = true);
    while (!stopInnerMenu) {
      CommonService.menuHandler(options, actions);
    }
  }

  // ------------ Playlist Actions ------------------

  public static void showPlaylistActions() {
    stopInnerMenu = false;
    List<String> options = new ArrayList<>(PlaylistActionsUI.getOptions());
    List<Runnable> actions = new ArrayList<>(PlaylistActionsUI.getActions());
    options.add("" + (options.size() + 1) + ". <- Go Back");
    actions.add(() -> stopInnerMenu = true);
    while (!stopInnerMenu) {
      CommonService.menuHandler(options, actions);
    }
  }

  // ------------ Search Actions ------------------

  public static void showSearchActions() {
    stopInnerMenu = false;
    List<String> options = Arrays.asList(
      "1. Search in Songs by Name",
      "2. Search in Songs by Category",
      "3. Search in Artists and Listeners by Name",
      "4. View an Artist's or a Listener's Profile",
      "5. <- Go Back"
    );
    List<Runnable> actions = Arrays.asList(
      () -> OutputService.printSongs(SongManager.filterSongsByName(InputReaderService.getString("Enter text to search songs by: ", null), true), false),
      () -> OutputService.printSongs(SongManager.filterSongsByCategory(OutputService.getCategory("Enter category to search songs by: "), true), false),
      () -> OutputService.printDetailedUsers(UserManager.getDetailedUsers(UserManager.filterUsersByName(InputReaderService.getString("Enter category to search artists and listeners by: ", null)))),
      () -> showProfileActions(getUserId("Enter user ID to visit: ")),
      () -> { stopInnerMenu = true; }
    );
    while (!stopInnerMenu) {
      CommonService.menuHandler(options, actions);
    }
  }

  // ------------ User Profile Actions ------------------

  public static void showProfileActions(int userId) {
    stopInnerMenu = false;
    DetailedUser detailedUser = (DetailedUser) UserManager.getUserById(userId);
    boolean isAlreadyFollowed = listener.hasFollowedTheUser(userId);
    boolean isAlreadyBlocked = listener.hasBlockedTheUser(userId);
    
    List<String> options = Arrays.asList(
      "1. Print Playlists",
      "2. Print Songs",
      "3. Print Most Played Song",
      isAlreadyFollowed ? "4. Unfollow" : "4. Follow",
      isAlreadyBlocked ? "5. Unblock" : "5. Block",
      "7. <- Go Back"
    );
    List<Runnable> actions = Arrays.asList(
      () -> OutputService.printPlaylists(PlaylistManager.getActivePlaylistsOfUser(userId), false),
      () -> OutputService.printSongs(SongManager.getActiveSongsOfArtist(userId), false),
      () -> OutputService.printSong(detailedUser.getMostPlayedSong()),
      () -> handleFollow(detailedUser, !isAlreadyFollowed),
      () -> handleBlock(detailedUser, !isAlreadyBlocked),
      () -> { stopInnerMenu = true; }
    );
    while (!stopInnerMenu) {
      CommonService.menuHandler(options, actions);
    }
  }

  public static void handleFollow(DetailedUser detailedUser, boolean follow) {
    if (follow) {
      listener.follow(detailedUser);
    } else {
      listener.unfollow(detailedUser);
    }
    System.out.println((detailedUser instanceof Artist ? ((Artist)detailedUser).getNickname() : detailedUser.getFullname()) + (follow ? " followed" : " unfollowed") + " successfully!");
  }

  public static void handleBlock(DetailedUser detailedUser, boolean block) {
    if (block) {
      listener.block(detailedUser);
    } else {
      listener.unblock(detailedUser);
    }
    System.out.println((detailedUser instanceof Artist ? ((Artist)detailedUser).getNickname() : detailedUser.getFullname()) + (block ? " blocked" : " unblocked") + " successfully!");
  }

  // ------------ Song Actions ------------------
  
  public static void listenToPlaylist(List<Song> songs) {
    SongPlayerService.stop();
    SongPlayerService.init(songs);
    for (Song song : songs) {
      OutputService.printSong(song);
      SongPlayerService.listenToSong(song);
      
    }
    
  }

}
