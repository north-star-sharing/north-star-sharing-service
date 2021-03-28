package edu.cnm.deepdive.northstarsharing.service;

import edu.cnm.deepdive.northstarsharing.model.dao.CelestialObjectRepository;
import org.springframework.stereotype.Service;

@Service
public class CelestialObjectService {

  private final CelestialObjectRepository celestialObjectRepository;

  public CelestialObjectService(
      CelestialObjectRepository celestialObjectRepository) {
    this.celestialObjectRepository = celestialObjectRepository;
  }
}
