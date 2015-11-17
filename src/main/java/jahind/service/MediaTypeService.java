package jahind.service;

import jahind.entity.Media_Type;

import java.util.Collection;

/**
 * Created by Gaurav on 16/11/15.
 */
public interface MediaTypeService {

    Collection<Media_Type> findAll();

    Media_Type findOne(Long id);

    Media_Type create(Media_Type mediaType);

    Media_Type update(Media_Type mediaType);

    void delete(Long id);

    void evictCache();
}
