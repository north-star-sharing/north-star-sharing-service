package edu.cnm.deepdive.northstarsharing.model.dao;

import edu.cnm.deepdive.northstarsharing.model.entity.Gallery;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GalleryRepository extends JpaRepository<Gallery, UUID> {

  Iterable<Gallery> getAllByOrderByTitleAsc();

}
