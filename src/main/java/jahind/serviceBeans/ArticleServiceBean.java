package jahind.serviceBeans;

import jahind.entity.Article;
import jahind.repository.ArticleRepository;
import jahind.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

/**
 * Created by Gaurav on 17/11/15.
 */
@Service
public class ArticleServiceBean implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

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
    public Page<Article> findAll(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }


}
