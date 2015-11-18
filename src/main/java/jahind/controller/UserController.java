package jahind.controller;

import jahind.entity.User;
import jahind.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Gaurav on 18/11/15.
 */
@RestController
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping("/api/users")
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }
}


