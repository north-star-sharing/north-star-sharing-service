package edu.cnm.deepdive.northstarsharing.model.dao;

import edu.cnm.deepdive.northstarsharing.model.entity.Gallery;
import edu.cnm.deepdive.northstarsharing.model.entity.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GalleryRepository extends JpaRepository<User, UUID> {

  Iterable<Gallery> getAllByOrderByTitleAsc();

}
