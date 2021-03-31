package edu.cnm.deepdive.northstarsharing.controller;

import edu.cnm.deepdive.northstarsharing.model.entity.Gallery;
import edu.cnm.deepdive.northstarsharing.service.GalleryService;
import edu.cnm.deepdive.northstarsharing.service.ImageService;
import java.util.UUID;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/galleries")
@ExposesResourceFor(Gallery.class)
public class GalleryController {

  private final GalleryService galleryService;
  private final ImageService imageService;

  public GalleryController(GalleryService galleryService,
      ImageService imageService) {
    this.galleryService = galleryService;
    this.imageService = imageService;
  }

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
