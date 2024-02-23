package Managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Abstracts.DetailedUser;
import Entities.Playlist;
import Entities.Song;
import Enums.Category;
import Services.SongService;

public class PlaylistManager {
  private static List<Playlist> playlists = new ArrayList<>();
  
  public static List<Playlist> getAllPlaylists() {
    return playlists;
  }

  public static void addPlaylist(Playlist playlist) {
    playlists.add(playlist);
  }

  public static void removePlaylist(Playlist playlist) {
    playlists.remove(playlist);
  }

  public static Playlist createPlaylist(String name, String description, DetailedUser owner, List<Song> songs) {
    Playlist newPlaylist = new Playlist(name, description, owner, songs);
    addPlaylist(newPlaylist);
    return newPlaylist;
  }

  public static void removePlaylistById(int playlistId) {
    Playlist playlist = getPlaylistById(playlistId);
    removePlaylist(playlist);
  }

  public static Playlist getPlaylistById(int playlistId) {
    Optional<Playlist> playlist = playlists.stream().filter(p -> p.getId() == playlistId).findFirst();
    return playlist.orElse(null);
  }

  public static List<Playlist> getActivePlaylists() {
    List<Playlist> filteredPlaylists = playlists.stream().filter(p -> p.isPublic() && p.isPublished() && !p.isBanned()).toList();
    return filteredPlaylists;
  }

  public static List<Playlist> getActivePlaylistsOfUser(int userId) {
    List<Playlist> filteredPlaylists = getActivePlaylists().stream().filter(p -> p.getOwner().getId() == userId).toList();
    return filteredPlaylists;
  }

  public static void banPlaylist(int playlistId, boolean banned) {
    Playlist playlist = getPlaylistById(playlistId);
    playlist.setBanned(banned);
    playlist.getSongs().forEach(s -> s.setBanned(banned));
  }

  public static List<Playlist> filterPlaylistsByName(String query, boolean onlyActive) {
    List<Playlist> playlistsToSearch = onlyActive ? getActivePlaylists() : playlists;
    List<Playlist> song = playlistsToSearch.stream().filter(p -> p.getName().toLowerCase().contains(query.toLowerCase())).toList();
    return song;
  }

  public static List<Playlist> filterPlaylistsByCategory(Category category, boolean onlyActive) {
    List<Playlist> playlistsToSearch = onlyActive ? getActivePlaylists() : playlists;
    List<Playlist> filteredPlaylists = playlistsToSearch.stream().filter(p -> SongService.areHalfOfSongsSameCategory(p.getSongs(), category)).toList();
    return filteredPlaylists;
  }

  public static List<Playlist> sortPlayListsByPlayCount(boolean onlyActive) {
    List<Playlist> playlistsToSearch = onlyActive ? getActivePlaylists() : playlists;
    List<Playlist> playlists = playlistsToSearch.stream().sorted((u1, u2) -> {
      return u1.getOverallPlayedCount().compareTo(u2.getOverallPlayedCount());
    }).toList();
    return playlists;
  }

  public static void generateData() {
    // addUser(new Admin("Ceena Yekta", "ceen", "ceen123"));
    // addUser(new Artist("Sebastian Bach", "Bach.S", "bach#1", "bach", "The Great Bach"));
    // addUser(new Listener("John Doe", "John", "doe12", "John Who?"));
  }
}
