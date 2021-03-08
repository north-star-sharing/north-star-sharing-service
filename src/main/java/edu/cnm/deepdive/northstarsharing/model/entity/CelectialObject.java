package edu.cnm.deepdive.northstarsharing.model.entity;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Date;

@Entity
public class CelectialObject {

  @PrimaryKey(autoGenerate = true)
  private long celestialObjectId;

  @NonNull
  @ColumnInfo(name = "object_import_date")
  private Date created = new Date();

  @ColumnInfo(name = "object_name")
  private String name = new String();

  private String altitude = new String();

  private String azimuth = new String();

  private String rightAscension = new String();

  private String declination = new String();

  private String cartesianX = new String();

  private String cartesianY = new String();

  private String cartesianZ = new String();

  @NonNull
  public Date getCreated() {
    return created;
  }

  public void setCreated(@NonNull Date created) {
    this.created = created;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAltitude() {
    return altitude;
  }

  public void setAltitude(String altitude) {
    this.altitude = altitude;
  }

  public String getAzimuth() {
    return azimuth;
  }

  public void setAzimuth(String azimuth) {
    this.azimuth = azimuth;
  }

  public String getRightAscension() {
    return rightAscension;
  }

  public void setRightAscension(String rightAscension) {
    this.rightAscension = rightAscension;
  }

  public String getDeclination() {
    return declination;
  }

  public void setDeclination(String declination) {
    this.declination = declination;
  }

  public String getCartesianX() {
    return cartesianX;
  }

  public void setCartesianX(String cartesianX) {
    this.cartesianX = cartesianX;
  }

  public String getCartesianY() {
    return cartesianY;
  }

  public void setCartesianY(String cartesianY) {
    this.cartesianY = cartesianY;
  }

  public String getCartesianZ() {
    return cartesianZ;
  }

  public void setCartesianZ(String cartesianZ) {
    this.cartesianZ = cartesianZ;
  }
}
