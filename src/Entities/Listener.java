package Entities;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Abstracts.DetailedUser;

public class Listener extends DetailedUser {
  private Song lastPlayedSong;
  private List<Song> playedSongs;
  private List<Playlist> likedAlbums;

  public Listener(String fullname, String username, String password, String bio) {
    super(fullname, username, password, bio);
    this.lastPlayedSong = null;
    this.likedAlbums = new ArrayList<>();
    this.playedSongs = new ArrayList<>();
  }

  @Override
  public UserRole getRole() {
    return UserRole.Artist;
  }

  public Song getLastPlayedSong() {
    return lastPlayedSong;
  }
  public void setLastPlayedSong(Song lastPlayedSong) {
    this.lastPlayedSong = lastPlayedSong;
  }

  public List<Playlist> getLikedAlbums() {
    return likedAlbums;
  }
  public void setLikedAlbums(List<Playlist> likedAlbums) {
    this.likedAlbums = likedAlbums;
  }
  public void likeAlbums(Playlist album) {
    this.likedAlbums.add(album);
  }
  public void dislikeAlbums(Playlist album) {
    this.likedAlbums.add(album);
  }

  public List<Song> getPlayedSongs() {
    return playedSongs;
  }
  public void setPlayedSongs(List<Song> playedSongs) {
    this.playedSongs = playedSongs;
  }
  public void addToPlayedSongs(Song song) {
    this.playedSongs.add(song);
  }

  public Song getMostPlayedSong() {
    Map<Song, Integer> songCountMap = new HashMap<>();
    Song mostPlayedSong = null;
    int maxCount = 0;

    for (Song song : this.playedSongs) {
      int count = songCountMap.getOrDefault(song, 0) + 1;
      songCountMap.put(song, count);
      if (count > maxCount) {
        maxCount = count;
        mostPlayedSong = song;
      }
    }

    return mostPlayedSong;
  }
  
}
