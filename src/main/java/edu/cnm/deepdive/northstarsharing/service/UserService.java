package edu.cnm.deepdive.northstarsharing.service;

import edu.cnm.deepdive.northstarsharing.model.dao.UserRepository;
import edu.cnm.deepdive.northstarsharing.model.entity.User;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

/**
 * Implements high-level operations on {@link User} instances, including automatic creation on
 * OpenID token verification, inclusion of an instance as the value returned by {@link
 * Authentication#getPrincipal()}, and delegation to methods declared in {@link UserRepository}.
 */
@Service
public class UserService implements Converter<Jwt, UsernamePasswordAuthenticationToken> {

  private final UserRepository repository;

  /**
   * Initializes this service with an injected {@link UserRepository}.
   *
   * @param repository Spring Data repository used for CRUD operations.
   */
  @Autowired
  public UserService(UserRepository repository) {
    this.repository = repository;
  }

  /**
   * Retrieves an instance of {@link User} with the specified {@code oauthKey}; if none exists,
   * creates and persists a new instance.
   *
   * @param oauthKey    OpenID unique identifier.
   * @param displayName Name used for display (not identification) purposes.
   * @return Created or retrieved instance of {@link User}.
   */
  public User getOrCreate(String oauthKey, String displayName) {
    return repository.findFirstByOauthKey(oauthKey)
        .map((user) -> {
          user.setConnected(new Date());
          return repository.save(user);
        })
        .orElseGet(() -> {
          User user = new User();
          user.setOauthKey(oauthKey);
          user.setDisplayName(displayName);
          user.setConnected(new Date());
          return repository.save(user);
        });
  }

  /**
   * Selects and returns a {@link User} with the specified {@code id}, as the content of an {@link
   * Optional Optional&lt;User&gt;}. If no such instance exists, the {@link Optional} is empty.
   *
   * @param id Unique identifier of the {@link User}.
   * @return {@link Optional Optional&lt;User&gt;} containing the selected user.
   */
  public Optional<User> get(UUID id) {
    return repository.findById(id);
  }

  @Override
  public UsernamePasswordAuthenticationToken convert(Jwt jwt) {
    Collection<SimpleGrantedAuthority> grants =
        Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    return new UsernamePasswordAuthenticationToken(
        getOrCreate(jwt.getSubject(),
            jwt.getClaim("name")),
        jwt.getTokenValue(),
        grants);
  }

}
