package Pages;

import java.util.Arrays;
import java.util.List;

import Abstracts.DetailedUser;
import Entities.Artist;
import Entities.Playlist;
import Entities.Song;
import Managers.PlaylistManager;
import Managers.SongManager;
import Services.AuthService;
import Services.CommonService;
import Services.InputReaderService;
import Services.OutputService;

public class PlaylistActionsUI {
  private static DetailedUser detailedUser = (DetailedUser) AuthService.getUser();

  public static List<String> getOptions() {
    List<String> options = Arrays.asList(
      "1. Show Top PlayLists",
      "2. Show All PlayLists",
      "3. Change a Playlist Name",
      "4. Change a Playlist Description",
      "5. Add a PlayList",
      "6. Private a PlayList",
      "7. Public a PlayList",
      "8. Delete a PlayList"
    );
    return options;
  }
  
  public static List<Runnable> getActions() {
    List<Runnable> actions = Arrays.asList(
      () -> OutputService.printPlaylists(PlaylistManager.getActivePlaylistsOfUser(detailedUser.getId()), true),
      () -> OutputService.printPlaylists(detailedUser.getPlaylists(), true),
      () -> changePlaylistName(getPlaylistId("Enter playlist id you want to set new name to: ")),
      () -> changePlaylistDescription(getPlaylistId("Enter playlist id you want to set new description to: ")),
      () -> OutputService.playlistCreationWizard(),
      () -> changePlaylistPublicOrPrivate(false),
      () -> changePlaylistPublicOrPrivate(true),
      () -> deletePlaylist(getPlaylistId("Enter playlist id you want to remove: "))
    );
    return actions;
  }

  public static int getSongId(String message) {
    List<Song> songs = detailedUser instanceof Artist ? ((Artist)detailedUser).getAllSongs() : SongManager.getActiveSongs();
    List<Object> possibleIds = CommonService.getAllIdsOfEntity(songs);
    int songId = Integer.parseInt(InputReaderService.getString(message, possibleIds));
    return songId;
  }

  public static int getPlaylistId(String message) {
    List<Object> possibleIds = CommonService.getAllIdsOfEntity(detailedUser.getPlaylists());
    int playlistId = Integer.parseInt(InputReaderService.getString(message, possibleIds));
    return playlistId;
  }

  public static void changePlaylistName(int playlistId) {
    Playlist playlist = PlaylistManager.getPlaylistById(playlistId);
    String name = InputReaderService.getString("Type your new name for playlist: ", null);
    playlist.setName(name);
  }

  public static void changePlaylistDescription(int playlistId) {
    Playlist playlist = PlaylistManager.getPlaylistById(playlistId);
    String description = InputReaderService.getString("Type your new description for playlist: ", null);
    playlist.setDescription(description);
  }

  public static void deletePlaylist(int playlist) {
    Playlist foundPlaylist = PlaylistManager.getPlaylistById(playlist);
    if (foundPlaylist == null) {
      System.out.println("Playlist not found.");
    } else {
      OutputService.printPlaylist(foundPlaylist);
      String confirm = InputReaderService.getString("Are you sure you want to delete this playlist? (y/n): ", Arrays.asList("y", "n"));
      if (confirm.equals("y")) {
        String confirmSongsDeletion = InputReaderService.getString("Do you wanna delete songs from this playlist? (y/n): ", Arrays.asList("y", "n"));
        PlaylistManager.removePlaylist(foundPlaylist, confirmSongsDeletion.equals("y"));
      } else {
        System.out.println("Deleting cancelled!");
      }
    }
  }

  public static void changePlaylistPublicOrPrivate(boolean isPublic) {
    List<Object> possibleIds = CommonService.getAllIdsOfEntity(detailedUser.getPlaylists());
    InputReaderService.getString("Enter playlist id you mind to make " + (isPublic ? "public" : "private") + ": ", possibleIds);
    PlaylistManager.getPlaylistById(0);
  }
}
