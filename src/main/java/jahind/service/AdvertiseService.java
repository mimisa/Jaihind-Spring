package jahind.service;

import jahind.entity.Advertise;

/**
 * Created by Gaurav on 14-12-2015.
 */
public interface AdvertiseService {
    Advertise save(Advertise advertise);

    Advertise findOne(Long advertise_id);
}
