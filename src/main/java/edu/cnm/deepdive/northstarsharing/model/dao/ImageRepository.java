package edu.cnm.deepdive.northstarsharing.model.dao;

import edu.cnm.deepdive.northstarsharing.model.entity.Image;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Declares custom queries (beyond those declared in {@link JpaRepository}) on {@link Image} entity
 * instances.
 */
public interface ImageRepository extends JpaRepository<Image, UUID> {

  /**
   * Returns all images in created datetime (descending) order.
   */
  Iterable<Image> getAllByOrderByCreatedDesc();

}
