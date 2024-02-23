package Entities;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import Abstracts.CommonProperties;
import Enums.Category;

public class Song extends CommonProperties {
  private boolean isBanned = false;
  private String name;
  private Playlist album;
  private Artist artist;
  private File file;
  private Category category;
  private List<Listener> playedBy;
  private int priorityInAlbum = 0;

  public Song(String name, Playlist album, Artist artist, File file, Category category) {
    super();
    this.name = name;
    this.album = album;
    this.artist = artist;
    this.file = file;
    this.category = category;
    this.playedBy = new ArrayList<>();
    if (artist != null) artist.addSong(this);
    if (album != null) {
      album.addSong(this);
      this.priorityInAlbum = album.getSongsCount();
    }
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isBanned() {
    return isBanned;
  }
  public void setBanned(boolean isBanned) {
    this.isBanned = isBanned;
  }

  public Playlist getAlbum() {
    return album;
  }

  public void setAlbum(Playlist album) {
    if (this.album != null) {
      this.album.removeSong(this);
    }
    this.album = album;
    priorityInAlbum = album.getSongsCount() + 1;
  }

  public Artist getArtist() {
    return artist;
  }

  public void setArtist(Artist artist) {
    this.artist = artist;
  }

  public File getFile() {
    return file;
  }
  public void setFile(File file) {
    this.file = file;
  }
  public float getSize() {
    return this.file.length();
  }
  
  public int getDuration() {
    try {
      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(this.file);
      AudioFormat format = audioInputStream.getFormat();
      long frames = audioInputStream.getFrameLength();
      double durationInSeconds = (frames+0.0) / format.getFrameRate();
      return ((int)durationInSeconds);
    } catch (Exception e) {
      return 0;
  }
  }
  public String getPath() {
    return this.file.getPath();
  }

  public Category getCategory() {
    return category;
  }
  public void setCategory(Category category) {
    this.category = category;
  }

  public List<Listener> getPlayedBy() {
    return playedBy;
  }
  public void setPlayedBy(List<Listener> playedBy) {
    this.playedBy = playedBy;
  }
  public void addPlayedBy(Listener listener) {
    playedBy.add(listener);
  }
  public Integer getPlayCount() {
    return playedBy.size();
  }

  public LocalDateTime getPublishedAt() {
    return album.getPublishedAt();
  }

  public int getPriorityInAlbum() {
    return priorityInAlbum;
  }
  public void setPriorityInAlbum(int priorityInAlbum) {
    this.priorityInAlbum = priorityInAlbum;
  }
}
