package edu.cnm.deepdive.northstarsharing.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.cnm.deepdive.northstarsharing.model.entity.Gallery;
import edu.cnm.deepdive.northstarsharing.model.entity.Image;
import edu.cnm.deepdive.northstarsharing.model.entity.User;
import edu.cnm.deepdive.northstarsharing.service.ImageService;
import edu.cnm.deepdive.northstarsharing.views.ImageViews;
import java.io.IOException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Handles REST requests for operations on individual instances of the {@link Image} type.
 */
@RestController
@RequestMapping("/images")
@ExposesResourceFor(Image.class)
@Profile("service")
public class ImageController {

  private final ImageService imageService;

  /**
   * Initializes this instance with the {@link ImageService} instances used to perform the requested
   * operations.
   *
   * @param imageService Provides access to high-level query &amp; persistence operations on {@link
   *                     Image} instances.
   */
  @Autowired
  public ImageController(ImageService imageService) {
    this.imageService = imageService;
  }

  /**
   * Stores uploaded file content along with a new {@link Image} instance referencing the content.
   *
   * @param file           MIME content of single file upload.
   * @param title          Summary of uploaded content.
   * @param description    (Optional) Detailed description of uploaded content.
   * @param authentication Authentication token with {@link User} principal.
   * @return Instance of {@link Image} created &amp; persisted for the uploaded content.
   */
  @PostMapping(
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Image> post(
      @RequestParam MultipartFile file,
      @RequestParam(required = true) String title,
      @RequestParam(required = false) String description,
      @RequestParam(required = true) String azimuth,
      @RequestParam(required = true) String pitch,
      @RequestParam(required = true) String roll,
      @RequestParam(required = true) String latitude,
      @RequestParam(required = true) String longitude,
      Authentication authentication,
      Gallery gallery) throws IOException, HttpMediaTypeException {
    Image image = (imageService.store(
        file,
        title,
        description,
        Float.parseFloat(azimuth),
        Float.parseFloat(pitch),
        Float.parseFloat(roll),
        Double.parseDouble(latitude),
        Double.parseDouble(longitude),
        (User) authentication.getPrincipal(),
        gallery));
    return ResponseEntity.created(image.getHref()).body(image);
  }

  /**
   * Returns the file content of the specified {@link Image} resource. The original filename of the
   * image is included in the {@code filename} portion of the {@code content-disposition} response
   * header, while the MIME type is returned in the {@code content-type} header.
   *
   * @param id             Unique identifier of {@link Image} resource.
   * @param authentication Authentication token with {@link User} principal.
   * @return Image content.
   */
  @GetMapping("/{id}/content")
  public ResponseEntity<Resource> getContent(@PathVariable UUID id, Authentication authentication) {
    return imageService
        .getById(id)
        .map((image) -> {
          try {
            Resource resource = imageService.getContent(image);
            return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                    String.format("File attachment: Filename=\"%s\"",
                        image.getName()))
                .header(HttpHeaders.CONTENT_LENGTH,
                    String.valueOf(resource.contentLength()))
                .header(HttpHeaders.CONTENT_TYPE, image.getContentType())
                .body(resource);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        })
        .orElseThrow();
  }

  @JsonView(ImageViews.Hierarchical.class)
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Image get(@PathVariable UUID id, Authentication auth) {
    return imageService
        .getById(id)
        .orElseThrow();
  }

  @JsonView(ImageViews.Flat.class)
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Image> list(Authentication auth) {
    return imageService.listByCreated();
  }
}
