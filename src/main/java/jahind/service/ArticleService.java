package jahind.service;

import jahind.entity.Article;

import java.util.Collection;

/**
 * Created by Gaurav on 16/11/15.
 */
public interface ArticleService {

    Collection<Article> findAll();

    Article findOne(Long id);

    Article create(Article article);
}
