package jahind.serviceBeans;

import jahind.entity.Page_Entity;
import jahind.repository.PageRepository;
import jahind.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Gaurav on 18-12-2015.
 */
@Service
public class PageServiceBean implements PageService {

    @Autowired
    private PageRepository pageRepository;

    @Override
    public org.springframework.data.domain.Page<Page_Entity> findAll(Pageable pageable) {
        return pageRepository.findAll(pageable);
    }

    @Override
    public List<Page_Entity> findAll() {
        return pageRepository.findAll();
    }

    @Override
    public Page_Entity findOne(Long page_id) {
        return pageRepository.findOne(page_id);
    }

    @Override
    public Page_Entity findByPageName(String page_name) {
        return pageRepository.findByPageName(page_name);
    }

    @Override
    public Page_Entity save(Page_Entity pageEntity) {
        return pageRepository.save(pageEntity);
    }

    @Override
    public Page_Entity update(Page_Entity pageEntity) {
        return pageRepository.save(pageEntity);
    }

    @Override
    public void delete(Page_Entity pageEntity) {
        pageRepository.delete(pageEntity);
    }
}
