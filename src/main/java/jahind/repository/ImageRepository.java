package jahind.repository;

import jahind.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Gaurav on 30/11/15.
 */
public interface ImageRepository extends JpaRepository<Image, Long> {
}
