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

    Collection<Article> findAll();

    Article findOne(Long id);

    Article create(Article article);

}
