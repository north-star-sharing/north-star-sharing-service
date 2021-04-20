package edu.cnm.deepdive.northstarsharing.controller;

import edu.cnm.deepdive.northstarsharing.model.entity.Image;
import edu.cnm.deepdive.northstarsharing.model.entity.User;
import edu.cnm.deepdive.northstarsharing.service.UserService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles REST requests for operations on individual instances of the {@link User} type.
 */
@RestController
@RequestMapping("/users")
@ExposesResourceFor(User.class)
@Profile("service")
public class UserController {

  private final UserService userService;

  /**
   * Initializes this instance with the {@link UserService} instance used to perform the requested
   * operations.
   *
   * @param userService Provides access to high-level query operations on {@link User} instances.
   */
  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Returns the {@link User} principal from the authentication token.
   *
   * @param authentication Authentication token with {@link User} principal.
   */
  @GetMapping(value = "/self", produces = MediaType.APPLICATION_JSON_VALUE)
  public User self(Authentication authentication) {
    return (User) authentication.getPrincipal();
  }

  /**
   * Returns the unique identifier for the {@link User}.
   *
   * @param id             Unique identifier.
   * @param authentication Authentication token with {@link User} principal.
   */
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public User getId(@PathVariable("id") UUID id, Authentication authentication) {
    return userService
        .get(id)
        .orElseThrow();
  }

}
