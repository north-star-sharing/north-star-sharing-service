package edu.cnm.deepdive.northstarsharing.views;

/**
 * Used by Gson's @JsonView annotation for including and excluding items to prevent recursively
 * constructed Json objects being returned.
 */
public class CelestialObjectViews {

  /**
   * Mark a class or field as part of the Flat CelestialObject view.
   */
  public static class Flat {

  }

  /**
   * Mark a class or field as part of the Hierarchical CelestialObject view.
   */
  public static class Hierarchical extends  Flat{

  }

}
