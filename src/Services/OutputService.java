package Services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Abstracts.User;
import Entities.Artist;
import Entities.Playlist;
import Entities.Song;
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
    for (List<Object> row : data) {
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

  public static void printSongs(List<Song> songs) {
    // List<String> labels = Arrays.asList("ID", "Fullname", "Birthday", "Username");
    // List<List<Object>> data = CommonService.map2D(songs, (s) -> Arrays.asList(s.getId(), s.getName(), s.));
    // printTable(labels, data);
  }

  // --------------- wizards ----------------
  
  public static User signupWizard() {
    User user = null;
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
  
  public static Playlist playlistWizard() {
    User owner = AuthService.getUser();
    List<Song> songs = new ArrayList<>();
    String name = InputReaderService.getString("Enter a name for you playlist: ", null);
    String description = InputReaderService.getString("(Optional) Add a description for " + name + ": ", null);
    if (owner instanceof Artist) {
      String choice = InputReaderService.getString("Would you like to add any of your single tracks to this playlist? (y/n) ", Arrays.asList("y", "n"));
      if (choice.equals("y")) {
        List<Song> singleTracks = ((Artist)owner).getSingleTrackSongs();
        printSongs(singleTracks);
        String inputIds = InputReaderService.getString("Enter each ID of tracks you want to add (separated by comma ex. 1, 2, 3): ", null);
        List<String> ids = Arrays.stream(inputIds.split(",")).map(id -> id.trim()).toList();
        List<Song> filteredTracks = SongService.filterSongsByIds(singleTracks, ids);
        songs.addAll(filteredTracks);
      }
    }
    Playlist playlist = new Playlist(name, description, owner, songs);
    return playlist;
  }
}
