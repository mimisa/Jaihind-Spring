package jahind.repository;

import jahind.entity.Media_Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Gaurav on 16/11/15.
 */

@Repository
public interface MediaTypeRepository extends JpaRepository<Media_Type, Long> {

}
