package edu.cnm.deepdive.northstarsharing.model.entity;

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

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(
    indexes = {
        @Index(columnList = "name")
    }
)
public class CelestialObject {

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

  @Column(nullable = false)
  private double altitude;

  @Column(nullable = false)
  private double azimuth;

  @Column(nullable = false)
  private double rightAscension;

  @Column(nullable = false)
  private double declination;

  @Column(nullable = false)
  private double cartesianX;

  @Column(nullable = false)
  private double cartesianY;

  @Column(nullable = false)
  private double cartesianZ;

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
  public Date getUpdated() {
    return updated;
  }

  public void setUpdated(@NonNull Date updated) {
    this.updated = updated;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getAltitude() {
    return altitude;
  }

  public void setAltitude(double altitude) {
    this.altitude = altitude;
  }

  public double getAzimuth() {
    return azimuth;
  }

  public void setAzimuth(double azimuth) {
    this.azimuth = azimuth;
  }

  public double getRightAscension() {
    return rightAscension;
  }

  public void setRightAscension(double rightAscension) {
    this.rightAscension = rightAscension;
  }

  public double getDeclination() {
    return declination;
  }

  public void setDeclination(double declination) {
    this.declination = declination;
  }

  public double getCartesianX() {
    return cartesianX;
  }

  public void setCartesianX(double cartesianX) {
    this.cartesianX = cartesianX;
  }

  public double getCartesianY() {
    return cartesianY;
  }

  public void setCartesianY(double cartesianY) {
    this.cartesianY = cartesianY;
  }

  public double getCartesianZ() {
    return cartesianZ;
  }

  public void setCartesianZ(double cartesianZ) {
    this.cartesianZ = cartesianZ;
  }

  @NonNull
  public List<Image> getImages() {
    return images;
  }
}
