package jahind.assembler;

import jahind.controller.ArticleController;
import jahind.controller.PollController;
import jahind.entity.Poll;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by Gaurav on 14-12-2015.
 */

@Component
public class PollResourceAssembler extends ResourceAssemblerSupport<Poll, Resource> {

    public PollResourceAssembler() {
        super(PollController.class, Resource.class);
    }

    @Override
    public List<Resource> toResources(Iterable<? extends Poll> polls) {
        List<Resource> resources = new ArrayList<Resource>();
        for (Poll poll : polls) {
            resources.add(new Resource<Poll>(poll, linkTo(PollController.class).slash(poll.getPoll_id()).withSelfRel()));
        }
        return resources;
    }

    @Override
    public Resource toResource(Poll poll) {
        return new Resource<Poll>(poll, linkTo(PollController.class).slash(poll.getPoll_id()).withSelfRel());
    }
}
