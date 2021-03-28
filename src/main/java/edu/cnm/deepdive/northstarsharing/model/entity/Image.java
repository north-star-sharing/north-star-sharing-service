package edu.cnm.deepdive.northstarsharing.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import java.net.URI;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(
    indexes = {
        @Index(columnList = "created"),
        @Index(columnList = "title")
    }
)
@Component
public class Image {

  private static EntityLinks entityLinks;

  @NonNull
  @ManyToMany(
      fetch = FetchType.LAZY,
      cascade = {
          CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.PERSIST
      },
      mappedBy = "images"
  )
  @OrderBy("name ASC, created DESC")
  private final List<CelestialObject> celestialObjects = new LinkedList<>();

  @NonNull
  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "image_id", nullable = false, updatable = false, columnDefinition = "CHAR(16) FOR BIT DATA")
  private UUID id;

  @JsonIgnore
  @NonNull
  @Column(name = "imageKey", nullable = false, updatable = false)
  private String key;

  @NonNull
  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  private User user;

  @ManyToOne(optional = true)
  @JoinColumn(name = "gallery_id")
  private Gallery gallery;

  @NonNull
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date created;

  @NonNull
  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  private Date updated;

  @NonNull
  @Column(nullable = false)
  private String title;

  private String caption;

  @NonNull
  @Column(nullable = false)
  private String path;

  private String contentType;

  @NonNull
  @Column(nullable = false, updatable = false)
  private String name;

  // Getters and Setters

  @NonNull
  public UUID getId() {
    return id;
  }

  @NonNull
  public Date getCreated() {
    return created;
  }

  @NonNull
  public String getKey() {
    return key;
  }

  public void setKey(@NonNull String key) {
    this.key = key;
  }

  @NonNull
  public Date getUpdated() {
    return updated;
  }

  @NonNull
  public User getUser() {
    return user;
  }

  public void setUser(@NonNull User user) {
    this.user = user;
  }

  public Gallery getGallery() {
    return gallery;
  }

  public void setGallery(Gallery gallery) {
    this.gallery = gallery;
  }

  @NonNull
  public String getTitle() {
    return title;
  }

  public void setTitle(@NonNull String title) {
    this.title = title;
  }

  public String getCaption() {
    return caption;
  }

  public void setCaption(String caption) {
    this.caption = caption;
  }

  @NonNull
  public String getPath() {
    return path;
  }

  public void setPath(@NonNull String path) {
    this.path = path;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentFileType) {
    this.contentType = contentFileType;
  }

  @NonNull
  public String getName() {
    return name;
  }

  public void setName(@NonNull String name) {
    this.name = name;
  }

  @NonNull
  public List<CelestialObject> getCelestialObjects() {
    return celestialObjects;
  }

  // Link methods

  public URI getHref() {
    //noinspection ConstantConditions
    return (id != null) ? entityLinks.linkToItemResource(Image.class, id).toUri() : null;
  }

  @Autowired
  public void setEntityLinks(
      @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") EntityLinks entityLinks) {
    Image.entityLinks = entityLinks;
  }


  @PostConstruct
  private void initHateoas() {
    //noinspection ResultOfMethodCallIgnored
    entityLinks.hashCode();
  }

}
