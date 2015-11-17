package jahind.serviceBeans;

import jahind.entity.Media_Type;
import jahind.repository.MediaTypeRepository;
import jahind.service.MediaTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by Gaurav on 16/11/15.
 */
@Service
public class MediaTypeServiceBean implements MediaTypeService {

    @Autowired
    MediaTypeRepository mediaTypeRepository;

    @Override
    public Collection<Media_Type> findAll() {
        return mediaTypeRepository.findAll();
    }

    @Override
    public Media_Type findOne(Long id) {
        return mediaTypeRepository.findOne(id);
    }

    @Override
    public Media_Type create(Media_Type mediaType) {
        if (mediaType.getMedia_type_id() == null) {
            // Cannot create Greeting
        }

        return mediaTypeRepository.save(mediaType);
    }

    @Override
    public Media_Type update(Media_Type mediaType) {
        Media_Type persistedMediaType = mediaTypeRepository.findOne(mediaType.getMedia_type_id());
        if (persistedMediaType == null) {
            // Cannot update as Greeting doesnot exist.
        }

        return mediaTypeRepository.save(mediaType);
    }

    @Override
    public void delete(Long id) {
        mediaTypeRepository.delete(id);
    }

    @Override
    public void evictCache() {

    }
}
