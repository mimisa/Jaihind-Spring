package jahind.serviceBeans;

import jahind.entity.Video;
import jahind.repository.VideoRepository;
import jahind.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Gaurav on 10-12-2015.
 */

@Service
public class VideoServiceBean implements VideoService {

    @Autowired
    private VideoRepository videoRepository;

    @Override
    public Video create(Video video) {
        return videoRepository.save(video);
    }

    @Override
    public Video findOne(Long video_id) {
        return videoRepository.findOne(video_id);
    }
}
