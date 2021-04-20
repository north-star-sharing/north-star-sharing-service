package edu.cnm.deepdive.northstarsharing.model.entity;

import com.fasterxml.jackson.annotation.JsonView;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.NonNull;

/**
 * Encapsulates a persistent celestial object with: name, right ascension and declination.
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(
    indexes = {
        @Index(columnList = "name")
    }
)
@JsonView({})
public class CelestialObject {

  @NonNull
  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "celestial_object_id", nullable = false, updatable = false, columnDefinition = "CHAR(16) FOR BIT DATA")
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
  private String name;

  @NonNull
  private double rightAscension;

  @NonNull
  private double declination;


  @NonNull
  @ManyToMany(
      fetch = FetchType.LAZY,
      cascade = {
          CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH
      }
  )
  @JoinTable(
      name = "celestial_object_image",
      joinColumns = @JoinColumn(name = "celestial_object_id", nullable = false, updatable = false),
      inverseJoinColumns = @JoinColumn(name = "image_id", nullable = false, updatable = false)
  )
  @OrderBy("created DESC")
  private final List<Image> images = new LinkedList<>();

  // Getters and Setters

  /**
   * Returns the unique identifier of this celestial object.
   */
  @NonNull
  public UUID getId() {
    return id;
  }

  /**
   * Returns the datetime this celestial object was first persisted to the database.
   */
  @NonNull
  public Date getCreated() {
    return created;
  }

  /**
   * Returns the datetime this celestial object was most recently updated.
   */
  @NonNull
  public Date getUpdated() {
    return updated;
  }

  /**
   * Sets the datetime this celestial object was most recently updated.
   */
  public void setUpdated(@NonNull Date updated) {
    this.updated = updated;
  }

  /**
   * Returns the name of this celestial object.
   */
  @NonNull
  public String getName() {
    return name;
  }

  /**
   * Sets the name of this celestial object to the specified {@code title}.
   */
  public void setName(@NonNull String name) {
    this.name = name;
  }

  /**
   * Returns the right ascension of this celestial object.
   */
  @NonNull
  public double getRightAscension() {
    return rightAscension;
  }

  /**
   * Sets the right ascension of this celestial object to the specified {@code rightAscension}.
   */
  public void setRightAscension(@NonNull double rightAscension) {
    this.rightAscension = rightAscension;
  }

  /**
   * Returns the declination of this celestial object.
   */
  @NonNull
  public double getDeclination() {
    return declination;
  }

  /**
   * Sets the declination of this celestial object to the specified {@code declination}.
   */
  public void setDeclination(@NonNull double declination) {
    this.declination = declination;
  }

  /**
   * Returns the {@link List List&lt;Image&gt;} contributed by this celestial object, in descending
   * date order.
   */
  @NonNull
  public List<Image> getImages() {
    return images;
  }

}
