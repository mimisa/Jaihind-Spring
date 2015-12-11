package jahind.service;

import jahind.entity.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Gaurav on 10-12-2015.
 */
public interface VideoService {

    Video create(Video video);

    Video findOne(Long video_id);

    Page<Video> findAll(Pageable pageable);

    Page<Video> findByCategory(Pageable pageable, String category);

    void delete(Long video_id);

}
