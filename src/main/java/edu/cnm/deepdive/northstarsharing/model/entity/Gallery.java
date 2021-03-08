package edu.cnm.deepdive.northstarsharing.model.entity;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.util.Date;

@Entity(
    foreignKeys = {
        @ForeignKey(
            entity = User.class,
            parentColumns = "user_id_number", childColumns = "user_id_number"
        )
    }
)
public class Gallery {

  @PrimaryKey(autoGenerate = true)
  private long galleryId;

  @NonNull
  @ColumnInfo(index = true)
  private Date creationTimestamp = new Date();

  @ColumnInfo(index = true)
  private Date updateTimestamp = new Date();

  private String caption = new String();

  private String description = new String();

  public long getGalleryId() {
    return galleryId;
  }

  public void setGalleryId(long galleryId) {
    this.galleryId = galleryId;
  }

  @NonNull
  public Date getCreationTimestamp() {
    return creationTimestamp;
  }

  public void setCreationTimestamp(@NonNull Date creationTimestamp) {
    this.creationTimestamp = creationTimestamp;
  }

  public Date getUpdateTimestamp() {
    return updateTimestamp;
  }

  public void setUpdateTimestamp(Date updateTimestamp) {
    this.updateTimestamp = updateTimestamp;
  }

  public String getCaption() {
    return caption;
  }

  public void setCaption(String caption) {
    this.caption = caption;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
