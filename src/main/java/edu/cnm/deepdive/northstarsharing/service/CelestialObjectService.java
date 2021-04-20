package edu.cnm.deepdive.northstarsharing.service;

import edu.cnm.deepdive.northstarsharing.model.dao.CelestialObjectRepository;
import edu.cnm.deepdive.northstarsharing.model.dao.ImageRepository;
import edu.cnm.deepdive.northstarsharing.model.entity.CelestialObject;
import edu.cnm.deepdive.northstarsharing.model.entity.Image;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

/**
 * Implements high-level operations on {@link Image} instances, including file store operations and
 * delegation to methods declared in {@link ImageRepository}.
 */
@Service
public class CelestialObjectService {

  private final CelestialObjectRepository celestialObjectRepository;

  /**
   * Initializes this instance with the provided instances of {@link CelestialObjectRepository}.
   *
   * @param celestialObjectRepository Spring Data repository providing CRUD operations on {@link
   *                                  Image} instances.
   */
  public CelestialObjectService(CelestialObjectRepository celestialObjectRepository) {
    this.celestialObjectRepository = celestialObjectRepository;
  }

  /**
   * Persists (creates or updates) the specified {@link CelestialObject} instance to the database,
   * updating and returning the instance accordingly. (The instance is updated in-place, but the
   * reference to it is also returned.)
   *
   * @param celestialObject Instance to be persisted.
   * @return Updated instance.
   */
  public CelestialObject save(CelestialObject celestialObject) {
    celestialObject.getName();
    return celestialObjectRepository.save(celestialObject);
  }

  /**
   * Selects and returns a {@link CelestialObject} with the specified {@code id}, as the content of
   * an {@link Optional Optional&lt;CelestialObject&gt;}. If no such instance exists, the {@link
   * Optional} is empty.
   *
   * @param id Unique identifier of the {@link CelestialObject}.
   * @return {@link Optional Optional&lt;CelestialObject&gt;} containing the selected celestial
   * object.
   */
  public Optional<CelestialObject> getById(UUID id) {
    return celestialObjectRepository.findById(id);
  }

  /**
   * Selects and returns all celestial objects containing the {@code name} string from the
   * CelestialObject entity name field.
   *
   * @param name Search text.
   * @return All images containing {@code fragment} in the metadata.
   */
  public Iterable<CelestialObject> search(String name) {
    return celestialObjectRepository.findCelestialObjectsByName(name);
  }

}
