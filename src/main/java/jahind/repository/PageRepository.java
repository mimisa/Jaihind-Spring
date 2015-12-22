package jahind.repository;

import jahind.entity.Page_Entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Gaurav on 18-12-2015.
 */
@Repository
public interface PageRepository extends JpaRepository<Page_Entity, Long> {

    Page<Page_Entity> findAll(Pageable pageable);

    Page_Entity findByPageName(String page_name);

}
