package jahind.service;

import jahind.entity.Advertise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Gaurav on 14-12-2015.
 */
public interface AdvertiseService {
    Advertise save(Advertise advertise);

    Advertise findOne(Long advertise_id);

    Page<Advertise> findAll(Pageable pageable);

    Page<Advertise> findByPlacement(Pageable pageable, String placement);
}
