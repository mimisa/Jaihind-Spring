package jahind.controller;

import jahind.entity.Article;
import jahind.entity.Role;
import jahind.entity.User;
import jahind.repository.RoleRepository;
import jahind.repository.UserRepository;
import jahind.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by Gaurav on 18/11/15.
 */
@RestController
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserService userService;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // FETCH ALL USERS
    @RequestMapping(value = "/api/users")
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    // FETCH USER BASED ON USER_ID
    @RequestMapping(value = "/api/users/{user_id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource<User> getUser(@PathVariable("user_id") Integer user_id) {
        User user = userRepository.findOne(user_id);
        Resource<User> resource = new Resource<User>(user);
        resource.add(linkTo(methodOn(UserController.class).getUser(user_id)).withSelfRel());


        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            resource.add(linkTo(methodOn(RoleController.class).getRole(role.getId()))
                    .withRel("Role: " + role.getName()));
        }

        List<Article> articles = user.getArticles();
        for (Article a : articles) {
            resource.add(linkTo(methodOn(ArticleController.class).getArticle(a.getArticle_id())).withRel("Article"));
        }
        return resource;
    }


    // INSERT A USER
    @RequestMapping(value = "/api/users", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource<User> insertUser(@RequestBody User user, @RequestParam String role_name) {

        Role role = roleRepository.findByName(role_name);
        Set<Role> roles = new HashSet<Role>();
        roles.add(role);

        user.setRoles(roles);

        User savedUser = userRepository.save(user);
        Set<User> users = new HashSet<User>();
        users.add(user);
        role.setUsers(users);
        roleRepository.save(role);

        Resource<User> resource = new Resource<User>(savedUser);
        resource.add(linkTo(methodOn(UserController.class).getUser(savedUser.getId())).withSelfRel());

        roles = this.getRoles(savedUser);
        for (Role r : roles) {
            // resource.add(linkTo(methodOn(RoleController.class).getRole(r.getId())).withRel("Role: "
            // + r.getName()));
        }

        List<Article> articles = user.getArticles();
        for (Article a : articles) {
            resource.add(linkTo(methodOn(UserController.class).getArticle(user.getId(), a.getArticle_id()))
                    .withRel("Article"));
        }
        return resource;

    }

    private Set<Role> getRoles(User savedUser) {
        return savedUser.getRoles();
    }

    // FETCH AN ARTICLES OF A PARTICULAR USER
    @RequestMapping(value = "/api/users/{user_id}/articles/{article_id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Article> getArticle(@PathVariable("user_id") Integer user_id,
                                              @PathVariable("article_id") Long article_id) {

        ResponseEntity<Article> entity = null;
        Resource<Article> resource = null;

        User user = userService.findOne(user_id);
        List<Article> articles = user.getArticles();
        if (!articles.isEmpty()) {
            for (Article a : articles) {
                entity = new ResponseEntity<Article>(a, HttpStatus.OK);
            }
        }
        return entity;
    }
}


