package edu.cnm.deepdive.northstarsharing.service;

import edu.cnm.deepdive.northstarsharing.model.dao.GalleryRepository;
import edu.cnm.deepdive.northstarsharing.model.entity.Gallery;
import edu.cnm.deepdive.northstarsharing.model.entity.User;
import java.util.Optional;
import java.util.UUID;
import jdk.jfr.Name;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class GalleryService {

  private final GalleryRepository galleryRepository;

  public GalleryService(GalleryRepository galleryRepository) {
    this.galleryRepository = galleryRepository;
  }

  public Gallery newGallery(Gallery gallery, User contributor) {
    gallery.setUser(contributor);
    return galleryRepository.save(gallery);
  }

  public Gallery saveGallery(@NonNull Gallery gallery) {
    return galleryRepository.save(gallery);
  }

  public Optional<Gallery> getById(UUID id) {
    return galleryRepository.findById(id);
  }

  public Iterable<Gallery> getAll() {
    return galleryRepository.findAll();
  }
}
