package jahind.repository;

import jahind.entity.Poll;
import jahind.entity.Poll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Gaurav on 14-12-2015.
 */
@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {

    Page<Poll> findAll(Pageable pageable);

    Page<Poll> findByCategory(Pageable pageable, String category);

    Page<Poll> findByPublished(Pageable pageable, Integer published);

    Page<Poll> findByCategoryAndPublished(Pageable pageable, String category, Integer published);
}
