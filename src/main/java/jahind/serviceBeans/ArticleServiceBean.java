package jahind.serviceBeans;

import jahind.entity.Article;
import jahind.repository.ArticleRepository;
import jahind.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by Gaurav on 17/11/15.
 */
@Service
public class ArticleServiceBean implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Page<Article> findAll(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    @Override
    public Page<Article> findByCatgory(Pageable pageable, String category) {
        return articleRepository.findByCategory(pageable, category);
    }

    @Override
    public Page<Article> findPublsihedArticles(Pageable pageable, Integer published) {
        return articleRepository.findByPublished(pageable, published);
    }

    @Override
    public Page<Article> findByCategoryAndPublished(Pageable pageable, String category, Integer published) {
        return articleRepository.findByCategoryAndPublished(pageable, category, published);
    }

    @Override
    public Collection<Article> findAll() {
        return articleRepository.findAll();
    }

    @Override
    public Article findOne(Long id) {
        return articleRepository.findOne(id);
    }

    @Override
    public Article create(Article article) {
        return articleRepository.save(article);
    }

    /*
    public Article create(Article article, User user) {
        // Adding date & user to article
        article.setCreated(new Date());
        article.setUser(user);
        Set<Article> articles = null;
        // Adding articles to user
        if (user.getArticles().size() == 0) {
           articles = new HashSet<>();
       } else {
           articles = user.getArticles();
       }
        articles.add(article);
        user.setArticles(articles);

        // save user
        userRepository.save(user);

        // save article
        return articleRepository.save(article);
    }
*/
    @Override
    public void delete(Article article) {
        articleRepository.delete(article);
    }

    @Override
    public void delete(Long article_id) {
        articleRepository.delete(article_id);
    }


}
