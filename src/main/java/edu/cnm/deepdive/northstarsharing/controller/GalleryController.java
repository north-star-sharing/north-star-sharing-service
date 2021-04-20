package edu.cnm.deepdive.northstarsharing.controller;

import edu.cnm.deepdive.northstarsharing.model.entity.Gallery;
import edu.cnm.deepdive.northstarsharing.service.GalleryService;
import edu.cnm.deepdive.northstarsharing.service.ImageService;
import java.util.UUID;
import org.springframework.context.annotation.Profile;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles REST requests for operations on individual instances of galleries of the
 * {@link edu.cnm.deepdive.northstarsharing.model.entity.Gallery} type and collections of the
 * {@link edu.cnm.deepdive.northstarsharing.model.entity.Image} type.
 */
@RestController
@RequestMapping("/galleries")
@ExposesResourceFor(Gallery.class)
@Profile("service")
public class GalleryController {

  private final GalleryService galleryService;
  private final ImageService imageService;

  /**
   * Initializes this instance with the {@link GalleryService} and {@link ImageService} instances used
   * to perform the requested operations.
   *
   * @param galleryService  Provides access to high-level query operations on {@link Gallery} instances.
   * @param imageService Provides access to high-level query &amp; persistence operations on {@link
   *                     edu.cnm.deepdive.northstarsharing.model.entity.Image} instances.
   */
  public GalleryController(GalleryService galleryService, ImageService imageService) {
    this.galleryService = galleryService;
    this.imageService = imageService;
  }

  /**
   * Add or remove an {@link edu.cnm.deepdive.northstarsharing.model.entity.Image} from a specified
   * {@link Gallery}.
   *
   * @param galleryId Unique identifier of {@link Gallery} resource.
   * @param imageId Unique identifier of {@link edu.cnm.deepdive.northstarsharing.model.entity.Image} resource.
   * @param imageInGallery Flag indicating whether the {@link edu.cnm.deepdive.northstarsharing.model.entity.Image} is to be added or removed from the {@link Gallery}.
   * @param authentication Authentication token with {@link edu.cnm.deepdive.northstarsharing.model.entity.User} principal.
   * @return Flag indicating whether the {@link edu.cnm.deepdive.northstarsharing.model.entity.Image} was added or removed.
   */
  @GetMapping(value = "/{galleryId}/images/{imageId}",
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
                  return galleryService.saveGallery(gallery);
                })
        )
        .map((gallery) -> imageInGallery)
        .orElseThrow();
  }
}
