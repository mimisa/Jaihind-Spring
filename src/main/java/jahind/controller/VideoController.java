package jahind.controller;


import jahind.entity.Video;
import jahind.service.VideoService;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Gaurav on 10-12-2015.
 */

@RestController
@RequestMapping("/api/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String createArticle(String video_name, String video_url, String video_category, HttpServletRequest req) throws JSONException {

        Video video = new Video();
        video.setVideo_name(video_name);
        video.setVideo_url(video_url);
        video.setvCategory(video_category);

        Video savedVideo = videoService.create(video);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Video_id", savedVideo.getVideo_id());
        jsonObject.put("Video_name", savedVideo.getVideo_name());
        jsonObject.put("Video_url", savedVideo.getVideo_url());
        jsonObject.put("Video_category", savedVideo.getvCategory());
        jsonObject.put("Self-Link", "http://localhost:8080/Jaihind/api/videos/" + savedVideo.getVideo_id());
        return jsonObject.toString();
    }

    @RequestMapping(value = "{video_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Video> getVideos(@PathVariable("video_id") Long video_id, HttpServletRequest req) throws JSONException {

        if (videoService.findOne(video_id) == null) {
            return new ResponseEntity<Video>(HttpStatus.NOT_FOUND);
        }

        Video video = videoService.findOne(video_id);
        return new ResponseEntity<Video>(video, HttpStatus.OK);
    }
}
