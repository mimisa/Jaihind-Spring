package jahind.controller;

import jahind.entity.Article;
import jahind.entity.User;
import jahind.service.ArticleService;
import jahind.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by Gaurav on 17/11/15.
 */
@RestController
@RequestMapping(value = "/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String hello() {
        return "Article Resource";
    }

    // Insert Article - payload: name,content
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Article> createArticle(@RequestBody Article article, @AuthenticationPrincipal User user) {
        User user1 = userService.findOne(1);

        article.setCreated(new Date());
        article.setUser(user1);
        List<Article> articles = null;
        if (user1.getArticles().isEmpty() || user1.getArticles().size() == 0) {
           articles = new ArrayList<>();
            articles.add(article);
            user1.setArticles(articles);

        } else {
            articles = user1.getArticles();
            articles.add(article);
            user1.setArticles(articles);

        }

        Article savedArticle = articleService.create(article);

        userService.save(user1);

        //Article savedArticle =  articleService.create(article, user1);
        // article.add(linkTo(methodOn(ArticleController.class).getArticle(savedArticle.getArticle_id())).withSelfRel());
        return new ResponseEntity<Article>(savedArticle, HttpStatus.CREATED);
    }

    // Testing
    // fetch article based on article_id
    @RequestMapping(value = "/{article_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource<Article> getArticle(@PathVariable(value = "article_id") long article_id) {

        Article article = articleService.findOne(article_id);
        if (article == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND);
        }
        Resource<Article> resource = new Resource<Article>(article);
        // Link to Article
        resource.add(linkTo(methodOn(ArticleController.class).getArticle(article.getArticle_id())).withSelfRel());

        return resource;
    }

}
