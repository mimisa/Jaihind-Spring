package jahind.controller;

import jahind.entity.Article;
import jahind.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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


    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Resource<Article>> getArticles() {
        Collection<Article> articles = articleService.findAll();
        List<Resource<Article>> resources = new ArrayList<Resource<Article>>();

        for (Article article : articles) {
            resources.add(getArticleResource(article));
        }
        return resources;
    }

    @RequestMapping(value = "/{article_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource<Article> getArticle(@PathVariable(value = "article_id") long article_id) {

        Article article = articleService.findOne(article_id);
        if (article == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND);
        }
        return getArticleResource(article);
    }


    // Insert Article
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Article> createArtilce(@RequestBody Article article) {
        article.setArticle_created(new Date());
        Article savedArticle = articleService.create(article);
        article.add(linkTo(methodOn(ArticleController.class).getArticle(savedArticle.getArticle_id()))
                .withSelfRel());
        return new ResponseEntity<Article>(savedArticle, HttpStatus.CREATED);
    }

    private Resource<Article> getArticleResource(Article article) {

        Resource<Article> resource = new Resource<Article>(article);
        // Link to Article
        resource.add(linkTo(methodOn(ArticleController.class).getArticle(article.getArticle_id())).withSelfRel());
        return resource;

    }


}
