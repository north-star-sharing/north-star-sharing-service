package edu.cnm.deepdive.northstarsharing.controller;

import edu.cnm.deepdive.northstarsharing.model.entity.CelestialObject;
import edu.cnm.deepdive.northstarsharing.service.CelestialObjectService;
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

@RestController
@RequestMapping("/celestialobjects")
@ExposesResourceFor(CelestialObject.class)
public class CelestialObjectController {

  private final CelestialObjectService celestialObjectService;

  @Autowired
  public CelestialObjectController(CelestialObjectService celestialObjectService) {
    this.celestialObjectService = celestialObjectService;
  }

  @GetMapping("/observable")
  public ResponseEntity<Resource> getObservable(@PathVariable UUID id, String name) {
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
