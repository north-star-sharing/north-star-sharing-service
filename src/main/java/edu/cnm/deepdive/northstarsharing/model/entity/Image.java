package edu.cnm.deepdive.northstarsharing.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import edu.cnm.deepdive.northstarsharing.configuration.Beans;
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

/**
 * Encapsulates a persistent image object with: title, description, file metadata (original filename
 * and MIME type), reference to the contributing {@link User}, reference to the {@link Gallery} it
 * is a part of, and reference to the actual content.
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(
    indexes = {
        @Index(columnList = "created"),
        @Index(columnList = "title")
    }
)
@Component
@JsonView({GalleryViews.Hierarchical.class, ImageViews.Flat.class})
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
  @Column(nullable = false, updatable = false)
  private String name;

  @JsonIgnore
  @NonNull
  @Column(name = "resource_key", nullable = false, updatable = false)
  private String key;

  @NonNull
  @Column(nullable = false, updatable = false)
  private String contentType;


  @Column(nullable = false, updatable = false)
  private float azimuth;


  @Column(nullable = false, updatable = false)
  private float pitch;


  @Column(nullable = false, updatable = false)
  private float roll;


  @Column(nullable = false, updatable = false)
  private double latitude;


  @Column(nullable = false, updatable = false)
  private double longitude;


  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  private User user;

  @ManyToOne(optional = true)
  @JoinColumn(name = "gallery_id")
  private Gallery gallery;

  // Getters and Setters

  /**
   * Returns the unique identifier of this image.
   */
  @NonNull
  public UUID getId() {
    return id;
  }

  /**
   * Returns the datetime this image was first persisted to the database.
   */
  @NonNull
  public Date getCreated() {
    return created;
  }

  /**
   * Returns the datetime this image was most recently updated in the database.
   */
  @NonNull
  public Date getUpdated() {
    return updated;
  }

  /**
   * Returns the title of this image.
   */
  @NonNull
  public String getTitle() {
    return title;
  }

  /**
   * Sets the title of this image to the specified {@code title}.
   */
  public void setTitle(@NonNull String title) {
    this.title = title;
  }

  /**
   * Returns the description of this image.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description of this image to the specified {@code description}.
   */
  public void setDescription(String caption) {
    this.description = caption;
  }

  /**
   * Returns the filename of this image.
   */
  @NonNull
  public String getName() {
    return name;
  }

  /**
   * Sets the filename of this image to the specified {@code name}.
   */
  public void setName(@NonNull String name) {
    this.name = name;
  }

  /**
   * Returns a reference (a {@link String} representation of a {@link java.nio.file.Path}, {@link
   * URI}, etc.) to the location of this image. This should be treated as an &ldquo;opaque&rdquo;
   * value, meaningful only to the storage service.
   */
  @NonNull
  public String getKey() {
    return key;
  }

  /**
   * Sets the location reference of this image to the specified {@code path}. This should be treated
   * as an &ldquo;opaque&rdquo; value, meaningful only to the storage service.
   */
  public void setKey(@NonNull String key) {
    this.key = key;
  }

  /**
   * Returns the MIME type of this image.
   */
  @NonNull
  public String getContentType() {
    return contentType;
  }

  /**
   * Sets the MIME type of this image to the specified {@code contentType}.
   */
  public void setContentType(@NonNull String contentType) {
    this.contentType = contentType;
  }

  /**
   * Returns the azimuth angle in degrees at the time the image was captured. This value represents
   * the angle between the device's y-axis and the magnetic north pole. When facing north, this
   * angle is 0, when facing south, this angle is π. Likewise, when facing east, this angle is π/2,
   * and when facing west, this angle is -π/2. The range of values is -π to π.
   */
  @NonNull
  public float getAzimuth() {
    return azimuth;
  }

  /**
   * Sets the azimuth angle in degrees at the time the image was captured. This value represents
   * the angle between the device's y-axis and the magnetic north pole. When facing north, this
   * angle is 0, when facing south, this angle is π. Likewise, when facing east, this angle is π/2,
   * and when facing west, this angle is -π/2. The range of values is -π to π.
   */
  public void setAzimuth(@NonNull float azimuth) {
    this.azimuth = azimuth;
  }

  /**
   * Returns the pitch angle in degrees at the time the image was captured. This value represents
   * the angle between a plane parallel to the device's screen and a plane parallel to the ground.
   * Assuming that the bottom edge of the device faces the user and that the screen is face-up,
   * tilting the top edge of the device toward the ground creates a positive pitch angle. The range
   * of values is -π to π.
   */
  @NonNull
  public float getPitch() {
    return pitch;
  }

  /**
   * Sets the pitch angle in degrees at the time the image was captured. This value represents
   * the angle between a plane parallel to the device's screen and a plane parallel to the ground.
   * Assuming that the bottom edge of the device faces the user and that the screen is face-up,
   * tilting the top edge of the device toward the ground creates a positive pitch angle. The range
   * of values is -π to π.
   */
  public void setPitch(@NonNull float pitch) {
    this.pitch = pitch;
  }

  /**
   * Returns the roll angle in degrees at the time the image was captured. This value represents the
   * angle between a plane perpendicular to the device's screen and a plane perpendicular to the
   * ground. Assuming that the bottom edge of the device faces the user and that the screen is
   * face-up, tilting the left edge of the device toward the ground creates a positive roll angle.
   * The range of values is -π/2 to π/2.
   */
  @NonNull
  public float getRoll() {
    return roll;
  }

  /**
   * Sets the roll angle in degrees at the time the image was captured. This value represents the
   * angle between a plane perpendicular to the device's screen and a plane perpendicular to the
   * ground. Assuming that the bottom edge of the device faces the user and that the screen is
   * face-up, tilting the left edge of the device toward the ground creates a positive roll angle.
   * The range of values is -π/2 to π/2.
   */
  public void setRoll(@NonNull float roll) {
    this.roll = roll;
  }

  /**
   * Returns the north/south latitude in degrees at the time the image was captured.
   */
  @NonNull
  public double getLatitude() {
    return latitude;
  }

  /**
   * Sets the north/south latitude in degrees at the time the image was captured.
   */
  public void setLatitude(@NonNull double latitude) {
    this.latitude = latitude;
  }

  /**
   * Returns the east/west longitude in degrees at the time the image was captured.
   */
  @NonNull
  public double getLongitude() {
    return longitude;
  }

  /**
   * Sets the east/west longitude in degrees at the time the image was captured.
   */
  public void setLongitude(@NonNull double longitude) {
    this.longitude = longitude;
  }

  /**
   * Returns the {@link User} that contributed this image.
   */
  @NonNull
  public User getUser() {
    return user;
  }

  /**
   * Sets this image's contributor to the specified {@link User}.
   */
  public void setUser(@NonNull User user) {
    this.user = user;
  }

  /**
   * Return the image's associated {@link Gallery}.
   */
  public Gallery getGallery() {
    return gallery;
  }

  /**
   * Sets the image's associated {@link Gallery}.
   */
  public void setGallery(Gallery gallery) {
    this.gallery = gallery;
  }

  /**
   * Returns a list of all associated {@link CelestialObject} tags.
   */
  @NonNull
  public List<CelestialObject> getCelestialObjects() {
    return celestialObjects;
  }

  // Link methods.



  /**
   * Returns the location of REST resource representation of this image.
   */
  public URI getHref() {
    //noinspection ConstantConditions
    EntityLinks entityLinks = Beans.bean(EntityLinks.class);
    return (id != null) ? entityLinks.linkToItemResource(Image.class, id).toUri() : null;
  }

  /**
   * Injects the {@link EntityLinks} required for constructing the REST resource location of an
   * image.
   */
  @Autowired
  public void setEntityLinks(
      @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") EntityLinks entityLinks) {
    Image.entityLinks = entityLinks;
  }


  @PostConstruct
  private void initHateoas() {
    //noinspection ResultOfMethodCallIgnored
    entityLinks.toString();
  }

}
