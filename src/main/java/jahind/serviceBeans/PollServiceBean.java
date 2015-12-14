package jahind.serviceBeans;

import jahind.entity.Poll;
import jahind.repository.PollRepository;
import jahind.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by Gaurav on 14-12-2015.
 */
@Service
public class PollServiceBean implements PollService {

    @Autowired
    private PollRepository pollRepository;

    @Override
    public Page<Poll> findAll(Pageable pageable) {
        return pollRepository.findAll(pageable);
    }

    @Override
    public Page<Poll> findByCatgory(Pageable pageable, String category) {
        return pollRepository.findByCategory(pageable, category);
    }

    @Override
    public Page<Poll> findPublsihedPolls(Pageable pageable, Integer published) {
        return pollRepository.findByPublished(pageable, published);
    }

    @Override
    public Page<Poll> findByCategoryAndPublished(Pageable pageable, String category, Integer published) {
        return pollRepository.findByCategoryAndPublished(pageable, category, published);
    }

    @Override
    public Poll findOne(Long id) {
        return pollRepository.findOne(id);
    }

    @Override
    public Poll create(Poll poll) {
        return pollRepository.save(poll);
    }

    @Override
    public Poll publish(Poll poll) {
        return pollRepository.save(poll);
    }

    @Override
    public Poll poll(Poll poll) {
        return pollRepository.save(poll);
    }

    @Override
    public void delete(Poll poll) {
        pollRepository.delete(poll);

    }

    @Override
    public void delete(Long poll_id) {
        pollRepository.delete(poll_id);
        ;

    }
}
