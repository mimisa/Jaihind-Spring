package jahind.service;

import jahind.entity.Video;

/**
 * Created by Gaurav on 10-12-2015.
 */
public interface VideoService {

    Video create(Video video);

    Video findOne(Long video_id);

}
