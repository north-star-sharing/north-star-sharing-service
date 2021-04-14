package edu.cnm.deepdive.northstarsharing.model.dao;

import edu.cnm.deepdive.northstarsharing.model.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Declares custom queries (beyond those declared in {@link JpaRepository}) on {@link User} entity
 * instances.
 */
public interface UserRepository extends JpaRepository<User, UUID> {

  /**
   * Returns an {@link Optional Optional&lt;User&gt;} containing a {@link User} with the specified
   * {@code oauthKey}, if one exists; if not, an empty {@link Optional} is returned.
   *
   * @param oauthKey OpenID unique identifier.
   * @return {@link Optional} containing the selected user, if any; if not, an empty {@link
   * Optional} is returned.
   */
  Optional<User> findFirstByOauthKey(String oauthKey);

}
