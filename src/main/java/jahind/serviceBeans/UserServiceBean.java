package jahind.serviceBeans;

import jahind.entity.User;
import jahind.repository.UserRepository;
import jahind.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Gaurav on 26/11/15.
 */

@Service
public class UserServiceBean implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findOne(Integer id) {
        return userRepository.findOne(id);
    }

    @Override
    public User save(User user) {
        userRepository.flush();
        return userRepository.save(user);
    }


}
