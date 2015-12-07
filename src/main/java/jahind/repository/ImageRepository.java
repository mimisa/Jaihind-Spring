package jahind.repository;

import jahind.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Gaurav on 30/11/15.
 */
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
