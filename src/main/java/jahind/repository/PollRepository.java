package jahind.repository;

import jahind.entity.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Gaurav on 11-12-2015.
 */

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {
}
