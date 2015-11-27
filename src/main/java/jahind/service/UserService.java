package jahind.service;

import jahind.entity.User;

/**
 * Created by Gaurav on 26/11/15.
 */
public interface UserService {

    User findOne(Integer id);

    User save(User user);
}
