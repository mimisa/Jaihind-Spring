package jahind.assembler;

import jahind.controller.VideoController;
import jahind.entity.Video;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by Gaurav on 11-12-2015.
 */

@Component
public class VideoResourceAssembler extends ResourceAssemblerSupport<Video, Resource> {

    public VideoResourceAssembler() {
        super(VideoController.class, Resource.class);
    }

    @Override
    public List<Resource> toResources(Iterable<? extends Video> Videos) {
        List<Resource> resources = new ArrayList<Resource>();
        for (Video Video : Videos) {
            resources.add(new Resource<Video>(
                    Video, linkTo(VideoController.class).slash(Video.getVideo_id()).withSelfRel())
            );
        }
        return resources;
    }

    @Override
    public Resource toResource(Video Video) {
        return new Resource<Video>(Video, linkTo(VideoController.class).slash(Video.getVideo_id()).withSelfRel());
    }
}
