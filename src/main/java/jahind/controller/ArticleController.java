package jahind.controller;

import jahind.assembler.ArticleResourceAssembler;
import jahind.entity.Article;
import jahind.entity.Article_Image;
import jahind.entity.Image;
import jahind.entity.User;
import jahind.repository.ArticleImageRepository;
import jahind.service.ArticleImageService;
import jahind.service.ArticleService;
import jahind.service.ImageService;
import jahind.service.UserService;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

//import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;

/**
 * Created by Gaurav on 17/11/15.
 */
@RestController
@RequestMapping(value = "/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ArticleImageService articleImageService;

    @Autowired
    private ArticleImageRepository articleImageRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleResourceAssembler articleResourceAssembler;

    // Fetch All Articles
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public PagedResources<Article> getArticles(Pageable pageable, PagedResourcesAssembler assembler) {
        Page<Article> articles = articleService.findAll(pageable);

        return assembler.toResource(articles, articleResourceAssembler);
    }

    // Insert Article - payload: name,content
    //@Cross
    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Article> createArticle(String article_name, String article_content, Long image_id,
                                                 String category, @AuthenticationPrincipal User user){
        User user1 = userService.findOne(1);

        // Creating Article
        Article article = new Article();
        article.setArticle_name(article_name);
        article.setArticle_content(article_content);
        article.setCreated(new Date());
        article.setArticle_published(0);
        article.setPublished_date(null);
        article.setCategory(category);

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

        // Fetch Image
        List<Article_Image> articleImageList = null;
        Article_Image ai = new Article_Image();
        if (imageService.findOne(image_id) == null) {
            article.setArticleImageList(null);
        } else {
            Image image = imageService.findOne(image_id);
            if (article.getArticleImageList() == null) {
                articleImageList = new ArrayList<>();
            } else {
                articleImageList = article.getArticleImageList();
            }
            ai.setArticle_id(article);
            ai.setImage_id(image);


            articleImageList.add(ai);

        }

        // Save Article
        article.setArticleImageList(articleImageList);
        Article savedArticle = articleService.create(article);

        userService.save(user1);

        articleImageService.save(ai);

        article.add(linkTo(methodOn(ArticleController.class).getArticle(savedArticle.getArticle_id())).withSelfRel());

        return new ResponseEntity<Article>(savedArticle, HttpStatus.CREATED);
    }

    // Fetch Article based on article_id
    @RequestMapping(value = "/{article_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource<Article> getArticle(@PathVariable(value = "article_id") long article_id){

        Article article = articleService.findOne(article_id);
        if (article == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND);
        }
        Resource<Article> resource = new Resource<Article>(article);

        // Link to Article
        resource.add(linkTo(methodOn(ArticleController.class).getArticle(article.getArticle_id())).withSelfRel());

        return resource;
    }

    // Delete Article based on article_id
    @RequestMapping(value = "{article_id}", method = RequestMethod.DELETE)
    public ResponseEntity<Article> deleteArticle(@PathVariable("article_id") Long article_id,
                                                 @AuthenticationPrincipal User user) {
        if (articleService.findOne(article_id) == null) {
            return new ResponseEntity<Article>(HttpStatus.NOT_FOUND);
        }

        articleService.delete(article_id);
        return new ResponseEntity<Article>(HttpStatus.OK);
    }

    //Publish Article based on article_id
    @RequestMapping(value = "{article_id}/publish",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Article> publishArticle(@PathVariable("article_id") Long article_id,
                                                  @AuthenticationPrincipal User user) {
        if (articleService.findOne(article_id) == null) {
            return new ResponseEntity<Article>(HttpStatus.NOT_FOUND);
        }

        Article article = articleService.findOne(article_id);
        article.setArticle_published(1);
        article.setPublished_date(new Date());

        Article updatedArticle = articleService.create(article);

        return new ResponseEntity<Article>(updatedArticle, HttpStatus.OK);
    }


    // Display article based on category- url param
    @RequestMapping(value = "{category}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getArticleByCategory(@RequestParam("category") String category) throws JSONException {
        JSONObject outputJsonObj = new JSONObject();
        outputJsonObj.put("Key", category);

        return outputJsonObj.toString();
    }
}
