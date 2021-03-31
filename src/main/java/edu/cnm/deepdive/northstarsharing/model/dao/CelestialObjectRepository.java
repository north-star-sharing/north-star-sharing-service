package edu.cnm.deepdive.northstarsharing.model.dao;

import edu.cnm.deepdive.northstarsharing.model.entity.CelestialObject;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CelestialObjectRepository extends JpaRepository<CelestialObject, UUID> {

  Iterable<CelestialObject> getAllByOrderByNameAsc();

  Iterable<CelestialObject> findCelestialObjectsByName(String name);

}
