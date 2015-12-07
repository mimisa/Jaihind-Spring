package jahind.serviceBeans;

import jahind.entity.Article;
import jahind.entity.Article_Image;
import jahind.entity.Image;
import jahind.repository.ArticleImageRepository;
import jahind.service.ArticleImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Gaurav on 3/12/15.
 */
@Service
public class ArticleImageServiceBean implements ArticleImageService{

    @Autowired
    private ArticleImageRepository articleImageRepository;


    @Override
    public Article_Image save(Article_Image ai) {
        return articleImageRepository.save(ai);
    }


}
