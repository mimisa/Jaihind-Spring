package jahind.service;

import jahind.entity.Poll;

/**
 * Created by Gaurav on 11-12-2015.
 */
public interface PollService {

    Poll create(Poll poll);

    Poll poll(Poll poll);

    Poll findOne(Long poll_id);

    void delete(Long poll_id);
}
