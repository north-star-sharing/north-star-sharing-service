package edu.cnm.deepdive.northstarsharing.model.dao;

import edu.cnm.deepdive.northstarsharing.model.entity.CelestialObject;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Declares custom queries (beyond those declared in {@link JpaRepository}) on {@link
 * CelestialObject} entity instances.
 */
public interface CelestialObjectRepository extends JpaRepository<CelestialObject, UUID> {

  /**
   * Returns all celestial objects in name (ascending) order.
   */
  Iterable<CelestialObject> getAllByOrderByNameAsc();

  /**
   * Retrieve celestial objects that match the query filter {@code name}.
   *
   * @param name Query filter by name of the Celestial object.
   */
  Iterable<CelestialObject> findCelestialObjectsByName(String name);

}
