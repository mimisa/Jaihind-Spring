package jahind.serviceBeans;

import jahind.entity.Article;
import jahind.repository.ArticleRepository;
import jahind.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Collection<Article> findAll() {
        return articleRepository.findAll();
    }

    @Override
    public Article findOne(Long id) {
        return articleRepository.findOne(id);
    }

    public Article create(Article article) {
        return articleRepository.save(article);
    }


}
