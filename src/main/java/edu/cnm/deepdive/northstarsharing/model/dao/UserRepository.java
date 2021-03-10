package edu.cnm.deepdive.northstarsharing.model.dao;

import edu.cnm.deepdive.northstarsharing.model.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

  Optional<User> findFirstByOauthKey(String oauthKey);

}
