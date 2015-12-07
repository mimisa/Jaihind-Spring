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
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    // Display article based on category- url param
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, params = "category")
    public String getData(@RequestParam("category") String category,
                          Pageable pageable, PagedResourcesAssembler assembler, HttpServletRequest req) throws Exception {
        Page<Article> articles = articleService.findByCatgory(pageable, category);
        JSONArray jsonArray = new JSONArray();
        for (Article article : articles) {
            List<Article_Image> aiList = article.getArticleImageList();
            long image_id = 0L;
            for (Article_Image ai : aiList) {
                Image image = ai.getImage_id();
                image_id = image.getImage_id();
            }
            String IP = req.getServerName();
            int Port = req.getServerPort();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("article_id", article.getArticle_id());
            jsonObject.put("article_name", article.getArticle_name());
            jsonObject.put("article_content", article.getArticle_content());
            jsonObject.put("article_created", article.getCreated());
            jsonObject.put("category", article.getCategory());
            jsonObject.put("article_published", article.getArticle_published());
            jsonObject.put("published_date", article.getPublished_date());
            jsonObject.put("link", "http://" + IP + ":" + Port + "/Jaihind/api/articles/" + article.getArticle_id());
            jsonObject.put("user_name", article.getUser().getName());
            if (image_id != 0L) {
                jsonObject.put("image_link", "http://" + IP + ":" + Port + "/Jaihind/api/images/" + image_id);
            } else {
                jsonObject.put("image_link", "NA");
            }

            jsonArray.put(jsonObject);
        }

        return jsonArray.toString();


    }

    // Fetch All Articles
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getArticles(Pageable pageable, PagedResourcesAssembler assembler, HttpServletRequest req)
            throws Exception {
        Page<Article> articles = articleService.findAll(pageable);
        JSONArray jsonArray = new JSONArray();

        for (Article article : articles) {
            List<Article_Image> aiList = article.getArticleImageList();
            long image_id = 0L;
            for (Article_Image ai : aiList) {
                Image image = ai.getImage_id();
                image_id = image.getImage_id();
            }
            String IP = req.getServerName();
            int Port = req.getServerPort();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("article_id", article.getArticle_id());
            jsonObject.put("article_name", article.getArticle_name());
            jsonObject.put("article_content", article.getArticle_content());
            jsonObject.put("article_created", article.getCreated());
            jsonObject.put("category", article.getCategory());
            jsonObject.put("article_published", article.getArticle_published());
            jsonObject.put("published_date", article.getPublished_date());
            jsonObject.put("link", "http://" + IP + ":" + Port + "/Jaihind/api/articles/" + article.getArticle_id());
            jsonObject.put("user_name", article.getUser().getName());
            if (image_id != 0L) {
                jsonObject.put("image_link", "http://" + IP + ":" + Port + "/Jaihind/api/images/" + image_id);
            } else {
                jsonObject.put("image_link", "NA");
            }

            jsonArray.put(jsonObject);
        }

        return jsonArray.toString();

        //return assembler.toResource(articles, articleResourceAssembler);
    }

    // Insert Article - payload: name,content
    //@Cross
    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String createArticle(String article_name, String article_content, Long image_id, String category,
                                HttpServletRequest req, @AuthenticationPrincipal User user) throws Exception {
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


        String IP = req.getServerName();
        int Port = req.getServerPort();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("article_name", article.getArticle_name());
        jsonObject.put("article_content", article.getArticle_content());
        jsonObject.put("article_created", article.getCreated());
        jsonObject.put("category", article.getCategory());
        jsonObject.put("article_published", article.getArticle_published());
        jsonObject.put("published_date", article.getPublished_date());
        jsonObject.put("link", "http://" + IP + ":" + Port + "/Jaihind/api/articles/" + article.getArticle_id());
        jsonObject.put("image_link", "http://" + IP + ":" + Port + "/Jaihind/api/images/" + image_id);
        jsonObject.put("user_name", article.getUser().getName());

        return jsonObject.toString();
        //article.add(linkTo(methodOn(ArticleController.class).getArticle(savedArticle.getArticle_id())).withSelfRel());

        //return new ResponseEntity<Article>(savedArticle, HttpStatus.CREATED);
    }

    // Fetch Article based on article_id
    @RequestMapping(value = "/{article_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getArticle(@PathVariable(value = "article_id") long article_id, HttpServletRequest req) throws Exception {

        Article article = articleService.findOne(article_id);
        if (article == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND);
        }
        Resource<Article> resource = new Resource<Article>(article);
        List<Article_Image> aiList = article.getArticleImageList();
        long image_id = 0L;
        for (Article_Image ai : aiList) {
            Image image = ai.getImage_id();
            image_id = image.getImage_id();
        }
        String IP = req.getServerName();
        int Port = req.getServerPort();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("article_name", article.getArticle_name());
        jsonObject.put("article_content", article.getArticle_content());
        jsonObject.put("article_created", article.getCreated());
        jsonObject.put("category", article.getCategory());
        jsonObject.put("article_published", article.getArticle_published());
        jsonObject.put("published_date", article.getPublished_date());
        jsonObject.put("link", "http://" + IP + ":" + Port + "/Jaihind/api/articles/" + article.getArticle_id());
        jsonObject.put("image_link", "http://" + IP + ":" + Port + "/Jaihind/api/images/" + image_id);
        jsonObject.put("user_name", article.getUser().getName());

        // Link to Article
        // resource.add(linkTo(methodOn(ArticleController.class).getArticle(article.getArticle_id())).withSelfRel());

        return jsonObject.toString();
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


}
