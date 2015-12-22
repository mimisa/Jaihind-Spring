package jahind.service;

import jahind.entity.Page_Entity;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Gaurav on 18-12-2015.
 */
public interface PageService {
    org.springframework.data.domain.Page<Page_Entity> findAll(Pageable pageable);

    List<Page_Entity> findAll();

    Page_Entity findOne(Long page_id);

    Page_Entity findByPageName(String page_name);

    Page_Entity save(Page_Entity pageEntity);

    Page_Entity update(Page_Entity pageEntity);

    void delete(Page_Entity pageEntity);
}
