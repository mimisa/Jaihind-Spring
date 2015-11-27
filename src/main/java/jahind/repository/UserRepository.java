package jahind.repository;

import jahind.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Gaurav on 18/11/15.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByLogin(String login);
    User save(User user);
}
