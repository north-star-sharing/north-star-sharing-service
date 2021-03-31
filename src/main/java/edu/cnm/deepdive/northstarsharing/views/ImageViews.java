package edu.cnm.deepdive.northstarsharing.views;

/**
 * Used by Gson's @JsonView annotation for including and excluding items to prevent
 * recursively constructed Json objects being returned.
 */
public class ImageViews {

  /**
   * Mark a class or field as part of the Flat Image view.
   */
  public static class Flat {}

  /**
   * Mark a class or field as part of the Hierarchical Image view.
   */
  public static class Hierarchical {}

}
