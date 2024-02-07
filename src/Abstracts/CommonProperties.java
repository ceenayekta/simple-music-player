package Abstracts;
import java.time.LocalDateTime;

public abstract class CommonProperties {
  private int id;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public CommonProperties() {
    super();
    this.createdAt = LocalDateTime.now();
    this.updatedAt = null;
  }
  
  public int getId() {
    return id;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void updateUpdatedAt() {
    this.updatedAt = LocalDateTime.now();
  }
}
