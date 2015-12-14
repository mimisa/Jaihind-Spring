package jahind.service;

import jahind.entity.Poll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Gaurav on 14-12-2015.
 */
public interface PollService {

    Page<Poll> findAll(Pageable pageable);

    Page<Poll> findByCatgory(Pageable pageable, String category);

    Page<Poll> findPublsihedPolls(Pageable pageable, Integer published);

    Page<Poll> findByCategoryAndPublished(Pageable pageable, String category, Integer published);

    //Collection<Poll> findAll();

    Poll findOne(Long id);

    Poll create(Poll poll);

    Poll publish(Poll poll);

    Poll poll(Poll poll);

    // Poll create(Poll Poll, User user);

    void delete(Poll poll);

    void delete(Long Poll_id);
}
