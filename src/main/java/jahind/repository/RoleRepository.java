package jahind.repository;

import jahind.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Gaurav on 19-11-2015.
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);
}
