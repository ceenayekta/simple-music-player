package Services;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Abstracts.DetailedUser;
import Abstracts.User;
import Entities.Artist;
import Entities.Playlist;
import Entities.Song;
import Enums.Category;
import Managers.PlaylistManager;
import Managers.UserManager;

public class OutputService {
  // --------------- prints ------------------

  public static void printTable(List<String> labels, List<List<Object>> data) {
    for (String label : labels) {
        System.out.printf("%-15s", label);
    }
    System.out.println();
    for (int i = 0; i < labels.size(); i++) {
      System.out.printf("%-15s", "---------------");
    }
    System.out.println();
    for (int i = 0; i < labels.size(); i++) {
      List<Object> row = data.get(i);
      for (Object cell : row) {
          System.out.printf("%-15s", cell);
      }
      System.out.println();
    }
    if (data.size() == 0) {
      System.out.printf("%-" + labels.size()*15 + "s", "No Data to Show!");
    }
    for (int i = 0; i < labels.size(); i++) {
      System.out.printf("%-15s", "---------------");
    }
    System.out.println();
  }

  public static void printSongs(List<Song> songs, boolean showIsBanned) {
    List<String> labels = Arrays.asList("ID", "Name", "Duration", "Played Count", "Category", "Album", "Artist", "Published At");
    List<List<Object>> data = CommonService.map2D(songs, (s) -> Arrays.asList(
      s.getId(),
      s.getName(),
      s.getDuration(),
      s.getPlayCount(),
      s.getCategory(),
      s.getAlbum() == null ? "(Single Track)" : s.getAlbum().getName(),
      s.getArtist().getNickname(),
      s.getPublishedAt() == null ? "(Not Published)" : CommonService.shortDateFormat(s.getPublishedAt()),
      s.isBanned() ? "Yes" : "No"
    ));
    if (showIsBanned) labels.add("Is Banned");
    printTable(labels, data);
  }

  public static void printPlaylists(List<Playlist> playlists, boolean showIsBanned) {
    List<String> labels = Arrays.asList("ID", "Name", "Overall Played Count", "Songs", "Duration", "Creator", "Published At");
    List<List<Object>> data = CommonService.map2D(playlists, (p) -> Arrays.asList(
      p.getId(),
      p.getName(),
      p.getOverallPlayedCount(),
      p.setSongsCount(),
      p.getTotalDuration(),
      p.getOwner() instanceof Artist ? ((Artist) p.getOwner()).getNickname() : p.getOwner().getFullname(),
      p.getPublishedAt() == null ? "(Not Published)" : CommonService.shortDateFormat(p.getPublishedAt()),
      p.isBanned() ? "Yes" : "No"
    ));
    if (showIsBanned) labels.add("Is Banned");
    printTable(labels, data);
  }

  public static void printUsers(List<User> users, boolean showIsBanned) {
    List<String> labels = Arrays.asList("ID", "Username", "Fullname", "Role", "Joined At", "Is Banned");
    List<List<Object>> data = CommonService.map2D(users, (u) -> Arrays.asList(
      u.getId(),
      u.getUsername(),
      u.getFullname(),
      u.getRole().toString(),
      CommonService.shortDateFormat(u.getCreatedAt()),
      u instanceof DetailedUser ? ((DetailedUser) u).isBanned() ? "Yes" : "No" : "No"
    ));
    if (showIsBanned) labels.add("Is Banned");
    printTable(labels, data);
  }

  public static void printDetailedUsers(List<DetailedUser> detailedUser) {
    List<String> labels = Arrays.asList("ID", "Nickname", "Playlists", "Songs", "Followers", "Following", "Joined At");
    List<List<Object>> data = CommonService.map2D(detailedUser, (d) -> Arrays.asList(
      d.getId(),
      d instanceof Artist ? ((Artist) d).getNickname() : d.getFullname(),
      d.getPlaylistsCount(),
      d.getSongsCount(),
      d.getFollowersCount(),
      d.getFollowingsCount(),
      CommonService.shortDateFormat(d.getCreatedAt())
    ));
    printTable(labels, data);
  }

  public static String printCategories() {
    CommonService.printOptions(Arrays.stream(Category.values()).map(c -> c.toString()).toList());
    return InputReaderService.getString("Give your song a category: ", CommonService.createArrayOf(Arrays.asList(Category.values()).size()));
  }

  // --------------- wizards ----------------
  
  public static DetailedUser signupWizard() {
    DetailedUser user = null;
    String isArtist = InputReaderService.getString("Are you Artist or Listener? (a/l) ", Arrays.asList("a", "l"));
    String fullName = InputReaderService.getString("Enter your full name: ", null);
    String username = InputReaderService.getString("Enter your username: ", null);
    String bio = InputReaderService.getString("Tell us a short bio about " + fullName + ": ", null);
    while (UserManager.getUserByUsername(username) != null) {
      System.out.println("This username is already taken.");
      username = InputReaderService.getString("Please choose another one: ", null);
    }
    String password = InputReaderService.getString("Enter your password: ", null);
    if (isArtist.equals("a")) {
      String nickname = InputReaderService.getString("Enter your nickname: ", null);
      user = UserManager.createUser(fullName, username, password, bio, nickname);
    } else {
      user = UserManager.createUser(fullName, username, password, bio);
    }
    return user;
  }
  
  public static Playlist playlistCreationWizard() {
    User owner = AuthService.getUser();
    List<Song> songs = new ArrayList<>();
    String name = InputReaderService.getString("Enter a name for you playlist: ", null);
    String description = InputReaderService.getString("(Optional) Add a description for " + name + ": ", null);
    if (owner instanceof Artist) {
      String choice = InputReaderService.getString("Would you like to add any of your single tracks to this playlist? (y/n) ", Arrays.asList("y", "n"));
      if (choice.equals("y")) {
        List<Song> singleTracks = ((Artist)owner).getSingleTrackSongs();
        printSongs(singleTracks, true);
        String inputIds = InputReaderService.getString("Enter each ID of tracks would you want to add? (separated by comma ex. 1, 2, 3): ", null);
        List<String> ids = Arrays.stream(inputIds.split(",")).map(id -> id.trim()).toList();
        List<Song> filteredTracks = SongService.filterSongsByIds(singleTracks, ids);
        songs.addAll(filteredTracks);
      }
    }
    Playlist playlist = new Playlist(name, description, owner, songs);
    return playlist;
  }
  
  public static Song songCreationWizard() {
    User artist = AuthService.getUser();
    if (artist instanceof Artist) {
      File file = null;
      String name = InputReaderService.getString("Enter a name for you song: ", null);
      Category category = getCategory();
      Playlist album = getAlbum(((Artist)artist).getPlaylists());
      try {
        file = FileService.importSongFile(name);
      } catch (Exception e) {
        System.out.println("Failed to import song file. " + e.getMessage());
        return null;
      }
      Song song = new Song(name, album, (Artist)artist, file, category);
      return song;
    } else {
      System.out.println("Only artists can create songs.");
    }
    return null;
  }

  public static Category getCategory() {
    String category = printCategories();
    return Category.valueOf(category);
  }

  public static Playlist getAlbum(List<Playlist> albums) {
    printPlaylists(albums, true);
    String id = InputReaderService.getString("Which album would you like to add this song to? (Enter ID): ", CommonService.getAllIdsOfEntity(albums));
    return PlaylistManager.getPlaylistById(Integer.parseInt(id));
  }
}
