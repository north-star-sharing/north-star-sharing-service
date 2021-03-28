package edu.cnm.deepdive.northstarsharing.service;

import edu.cnm.deepdive.northstarsharing.model.dao.CelestialObjectRepository;
import edu.cnm.deepdive.northstarsharing.model.entity.CelestialObject;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class CelestialObjectService {

  private final CelestialObjectRepository celestialObjectRepository;
  private final StorageService storageService;

  public CelestialObjectService(
      CelestialObjectRepository celestialObjectRepository,
      StorageService storageService) {
    this.celestialObjectRepository = celestialObjectRepository;
    this.storageService = storageService;
  }

  public CelestialObject newCelestialObject(CelestialObject celestialObject) {
    celestialObject.getName();
    return celestialObjectRepository.save(celestialObject);
  }

  public Optional<CelestialObject> getById(UUID id) {
    return celestialObjectRepository.findById(id);
  }

  public Iterable<CelestialObject> getByName(String name) {
    return celestialObjectRepository.findCelestialObjectsByNameOrderByNameAsc(name);
  }

  public Resource getObservable(@NonNull CelestialObject celestialObject)
      throws IOException {
    return storageService.find(celestialObject.getName());
  }
}
