package edu.cnm.deepdive.northstarsharing.model.entity;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.util.Date;

@Entity(
    foreignKeys = {
        @ForeignKey(
            entity = User.class,
            parentColumns = "user_id_number", childColumns = "user_id_number"
        ),
        @ForeignKey(
            entity = Gallery.class,
            parentColumns = "galleryId", childColumns = "galleryId"
        )
    }
)
public class Image {

  @PrimaryKey
  private long imageId;

  @NonNull
  private Date createdTimestamp = new Date();

  private Date updateTimestamp = new Date();

  private String title = new String();

  private String caption = new String();

  private String path = new String();

  private String name = new String();

  private String contentFileType = new String();

  public long getImageId() {
    return imageId;
  }

  public void setImageId(long imageId) {
    this.imageId = imageId;
  }

  @NonNull
  public Date getCreatedTimestamp() {
    return createdTimestamp;
  }

  public void setCreatedTimestamp(@NonNull Date createdTimestamp) {
    this.createdTimestamp = createdTimestamp;
  }

  public Date getUpdateTimestamp() {
    return updateTimestamp;
  }

  public void setUpdateTimestamp(Date updateTimestamp) {
    this.updateTimestamp = updateTimestamp;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getCaption() {
    return caption;
  }

  public void setCaption(String caption) {
    this.caption = caption;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getContentFileType() {
    return contentFileType;
  }

  public void setContentFileType(String contentFileType) {
    this.contentFileType = contentFileType;
  }
}
