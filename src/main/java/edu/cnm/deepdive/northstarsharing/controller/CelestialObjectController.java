package edu.cnm.deepdive.northstarsharing.controller;

import edu.cnm.deepdive.northstarsharing.model.entity.CelestialObject;
import edu.cnm.deepdive.northstarsharing.model.entity.Image;
import edu.cnm.deepdive.northstarsharing.service.CelestialObjectService;
import edu.cnm.deepdive.northstarsharing.service.ImageService;
import java.io.IOException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles REST requests for operations on individual instances of the {@link CelestialObject}
 * type.
 */
@RestController
@RequestMapping("/celestialobjects")
@ExposesResourceFor(CelestialObject.class)
public class CelestialObjectController {

  private final CelestialObjectService celestialObjectService;

  /**
   * Initializes this instance with the {@link CelestialObjectService} instances used to perform the
   * requested operations.
   *
   * @param celestialObjectService Provides access to high-level query &amp; persistence operations
   *                               on {@link CelestialObject} instances
   */
  @Autowired
  public CelestialObjectController(CelestialObjectService celestialObjectService) {
    this.celestialObjectService = celestialObjectService;
  }

  /**
   * Return the requested {@link CelestialObject} instance.
   *
   * @param id Unique identifier for the {@link CelestialObject} instance.
   * @return A {@link CelestialObject} HTTP GET request response.
   */
  @GetMapping("/observable/{id}")
  public ResponseEntity<Resource> getObservable(@PathVariable UUID id) {
    return celestialObjectService
        .getById(id)
        .map((observable) -> {
          try {
            Resource resource = celestialObjectService.getObservable(observable);
            return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_LENGTH,
                    String.valueOf(resource.contentLength()))
                .body(resource);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        })
        .orElseThrow();
  }
}
