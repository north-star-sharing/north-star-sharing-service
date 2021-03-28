package edu.cnm.deepdive.northstarsharing.service;

import edu.cnm.deepdive.northstarsharing.model.dao.ImageRepository;
import edu.cnm.deepdive.northstarsharing.model.entity.Image;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

  private final StorageService storageService;
  private final ImageRepository imageRepository;

  @Autowired
  public ImageService(StorageService storageService, ImageRepository imageRepository) {
    this.storageService = storageService;
    this.imageRepository = imageRepository;
  }

  public Optional<Image> getById(@NonNull UUID id) {
    return imageRepository.findById(id);
  }

  public Iterable<Image> listByCreated() {
    return imageRepository.getAllByOrderByCreatedDesc();
  }

  public void delete(@NonNull Image image) throws IOException {
    storageService.delete(image.getKey());
  }


}

