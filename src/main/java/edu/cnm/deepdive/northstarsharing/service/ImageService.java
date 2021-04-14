package edu.cnm.deepdive.northstarsharing.service;

import edu.cnm.deepdive.northstarsharing.model.dao.ImageRepository;
import edu.cnm.deepdive.northstarsharing.model.entity.Image;
import edu.cnm.deepdive.northstarsharing.model.entity.User;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

  private static final String UNTITLED_FILE = "Filename missing";

  private final ImageRepository imageRepository;
  private final StorageService storageService;

  /**
   * Create an ImageService.
   *
   * @param imageRepository a database ORM for managing data persistence
   * @param storageService a service for managing storing image for upload
   */
  @Autowired
  public ImageService(ImageRepository imageRepository, StorageService storageService) {
    this.imageRepository = imageRepository;
    this.storageService = storageService;
  }

  /**
   * Retrieve a specific image using it's database ID.
   *
   * @param id
   * @return
   */
  public Optional<Image> getById(@NonNull UUID id) {
    return imageRepository.findById(id);
  }

  /**
   * Return the resource corresponding to an image.
   *
   * @param image
   * @return
   * @throws IOException
   */
  public Resource getContent(@NonNull Image image) throws IOException {
    return storageService.retrieve(image.getKey());
  }

  /**
   * Persist the image's information in the database.
   *
   * @param image
   * @return
   * @throws IOException
   */
  public Image save(@NonNull Image image) throws IOException {
    return imageRepository.save(image);
  }

  /**
   * Retrieve image information from the database repository.
   *
   * @return
   */
  public Iterable<Image> listByCreated() {
    return imageRepository.getAllByOrderByCreatedDesc();
  }

  /**
   * Delete an image from the database and the file storage.
   *
   * @param image
   * @throws IOException
   */
  public void delete(@NonNull Image image) throws IOException {
    storageService.delete(image.getKey());
    imageRepository.delete(image);
  }

  /**
   * Store an image to the file storage.
   *
   * @param file
   * @param title
   * @param description
   * @param user
   * @return
   * @throws IOException
   * @throws HttpMediaTypeException
   */
  public Image store(@NonNull MultipartFile file, String title, String description,
      @NonNull User user) throws IOException, HttpMediaTypeException {
    String originalFilename = file.getOriginalFilename();
    String contentType = file.getContentType();
    String key = storageService.store(file);
    Image image = new Image();
    image.setTitle(title);
    image.setDescription(description);
    image.setName((originalFilename) != null ? originalFilename : UNTITLED_FILE);
    image.setContentType((contentType != null)
        ? contentType
        : MediaType.APPLICATION_OCTET_STREAM_VALUE);
    image.setKey(key);
    return save(image);

  }

}

