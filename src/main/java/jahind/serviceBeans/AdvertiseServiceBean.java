package jahind.serviceBeans;

import jahind.entity.Advertise;
import jahind.repository.AdvertiseRepository;
import jahind.service.AdvertiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Gaurav on 14-12-2015.
 */

@Service
public class AdvertiseServiceBean implements AdvertiseService {

    @Autowired
    private AdvertiseRepository advertiseRepository;

    @Override
    public Advertise save(Advertise advertise) {
        return advertiseRepository.save(advertise);
    }

    @Override
    public Advertise findOne(Long advertise_id) {
        return advertiseRepository.findOne(advertise_id);
    }
}
