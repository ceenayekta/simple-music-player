package Entities;
import java.time.LocalDateTime;

import Abstracts.CommonProperties;

public class Song extends CommonProperties {
  private String name;
  private Album album;
  private Artist artist;
  private float size;
  private float length;
  private String path;
  private LocalDateTime publishedAt;

  public String getName() {
    return name;
  }

  protected void setName(String name) {
    this.name = name;
  }

  public Album getAlbum() {
    return album;
  }

  protected void setAlbum(Album album) {
    this.album = album;
  }

  public Artist getArtist() {
    return artist;
  }

  protected void setArtist(Artist artist) {
    this.artist = artist;
  }

  public float getSize() {
    return size;
  }

  protected void setSize(float size) {
    this.size = size;
  }

  public float getLength() {
    return length;
  }

  protected void setLength(float length) {
    this.length = length;
  }

  public String getPath() {
    return path;
  }

  protected void setPath(String path) {
    this.path = path;
  }

  public LocalDateTime getPublishedAt() {
    return publishedAt;
  }

  protected void setPublishedAt(LocalDateTime publishedAt) {
    this.publishedAt = publishedAt;
  }
}
