package jahind.repository;

import jahind.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Gaurav on 10-12-2015.
 */

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
}
