package edu.cnm.deepdive.northstarsharing.service;

import edu.cnm.deepdive.northstarsharing.model.dao.ImageRepository;
import edu.cnm.deepdive.northstarsharing.model.entity.Gallery;
import edu.cnm.deepdive.northstarsharing.model.entity.Image;
import edu.cnm.deepdive.northstarsharing.model.entity.User;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.multipart.MultipartFile;

/**
 * Implements high-level operations on {@link Image} instances, including file store operations and
 * delegation to methods declared in {@link ImageRepository}.
 */
@Service
@Profile("service")
public class ImageService {

  private static final String UNTITLED_FILE = "Filename missing";

  private final ImageRepository imageRepository;
  private final StorageService storageService;

  /**
   * Initializes this instance with the provided instances of {@link ImageRepository} and {@link
   * StorageService}.
   *
   * @param imageRepository Spring Data repository providing CRUD operations on {@link Image}
   *                        instances.
   * @param storageService  File store.
   */
  @Autowired
  public ImageService(ImageRepository imageRepository, StorageService storageService) {
    this.imageRepository = imageRepository;
    this.storageService = storageService;
  }

  /**
   * Selects and returns a {@link Image} with the specified {@code id}, as the content of an {@link
   * Optional Optional&lt;Image&gt;}. If no such instance exists, the {@link Optional} is empty.
   *
   * @param id Unique identifier of the {@link Image}.
   * @return {@link Optional Optional&lt;Image&gt;} containing the selected image.
   */
  public Optional<Image> getById(@NonNull UUID id) {
    return imageRepository.findById(id);
  }

  /**
   * Uses the opaque reference contained in {@code image} to return a consumer-usable {@link
   * Resource} to previously uploaded content.
   *
   * @param image {@link Image} entity instance referencing the uploaded content.
   * @return {@link Resource} usable in a response body (e.g. for downloading).
   * @throws IOException If the file content cannot&mdash;for any reason&mdash;be read from the file
   *                     store.
   */
  public Resource getContent(@NonNull Image image) throws IOException {
    return storageService.retrieve(image.getKey());
  }

  /**
   * Persists (creates or updates) the specified {@link Image} instance to the database, updating
   * and returning the instance accordingly. (The instance is updated in-place, but the reference to
   * it is also returned.)
   *
   * @param image Instance to be persisted.
   * @return Updated instance.
   */
  public Image save(@NonNull Image image) throws IOException {
    return imageRepository.save(image);
  }

  /**
   * Selects and returns all images in date time created (descending) order.
   */
  public Iterable<Image> listByCreated() {
    return imageRepository.getAllByOrderByCreatedDesc();
  }

  /**
   * Deletes the specified {@link Image} instance from the database and the file store. It's assumed
   * that any access control conditions have already been checked.
   *
   * @param image Previously persisted {@link Image} instance to be deleted.
   * @throws IOException If the file cannot be accessed (for any reason) from the specified {@code
   *                     reference}.
   */
  public void delete(@NonNull Image image) throws IOException {
    storageService.delete(image.getKey());
    imageRepository.delete(image);
  }

  /**
   * Stores the image data to the file store, then constructs and returns the corresponding instance
   * of {@link Image}. The latter includes the specified {@code title} and {@code description}
   * metadata, along with a reference to {@code contributor}.
   *
   * @param file        Uploaded file content.
   * @param title       Optional (null is allowed) title of the image.
   * @param description Optional (null is allowed) description of the image.
   * @param user        Uploading {@link User}.
   * @return {@link Image} instance referencing and describing the uploaded content.
   * @throws IOException            If the file content cannot&mdash;for any reason&mdash;be written
   *                                to the file store.
   * @throws HttpMediaTypeException If the MIME type of the uploaded file is not on the whitelist.
   */
  public Image store(@NonNull MultipartFile file, String title, String description,
      float azimuth, float pitch, float roll, double latitude, double longitude,
      @NonNull User user, Gallery gallery) throws IOException, HttpMediaTypeException {
    String originalFilename = file.getOriginalFilename();
    String contentType = file.getContentType();
    String key = storageService.store(file);
    Image image = new Image();
    image.setTitle(title);
    image.setDescription(description);
    image.setAzimuth(azimuth);
    image.setPitch(pitch);
    image.setRoll(roll);
    image.setLatitude(latitude);
    image.setLongitude(longitude);
    image.setUser(user);
    image.setGallery(gallery);
    image.setName((originalFilename) != null ? originalFilename : UNTITLED_FILE);
    image.setContentType((contentType != null)
        ? contentType
        : MediaType.APPLICATION_OCTET_STREAM_VALUE);
    image.setKey(key);
    return save(image);
  }

}

