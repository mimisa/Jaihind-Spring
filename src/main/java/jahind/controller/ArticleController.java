package jahind.controller;

import jahind.assembler.ArticleResourceAssembler;
import jahind.entity.*;
import jahind.repository.ArticleImageRepository;
import jahind.service.*;
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
import org.springframework.security.core.Authentication;
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
    private PageService pageService;

    @Autowired
    private ArticleImageService articleImageService;

    @Autowired
    private ArticleImageRepository articleImageRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleResourceAssembler articleResourceAssembler;

   /* // Display article based on category- url param
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, params = "category")
    public String getData(@RequestParam("category") String category,
                          Pageable pageable, PagedResourcesAssembler assembler, HttpServletRequest req) throws Exception {
        Page<Article> articles = articleService.findByCatgory(pageable, category);
        return this.getJson(articles, req);
    }*/

    // Fetch All Articles
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getArticles(Pageable pageable, PagedResourcesAssembler assembler, HttpServletRequest req)
            throws Exception {
        Page<Article> articles = articleService.findAll(pageable);
        return this.getJson(articles, req);

        //return assembler.toResource(articles, articleResourceAssembler);
    }

    // Insert Article - payload: name,content
    //@Cross
    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String createArticle(String article_name, String article_content, Long image_id, String category,
                                HttpServletRequest req, @AuthenticationPrincipal User user, Authentication authentication) throws Exception {
//        Integer id = user.getId();
//        User user1 = userService.findOne(id);
        User user1 = userService.findOne(1);
        Page_Entity page = pageService.findByPageName(category);

        // Creating Article
        Article article = new Article();
        article.setArticle_name(article_name);
        article.setArticle_content(article_content);
        article.setCreated(new Date());
        article.setArticle_published(0);
        article.setPublishedDate(null);

        // Setting Category

        List<Article> pagedArticles = null;
        if (page.getArticles().isEmpty() || page.getArticles().size() == 0) {
            pagedArticles = new ArrayList<>();
        } else {
            pagedArticles = page.getArticles();
        }
        pagedArticles.add(article);
        page.setArticles(pagedArticles);
        article.setCategory(category);
        article.setPage(page);

        // Setting User
        article.setUser(user1);
        List<Article> articles = null;
        if (user1.getArticles().isEmpty() || user1.getArticles().size() == 0) {
            articles = new ArrayList<>();
        } else {
            articles = user1.getArticles();
        }

        articles.add(article);
        user1.setArticles(articles);

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

        pageService.save(page);

        userService.save(user1);

        articleImageService.save(ai);

        String IP = req.getServerName();
        int Port = req.getServerPort();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("article_id", savedArticle.getArticle_id());
        jsonObject.put("article_name", savedArticle.getArticle_name());
        jsonObject.put("article_content", savedArticle.getArticle_content());
        jsonObject.put("article_created", savedArticle.getCreated());
        jsonObject.put("category", savedArticle.getCategory());
        jsonObject.put("article_published", savedArticle.getArticle_published());
        jsonObject.put("published_date", savedArticle.getPublishedDate());
        jsonObject.put("link", "http://" + IP + ":" + Port + "/Jaihind/api/articles/" + savedArticle.getArticle_id());
        jsonObject.put("user_name", savedArticle.getUser().getName());
        if (image_id != 0L) {
            jsonObject.put("image_link", "http://" + IP + ":" + Port + "/Jaihind/api/images/" + image_id);
        } else {
            jsonObject.put("image_link", "NA");
        }
        return jsonObject.toString();
    }

    // Fetch Article based on article_id
    @RequestMapping(value = "/{article_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getArticle(@PathVariable(value = "article_id") long article_id, HttpServletRequest req)
            throws Exception {

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
        jsonObject.put("article_id", article.getArticle_id());
        jsonObject.put("article_name", article.getArticle_name());
        jsonObject.put("article_content", article.getArticle_content());
        jsonObject.put("article_created", article.getCreated());
        jsonObject.put("category", article.getCategory());
        jsonObject.put("article_published", article.getArticle_published());
        jsonObject.put("published_date", article.getPublishedDate());
        jsonObject.put("link", "http://" + IP + ":" + Port + "/Jaihind/api/articles/" + article.getArticle_id());
        jsonObject.put("user_name", article.getUser().getName());
        if (image_id != 0L) {
            jsonObject.put("image_link", "http://" + IP + ":" + Port + "/Jaihind/api/images/" + image_id);
        } else {
            jsonObject.put("image_link", "NA");
        }
        // Link to Article
        // resource.add(linkTo(methodOn(ArticleController.class).getArticle(article.getArticle_id())).withSelfRel());

        return jsonObject.toString();
    }

    // Delete Article based on article_id
    @RequestMapping(value = "{article_id}", method = RequestMethod.DELETE)
    public void deleteArticle(@PathVariable("article_id") Long article_id,
                                                 @AuthenticationPrincipal User user) throws Exception{

        if (articleService.findOne(article_id) == null) {
            throw new Exception();
        }

        Article article = articleService.findOne(article_id);
        List<Article_Image> aiList = article.getArticleImageList();
        for (Article_Image ai : aiList) {
            articleImageService.delete(ai);
        }
        articleService.delete(article);

    }

    //Publish Article based on article_id
    @RequestMapping(value = "{article_id}/publish",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String publishArticle(@PathVariable("article_id") Long article_id,
                                                  @AuthenticationPrincipal User user, HttpServletRequest req) throws Exception{
        if (articleService.findOne(article_id) == null) {
            throw new Exception();
        }

        Article article = articleService.findOne(article_id);
        article.setArticle_published(1);
        article.setPublishedDate(new Date());

        List<Article_Image> aiList = article.getArticleImageList();
        long image_id = 0L;
        for (Article_Image ai : aiList) {
            Image image = ai.getImage_id();
            image_id = image.getImage_id();
        }
        Article updatedArticle = articleService.create(article);

        String IP = req.getServerName();
        int Port = req.getServerPort();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("article_id", updatedArticle.getArticle_id());
        jsonObject.put("article_name", updatedArticle.getArticle_name());
        jsonObject.put("article_content", updatedArticle.getArticle_content());
        jsonObject.put("article_created", updatedArticle.getCreated());
        jsonObject.put("category", updatedArticle.getCategory());
        jsonObject.put("article_published", updatedArticle.getArticle_published());
        jsonObject.put("published_date", updatedArticle.getPublishedDate());
        jsonObject.put("link", "http://" + IP + ":" + Port + "/Jaihind/api/articles/" + updatedArticle.getArticle_id());
        jsonObject.put("user_name", updatedArticle.getUser().getName());
        if (image_id != 0L) {
            jsonObject.put("image_link", "http://" + IP + ":" + Port + "/Jaihind/api/images/" + image_id);
        } else {
            jsonObject.put("image_link", "NA");
        }

        return jsonObject.toString();
    }

    // Fetch publised and unpublished articles with pagination and sorting
    @RequestMapping(params = "published", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String fetchPublishedArticles(
            @RequestParam("published") boolean published, Pageable pageable,
            PagedResourcesAssembler assembler, HttpServletRequest req) throws Exception {

        Integer flag = 0;
        if (published == true) {
            flag = 1;
        }
        Page<Article> articles = articleService.findPublsihedArticles(pageable, flag);
        return this.getJson(articles, req);
    }

    // Fetch Articles by category and published with Pagination and sorting
    @RequestMapping(params = {"category", "published"},
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String fetchByCategoriesPublished(
            @RequestParam("category") String category, @RequestParam("published") boolean published, Pageable pageable,
            PagedResourcesAssembler assembler, HttpServletRequest req) throws Exception {
        Integer flag = 0;
        if (published == true) {
            flag = 1;
        }
        Page<Article> articles = articleService.findByCategoryAndPublished(pageable, category, flag);

        return this.getJson(articles, req);
    }

    // Edit Articles
    @RequestMapping(value = "/{article_id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String editArticle(@PathVariable("article_id") Long article_id,
                              String article_name, String article_content, HttpServletRequest req) throws Exception {
        Article article = articleService.findOne(article_id);
        article.setArticle_name(article_name);
        article.setArticle_content(article_content);

        Article savedArticle = articleService.update(article);

        List<Article_Image> aiList = article.getArticleImageList();
        long image_id = 0L;
        for (Article_Image ai : aiList) {
            Image image = ai.getImage_id();
            image_id = image.getImage_id();
        }

        String IP = req.getServerName();
        int Port = req.getServerPort();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("article_name", savedArticle.getArticle_name());
        jsonObject.put("article_content", savedArticle.getArticle_content());
        jsonObject.put("article_created", savedArticle.getCreated());
        jsonObject.put("article_category", savedArticle.getCategory());
        jsonObject.put("article_published", savedArticle.getArticle_published());
        jsonObject.put("published_date", savedArticle.getPublishedDate());
        jsonObject.put("link", "http://" + IP + ":" + Port + "/Jaihind/api/articles/" + savedArticle.getArticle_id());
        jsonObject.put("page", savedArticle.getPage().getPage_id());
        jsonObject.put("image_link", "http://" + IP + ":" + Port + "/Jaihind/api/images/" + image_id);
        jsonObject.put("user_name", savedArticle.getUser().getName());
        if (image_id != 0L) {
            jsonObject.put("image_link", "http://" + IP + ":" + Port + "/Jaihind/api/images/" + image_id);
        } else {
            jsonObject.put("image_link", "NA");
        }

        return jsonObject.toString();

    }

    // Utilty Method to get Json
    public String getJson(Page<Article> articles, HttpServletRequest req) throws Exception {
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
            jsonObject.put("published_date", article.getPublishedDate());
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
}
