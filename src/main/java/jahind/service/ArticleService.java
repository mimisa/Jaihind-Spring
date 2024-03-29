package jahind.service;

import jahind.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

/**
 * Created by Gaurav on 16/11/15.
 */
public interface ArticleService {

    Page<Article> findAll(Pageable pageable);

    Page<Article> findByCatgory(Pageable pageable, String category);

    Page<Article> findPublsihedArticles(Pageable pageable, Integer published);

    Page<Article> findByCategoryAndPublished(Pageable pageable, String category, Integer published);

    Collection<Article> findAll();

    Article findOne(Long id);

    Article create(Article article);

    Article update(Article article);

    // Article create(Article article, User user);

    void delete(Article article);

    void delete(Long article_id);

}
