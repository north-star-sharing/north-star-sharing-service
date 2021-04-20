package edu.cnm.deepdive.northstarsharing.model.entity;

import com.fasterxml.jackson.annotation.JsonView;
import edu.cnm.deepdive.northstarsharing.views.GalleryViews;
import edu.cnm.deepdive.northstarsharing.views.ImageViews;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

/**
 * Encapsulates a persistent gallery object with: title, description, reference to the owner {@link
 * User}, and a reference to the list of {@link Image}s it contains.
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(
    indexes = {
        @Index(columnList = "title")
    }
)
@JsonView({GalleryViews.Flat.class, ImageViews.Hierarchical.class})
public class Gallery {

  private static EntityLinks entityLinks;

  @NonNull
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @OrderBy("created DESC")
  @JsonView({GalleryViews.Hierarchical.class})
  private final List<Image> images = new LinkedList<>();

  @NonNull
  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "gallery_Id", nullable = false, updatable = false, columnDefinition = "CHAR(16) FOR BIT DATA")
  private UUID id;

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

  @Column(length = 1024)
  private String description;

  @NonNull
  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  @JsonView({GalleryViews.Hierarchical.class})
  private User user;

  // Getters and Setters

  /**
   * Returns the unique identifier of this gallery.
   */
  @NonNull
  public UUID getId() {
    return id;
  }

  /**
   * Returns the datetime this gallery was first persisted to the database.
   */
  @NonNull
  public Date getCreated() {
    return created;
  }

  /**
   * Returns the datetime this gallery was most recently updated in the database.
   */
  @NonNull
  public Date getUpdated() {
    return updated;
  }

  /**
   * Returns the {@link List} of {@link Image}s contained by this gallery.
   */
  @NonNull
  public List<Image> getImages() {
    return images;
  }

  /**
   * Return the user that created this gallery.
   */
  @NonNull
  public User getUser() {
    return user;
  }

  /**
   * Set the user that created this gallery.
   */
  public void setUser(@NonNull User user) {
    this.user = user;
  }

  /**
   * Returns the title of this gallery.
   */
  @NonNull
  public String getTitle() {
    return title;
  }

  /**
   * Sets the title of this gallery to the specified {@code title}.
   */
  public void setTitle(@NonNull String title) {
    this.title = title;
  }

  /**
   * Returns the description of this gallery.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description of this gallery to the specified {@code description}.
   */
  public void setDescription(String description) {
    this.description = description;
  }

  public URI getHref() {
    //noinspection ConstantConditions
    return (id != null) ? entityLinks.linkToItemResource(Image.class, id).toUri() : null;
  }

  @Autowired
  public void setEntityLinks(
      @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") EntityLinks entityLinks) {
    Gallery.entityLinks = entityLinks;
  }

  @PostConstruct
  private void initHateoas() {
    //noinspection ResultOfMethodCallIgnored
    entityLinks.hashCode();
  }
}
