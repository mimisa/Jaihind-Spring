package jahind.controller;

import jahind.entity.Role;
import jahind.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by Gaurav on 19-11-2015.
 */
@RestController
public class RoleController {

    @Autowired
    RoleRepository roleRepository;

    @RequestMapping(value = "/api/roles/{role_id}", method = RequestMethod.GET, produces = MediaType
            .APPLICATION_JSON_VALUE)
    public Resource<Role> getRole(@PathVariable(value = "role_id") Integer role_id) {
        Role role = roleRepository.findOne(role_id);
        Resource<Role> resource = new Resource<Role>(role);
        resource.add(linkTo(methodOn(RoleController.class).getRole(role.getId())).withSelfRel());
        return resource;
    }
}
