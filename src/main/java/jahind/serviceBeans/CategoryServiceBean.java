package jahind.serviceBeans;

import jahind.entity.Category;
import jahind.repository.CategoryRepository;
import jahind.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Gaurav on 16-12-2015.
 */
@Service
public class CategoryServiceBean implements CategoryService {


    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
