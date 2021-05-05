package edu.cnm.deepdive.northstarsharing.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.cnm.deepdive.northstarsharing.model.entity.Gallery;
import edu.cnm.deepdive.northstarsharing.model.entity.Image;
import edu.cnm.deepdive.northstarsharing.model.entity.User;
import edu.cnm.deepdive.northstarsharing.service.GalleryService;
import edu.cnm.deepdive.northstarsharing.service.ImageService;
import edu.cnm.deepdive.northstarsharing.views.GalleryViews;
import java.io.IOException;
import java.util.UUID;
import org.springframework.context.annotation.Profile;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Handles REST requests for operations on individual instances of galleries of the {@link
 * edu.cnm.deepdive.northstarsharing.model.entity.Gallery} type and collections of the {@link
 * edu.cnm.deepdive.northstarsharing.model.entity.Image} type.
 */
@RestController
@RequestMapping("/galleries")
@ExposesResourceFor(Gallery.class)
@Profile("service")
public class GalleryController {

  private final GalleryService galleryService;
  private final ImageService imageService;

  /**
   * Initializes this instance with the {@link GalleryService} and {@link ImageService} instances
   * used to perform the requested operations.
   *
   * @param galleryService Provides access to high-level query operations on {@link Gallery}
   *                       instances.
   * @param imageService   Provides access to high-level query &amp; persistence operations on
   *                       {@link edu.cnm.deepdive.northstarsharing.model.entity.Image} instances.
   */
  public GalleryController(GalleryService galleryService, ImageService imageService) {
    this.galleryService = galleryService;
    this.imageService = imageService;
  }

  @JsonView(GalleryViews.Hierarchical.class)
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Gallery> post(
      @RequestBody Gallery gallery,
      Authentication auth) {
    gallery = galleryService.saveGallery(gallery, (User) auth.getPrincipal());
    return ResponseEntity.created(gallery.getHref())
        .body(gallery);
  }

  @JsonView(GalleryViews.Flat.class)
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Gallery> getAll(Authentication auth) {
    return galleryService.getAll();
  }

  @JsonView(GalleryViews.Hierarchical.class)
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Gallery get(@PathVariable UUID id, Authentication auth) {
    return galleryService
        .getById(id)
        .orElseThrow();
  }

  /**
   * Add or remove an {@link edu.cnm.deepdive.northstarsharing.model.entity.Image} from a specified
   * {@link Gallery}.
   *
   * @param galleryId      Unique identifier of {@link Gallery} resource.
   * @param imageId        Unique identifier of {@link edu.cnm.deepdive.northstarsharing.model.entity.Image}
   *                       resource.
   * @param imageInGallery Flag indicating whether the {@link edu.cnm.deepdive.northstarsharing.model.entity.Image}
   *                       is to be added or removed from the {@link Gallery}.
   * @param authentication Authentication token with {@link edu.cnm.deepdive.northstarsharing.model.entity.User}
   *                       principal.
   * @return Flag indicating whether the {@link edu.cnm.deepdive.northstarsharing.model.entity.Image}
   * was added or removed.
   */

  @GetMapping(
      value = "/{galleryId}/images/{imageId}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public boolean setImageGallery(
      @PathVariable UUID galleryId,
      @PathVariable UUID imageId,
      @RequestBody boolean imageInGallery,
      Authentication authentication) {
    return galleryService
        .getById(galleryId)
        .flatMap((gallery) ->
            imageService
                .getById(imageId)
                .map((image) -> {
                  if (imageInGallery) {
                    image.setGallery(gallery);
                    gallery.getImages()
                        .add(image);
                  } else {
                    image.setGallery(null);
                    gallery.getImages()
                        .add(image);
                  }
                  return galleryService.saveGallery(gallery, gallery.getUser());
                })
        )
        .map((gallery) -> imageInGallery)
        .orElseThrow();
  }

  @JsonView(GalleryViews.Hierarchical.class)
  @PostMapping(
      value = "/{galleryId}/images",
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Image> post(
      @PathVariable(required = false) UUID galleryId,
      @RequestParam MultipartFile file,
      @RequestParam(required = true) String title,
      @RequestParam(required = false) String description,
      @RequestParam(required = true) String azimuth,
      @RequestParam(required = true) String pitch,
      @RequestParam(required = true) String roll,
      @RequestParam(required = true) String latitude,
      @RequestParam(required = true) String longitude,
      Authentication auth) throws IOException, HttpMediaTypeException {
    return galleryService
        .getById(galleryId)
        .map((gallery) ->
            securePost(file,
                (User) auth.getPrincipal(),
                gallery,
                title,
                description,
                Float.parseFloat(azimuth),
                Float.parseFloat(pitch),
                Float.parseFloat(roll),
                Double.parseDouble(latitude),
                Double.parseDouble(longitude)))
        .orElseThrow(ImageNotFoundException::new);
  }

  private ResponseEntity<Image> securePost(
      MultipartFile file,
      User user,
      Gallery gallery,
      String title,
      String description,
      float azimuth,
      float pitch,
      float roll,
      double latitude,
      double longitude) {
    try {
      Image image = imageService.store(
          file,
          title,
          description,
          azimuth,
          pitch,
          roll,
          latitude,
          longitude,
          user,
          gallery);
      return ResponseEntity.created(image.getHref())
          .body(image);
    } catch (IOException e) {
      throw new StorageException(e);
    } catch (HttpMediaTypeException e) {
      throw new MimeTypeNotAllowedException();
    }
  }
}
