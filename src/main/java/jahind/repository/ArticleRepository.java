package jahind.repository;

import jahind.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Gaurav on 16/11/15.
 */
public interface ArticleRepository extends JpaRepository<Article, Long> {
}
