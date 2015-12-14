package jahind.repository;

import jahind.entity.Advertise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Gaurav on 14-12-2015.
 */
@Repository
public interface AdvertiseRepository extends JpaRepository<Advertise, Long> {
}
