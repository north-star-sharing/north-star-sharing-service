package edu.cnm.deepdive.northstarsharing.service;

import edu.cnm.deepdive.northstarsharing.model.dao.GalleryRepository;
import edu.cnm.deepdive.northstarsharing.model.entity.Gallery;
import edu.cnm.deepdive.northstarsharing.model.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

/**
 * Implements high-level operations on {@link Gallery} instances, including delegation to methods
 * declared in {@link GalleryService}.
 */
@Service
public class GalleryService {

  private final GalleryRepository galleryRepository;

  /**
   * Initializes this instance with the provided instances of {@link GalleryRepository}.
   *
   * @param galleryRepository Spring Data repository providing CRUD operations on {@link Gallery}
   *                          instances.
   */
  public GalleryService(GalleryRepository galleryRepository) {
    this.galleryRepository = galleryRepository;
  }

  /**
   * Persists (creates or updates) the specified {@link Gallery} instance to the database, updating
   * and returning the instance accordingly. (The instance is updated in-place, but the reference to
   * it is also returned.)
   *
   * @param gallery Instance to be persisted.
   * @return Updated instance.
   */
  public Gallery saveGallery(@NonNull Gallery gallery) {
    return galleryRepository.save(gallery);
  }

  /**
   * Selects and returns a {@link Gallery} with the specified {@code id}, as the content of an
   * {@link Optional Optional&lt;Gallery&gt;}. If no such instance exists, the {@link Optional} is
   * empty.
   *
   * @param id Unique identifier of the {@link Gallery}.
   * @return {@link Optional Optional&lt;Gallery&gt;} containing the selected gallery.
   */
  public Optional<Gallery> getById(UUID id) {
    return galleryRepository.findById(id);
  }

  /**
   * Returns all {@link Gallery Galleries} as the content of an {@link Iterable
   * Iterable&lt;Gallery&gt;}.
   *
   * @return {@link Iterable Iterable&lt;Gallery&gt;} containing all galleries.
   */
  public Iterable<Gallery> getAll() {
    return galleryRepository.findAll();
  }

}
