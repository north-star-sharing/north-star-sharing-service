package edu.cnm.deepdive.northstarsharing.views;

/**
 * Used by Gson's @JsonView annotation for including and excluding items to prevent recursively
 * constructed Json objects being returned.
 */
public class GalleryViews {

  /**
   * Mark a class or field as part of the Flat Gallery view.
   */
  public static class Flat {

  }

  /**
   * Mark a class or field as part of the Hierarchical Gallery view.
   */
  public static class Hierarchical extends Flat {

  }

}
