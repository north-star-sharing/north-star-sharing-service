package edu.cnm.deepdive.northstarsharing.model.dao;

import edu.cnm.deepdive.northstarsharing.model.entity.Gallery;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Declares custom queries (beyond those declared in {@link JpaRepository}) on {@link Gallery}
 * entity instances.
 */
public interface GalleryRepository extends JpaRepository<Gallery, UUID> {

  /**
   * Returns all galleries in title (descending) order.
   */
  Iterable<Gallery> getAllByOrderByTitleAsc();

}
