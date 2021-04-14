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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * Encapsulates a persistent user object with basic OpenID properties.
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(
    name = "user_profile",
    indexes = {
        @Index(columnList = "created"),
        @Index(columnList = "connected")
    }
)
@Component
public class User {

  @NonNull
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @OrderBy("created DESC")
  private final List<Image> images = new LinkedList<>();

  @NonNull
  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "user_id", nullable = false, updatable = false, columnDefinition = "CHAR(16) FOR BIT DATA")
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
  private Date connected;

  @NonNull
  @Column(nullable = false, updatable = false, unique = true)
  private String oauthKey;

  @NonNull
  @Column(nullable = false, unique = true)
  private String displayName;

  // Getters and Setters

  /**
   * Returns the unique identifier of this user.
   */
  @NonNull
  public UUID getId() {
    return id;
  }

  /**
   * Returns the datetime this user was first persisted to the database.
   */
  @NonNull
  public Date getCreated() {
    return created;
  }

  /**
   * Returns the datetime this image was most recently connected/updated.
   */
  @NonNull
  public Date getConnected() {
    return connected;
  }

  /**
   * Sets the datetime this image was most recently connected/updated.
   */
  public void setConnected(@NonNull Date connected) {
    this.connected = connected;
  }

  /**
   * Returns the unique identifier provided (and recognized) by the OpenID/OAuth2.0 provider.
   */
  @NonNull
  public String getOauthKey() {
    return oauthKey;
  }

  /**
   * Sets the unique OpenID/OAuth2.0 identifier of this user to the specified {@code oauthKey}.
   */
  public void setOauthKey(@NonNull String oauthKey) {
    this.oauthKey = oauthKey;
  }

  /**
   * Returns the unique display name of this user.
   */
  @NonNull
  public String getDisplayName() {
    return displayName;
  }

  /**
   * Sets the display name of this user to the specified {@code displayName}.
   */
  public void setDisplayName(@NonNull String displayName) {
    this.displayName = displayName;
  }

  /**
   * Returns the {@link List List&lt;Image&gt;} contributed by this user, in descending date order.
   */
  @NonNull
  public List<Image> getImages() {
    return images;
  }
}
