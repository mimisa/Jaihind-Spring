package jahind.repository;

import jahind.entity.Article_Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Gaurav on 3/12/15.
 */
@Repository
public interface ArticleImageRepository extends JpaRepository<Article_Image, Long> {

}
