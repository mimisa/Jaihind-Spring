package jahind.serviceBeans;

import jahind.entity.Image;
import jahind.repository.ImageRepository;
import jahind.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Gaurav on 30/11/15.
 */
@Service
public class ImageServiceBean implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Image save(Image image) {
        return imageRepository.save(image);

        // return imageRepository.save(image);
    }

    @Override
    public Image findOne(Long image_id) {
        return imageRepository.findOne(image_id);
    }
}
