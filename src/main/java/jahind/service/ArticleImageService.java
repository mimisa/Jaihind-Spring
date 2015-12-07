package jahind.service;

import jahind.entity.Article_Image;

/**
 * Created by Gaurav on 3/12/15.
 */
public interface ArticleImageService {
    Article_Image save(Article_Image ai);

    void delete(Article_Image ai);

}
