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

  @Autowired
  public ImageService(StorageService storageService, ImageRepository imageRepository) {
    this.imageRepository = imageRepository;
    this.storageService = storageService;
  }

  public Optional<Image> getById(@NonNull UUID id) {
    return imageRepository.findById(id);
  }

  public Resource getContent(@NonNull Image image) throws IOException {
    return storageService.retrieve(image.getKey());
  }

  public Image save(@NonNull Image image) throws IOException {
    return imageRepository.save(image);
  }

  public Iterable<Image> listByCreated() {
    return imageRepository.getAllByOrderByCreatedDesc();
  }

  public void delete(@NonNull Image image) throws IOException {
    storageService.delete(image.getKey());
    imageRepository.delete(image);
  }

  public Image store(@NonNull MultipartFile file, String title, String caption,
      @NonNull User user) throws IOException, HttpMediaTypeException {
    String originalFilename = file.getOriginalFilename();
    String contentType = file.getContentType();
    String key = storageService.store(file);
    Image image = new Image();
    image.setTitle(title);
    image.setDescription(caption);
    image.setName((originalFilename) != null ? originalFilename : UNTITLED_FILE);
    image.setContentType((contentType != null)
        ? contentType
        : MediaType.APPLICATION_OCTET_STREAM_VALUE);
    image.setKey(key);
    return save(image);

  }

}

