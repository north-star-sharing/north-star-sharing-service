package edu.cnm.deepdive.northstarsharing.model.entity;

import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.NonNull;

@Entity
@Table(
    name = "celestial_object",
    indexes = {
        @Index(columnList = "name")
    }
)
public class CelectialObject {

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
  @Column(nullable = false)
  private String altitude;

  @NonNull
  @Column(nullable = false)
  private String azimuth;

  @NonNull
  @Column(nullable = false)
  private String rightAscension;

  @NonNull
  @Column(nullable = false)
  private String declination;

  @NonNull
  @Column(nullable = false)
  private String cartesianX;

  @NonNull
  @Column(nullable = false)
  private String cartesianY;

  @NonNull
  @Column(nullable = false)
  private String cartesianZ;

  // TODO Create getters for immutable data and getters/setters for the rest.

  // TODO Create linking table (CelestialObjectImage) to tag images.
}
