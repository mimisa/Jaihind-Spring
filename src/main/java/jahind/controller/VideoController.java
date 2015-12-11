package jahind.controller;


import jahind.assembler.VideoResourceAssembler;
import jahind.entity.User;
import jahind.entity.Video;
import jahind.service.UserService;
import jahind.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by Gaurav on 10-12-2015.
 */

@RestController
@RequestMapping("/api/videos")
public class VideoController {

    @Autowired
    private UserService userService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private VideoResourceAssembler videoResourceAssembler;

    // Insert Video
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource<Video> createArticle(String video_name, String video_url, String video_category, HttpServletRequest req) throws Exception {

        User user = userService.findOne(1);

        Video video = new Video();
        video.setVideo_name(video_name);
        video.setVideo_url(video_url);
        video.setvCategory(video_category);
        video.setCreated(new Date());
        video.setUser(user);

        List<Video> videos = null;

        if (user.getVideos().isEmpty() || user.getVideos().size() == 0) {
            videos = new ArrayList<>();
            videos.add(video);
            user.setVideos(videos);
        } else {
            videos = user.getVideos();
            videos.add(video);
            user.setVideos(videos);
        }


        Video savedVideo = videoService.create(video);
        userService.save(user);

        Resource<Video> resource = new Resource<>(video);
        resource.add(linkTo(methodOn(VideoController.class).getVideo(savedVideo.getVideo_id())).withSelfRel());

        return resource;

    }


    // Fetch all Videos
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public PagedResources<Video> getVideos(Pageable pageable, PagedResourcesAssembler assembler) {
        Page<Video> articles = videoService.findAll(pageable);

        return assembler.toResource(articles, videoResourceAssembler);
    }

    // Fetch all videos with categories
    @RequestMapping(params = "category", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public PagedResources<Video> getVideosByCategory(@RequestParam("category") String category, Pageable pageable,
                                                     PagedResourcesAssembler assembler) {
        Page<Video> articles = videoService.findByCategory(pageable, category);
        return assembler.toResource(articles, videoResourceAssembler);
    }

    // Fetch Video based on video_id
    @RequestMapping(value = "/{video_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource<Video> getVideo(@PathVariable("video_id") Long video_id) throws Exception {

        if (videoService.findOne(video_id) == null) {
            //return new ResponseEntity<Video>(HttpStatus.NOT_FOUND);
        }

        Video video = videoService.findOne(video_id);
        Resource<Video> resource = new Resource<>(video);
        resource.add(linkTo(methodOn(VideoController.class).getVideo(video.getVideo_id())).withSelfRel());

        return resource;
    }

    // Delete Video based on video_id
    @RequestMapping(value = "/{video_id}", method = RequestMethod.DELETE)
    public void deleteVideo(@PathVariable("video_id") Long video_id) throws Exception {
        videoService.delete(video_id);
    }


}
