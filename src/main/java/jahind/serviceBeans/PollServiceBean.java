package jahind.serviceBeans;

import jahind.entity.Poll;
import jahind.repository.PollRepository;
import jahind.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Gaurav on 11-12-2015.
 */

@Service
public class PollServiceBean implements PollService {

    @Autowired
    private PollRepository pollRepository;

    @Override
    public Poll create(Poll poll) {
        return pollRepository.save(poll);
    }

    @Override
    public Poll poll(Poll poll) {
        return pollRepository.save(poll);
    }

    @Override
    public Poll findOne(Long poll_id) {
        return pollRepository.findOne(poll_id);
    }

    @Override
    public void delete(Long poll_id) {
        pollRepository.delete(poll_id);
    }
}
