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
    name = "image",
    indexes = {
        @Index(columnList = "title")
    }
)
public class Image {

  @NonNull
  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "image_id", nullable = false, updatable = false, columnDefinition = "CHAR(16) FOR BIT DATA")
  private UUID id;

  // TODO Create FK annotation to User table.
  @NonNull
  @Column(nullable = false)
  private UUID userId;

  // TODO Create FK annotation to Gallery table.
  @NonNull
  @Column(nullable = false)
  private UUID galleryId;

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

  private String name;

  private String contentFileType;

  // TODO Learn/create annotation of reference to the foreign key Java object.
  private User user;

  // TODO Learn/create annotation of reference to the foreign key Java object.
  private Gallery gallery;

  // TODO Create getters for immutable data and getters/setters for the rest. Determine FK object.
}
