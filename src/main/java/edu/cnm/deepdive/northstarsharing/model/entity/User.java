package edu.cnm.deepdive.northstarsharing.model.entity;



import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.springframework.lang.NonNull;

@Entity
public class User {

  @Id
  private long userId;

  @NonNull
  private Date creationTimestamp;

  private Date updateTimestamp;

  private Date connectedTimestamp;

  private String oauthKey;

  private String userName;


  public long getId() {
    return userId;
  }

  public void setId(long userId) {
    this.userId = userId;
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

  public Date getConnectedTimestamp() {
    return connectedTimestamp;
  }

  public void setConnectedTimestamp(Date connectedTimestamp) {
    this.connectedTimestamp = connectedTimestamp;
  }

  public String getoOauthKey() {
    return oauthKey;
  }

  public void setoOauthKey(String oAuthKey) {
    this.oauthKey = oAuthKey;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
}
