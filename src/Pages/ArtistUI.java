package Pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Entities.Artist;
import Entities.Playlist;
import Entities.Song;
import Managers.PlaylistManager;
import Managers.SongManager;
import Services.AuthService;
import Services.CommonService;
import Services.InputReaderService;
import Services.OutputService;


public class ArtistUI {
  static boolean stopInnerMenu = false;
  private static Artist artist = (Artist) AuthService.getUser();

  public static List<String> getOptions() {
    stopInnerMenu = false;
    List<String> options = Arrays.asList(
      "1. Show Profile",
      "2. Edit Details",
      "3. Show Song Actions",
      "4. Show Playlist Actions"
    );
    return options;
  }
  
  public static List<Runnable> getActions() {
    List<Runnable> actions = Arrays.asList(
      () -> OutputService.printProfile(artist),
      () -> showEditActions(),
      () -> showSongActions(),
      () -> showPlaylistActions()
    );
    return actions;
  }

  // ------------ Edit Actions ------------------

  public static void showEditActions() {
    stopInnerMenu = false;
    List<String> options = new ArrayList<>(EditDetailActionsUI.getOptions());
    List<Runnable> actions = new ArrayList<>(EditDetailActionsUI.getActions());
    options.add("" + (options.size() + 1) + ". Edit Nickname");
    options.add("" + (options.size() + 1) + ". <- Go Back");
    actions.add(() -> artist.setNickname(InputReaderService.getString("Enter your new nickname: ", null)));
    actions.add(() -> stopInnerMenu = true);
    while (!stopInnerMenu) {
      CommonService.menuHandler(options, actions);
    }
  }

  public static int getSongId(String message) {
    List<Object> possibleIds = CommonService.getAllIdsOfEntity(artist.getAllSongs());
    int songId = Integer.parseInt(InputReaderService.getString(message, possibleIds));
    return songId;
  }

  public static int getPlaylistId(String message) {
    List<Object> possibleIds = CommonService.getAllIdsOfEntity(artist.getPlaylists());
    int playlistId = Integer.parseInt(InputReaderService.getString(message, possibleIds));
    return playlistId;
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

  // ------------ Song Actions ------------------

  public static void showSongActions() {
    stopInnerMenu = false;
    List<String> options = Arrays.asList(
      "1. Show Top Song",
      "2. Show Most Played Song",
      "3. Show All Songs",
      "4. Show Single Track Songs",
      "5. Move a Song To Playlist",
      "6. Import a Song",
      "7. Delete a Song",
      "8. Change Song Priority in Album",
      "9. <- Go Back"
    );
    List<Runnable> actions = Arrays.asList(
      () -> OutputService.printSongs(SongManager.getActiveSongsOfArtist(artist.getId()), true),
      () -> OutputService.printSong(artist.getMostPlayedSong()),
      () -> OutputService.printSongs(artist.getAllSongs(), true),
      () -> OutputService.printSongs(artist.getSingleTrackSongs(), true),
      () -> addSonToPlaylist(getPlaylistId("Enter playlist id you want to add song to: "), getSongId("Enter song id you want to add: ")),
      () -> OutputService.songCreationWizard(),
      () -> deleteSong(getSongId("Enter song id you want to delete: ")),
      () -> changeSongPriority(getSongId("Enter song id you want to change priority: ")),
      () -> { stopInnerMenu = true; }
    );
    while (!stopInnerMenu) {
      CommonService.menuHandler(options, actions);
    }
  }

  public static void addSonToPlaylist(int playlistId, int songId) {
    Song song = SongManager.getSongById(songId);
    Playlist playlist = PlaylistManager.getPlaylistById(playlistId);
    if (song == null || playlist == null) {
      System.out.println("Playlist or Song not found.");
    } else {
      song.setAlbum(playlist);
      System.out.println("Song " + song.getName() + " added to playlist " + playlist.getName());
    }
  }

  public static void deleteSong(int song) {
    Song foundSong = SongManager.getSongById(song);
    if (foundSong == null) {
      System.out.println("Song not found.");
    } else {
      OutputService.printSong(foundSong);
      String confirm = InputReaderService.getString("Are you sure you want to remove this song? (y/n): ", Arrays.asList("y", "n"));
      if (confirm.equals("y")) {
        SongManager.removeSong(foundSong);
      } else {
        System.out.println("Deleting cancelled!");
      }
    }
  }

  public static void changeSongPriority(int songId) {
    Song song = SongManager.getSongById(songId);
    if (song.getAlbum() == null) {
      System.out.println("This song is single track!");
    } else {
      int priority = InputReaderService.getInteger("Enter new song priority: ");
      song.getAlbum().changeSongPriority(song, priority);
    }
  }
  
}