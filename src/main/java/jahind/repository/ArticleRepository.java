package jahind.repository;

import jahind.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Gaurav on 16/11/15.
 */
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Page<Article> findAll(Pageable pageable);

    Page<Article> findByCategory(Pageable pageable, String category);

    Page<Article> findByPublished(Pageable pageable, Integer published);

    Page<Article> findByCategoryAndPublished(Pageable pageable, String category, Integer published);

}
