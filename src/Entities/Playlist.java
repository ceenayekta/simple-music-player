package Entities;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Abstracts.CommonProperties;
import Abstracts.DetailedUser;

public class Playlist extends CommonProperties {
  private String name;
  private String description;
  private DetailedUser owner;
  private boolean isPublic;
  private boolean isBanned = false;
  private LocalDateTime publishedAt;
  private List<Song> songs;

  public Playlist(String name, String description, DetailedUser owner, List<Song> songs) {
    this.name = name;
    this.description = description;
    this.owner = owner;
    this.isPublic = true;
    if (songs != null) {
      this.songs = songs;
      this.publishedAt = LocalDateTime.now();
    } else {
      this.songs = new ArrayList<>();
    }
    owner.addPlaylist(this);
  }

  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

  public DetailedUser getOwner() {
    return owner;
  }
  public void setOwner(DetailedUser owner) {
    this.owner = owner;
  }

  public boolean isPublic() {
    return isPublic;
  }
  public void setPublic(boolean isPublic) {
    this.isPublic = isPublic;
  }
  public void makeItPrivate() {
    setPublic(false);
  }
  public void makeItPublic() {
    setPublic(true);
  }

  public boolean isBanned() {
    return isBanned;
  }
  public void setBanned(boolean isBanned) {
    this.isBanned = isBanned;
  }

  public LocalDateTime getPublishedAt() {
    return publishedAt;
  }
  public void setPublishedAt(LocalDateTime publishedAt) {
    this.publishedAt = publishedAt;
  }
  public boolean isPublished() {
    return publishedAt != null;
  }
  public void publish() {
    setPublishedAt(LocalDateTime.now());
  }

  public List<Song> getSongs() {
    return songs;
  }
  public void setSongs(List<Song> songs) {
    this.songs = songs;
  }
  public int getSongsCount() {
    return songs.size();
  }
  public void addSong(Song song) {
    this.songs.add(song);
  }
  public void removeSong(Song song) {
    this.songs.remove(song);
  }

  public float getTotalDuration() {
    float total = 0;
    for (Song song : this.songs) {
      total += song.getDuration();
    }
    return total;
  }

  public Integer getOverallPlayedCount() {
    int count = 0;
    for (Song song : songs) {
      count += song.getPlayCount();
    }
    return count;
  }

  public void changeSongPriority(Song song, int priority) {
    Optional<Song> samePrioritySong = song.getAlbum().getSongs().stream().filter(s -> s.getPriorityInAlbum() == priority).findFirst();
    if (samePrioritySong.isPresent()) {
      samePrioritySong.get().setPriorityInAlbum(song.getPriorityInAlbum());
    }
    song.setPriorityInAlbum(priority);
  }
  
}
