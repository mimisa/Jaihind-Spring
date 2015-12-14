package jahind.controller;

import jahind.assembler.PollResourceAssembler;
import jahind.entity.Poll;
import jahind.entity.User;
import jahind.service.PollService;
import jahind.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by Gaurav on 14-12-2015.
 */
@RestController
@RequestMapping("/api/polls")
public class PollController {

    @Autowired
    private PollService pollService;

    @Autowired
    private UserService userService;


    @Autowired
    private PollResourceAssembler pollResourceAssembler;

    // Insert Poll
    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Resource<Poll> createPoll(String question, String category) throws Exception {
        User user = userService.findOne(1);

        Poll poll = new Poll();
        poll.setQuestion(question);
        poll.setCategory(category);
        poll.setYes(0);
        poll.setNo(0);
        poll.setCreated(new Date());
        poll.setPublished(0);
        poll.setPublishedDate(null);
        poll.setUser(user);


        List<Poll> polls = null;
        if (user.getPolls().size() == 0 || user.getPolls().isEmpty()) {
            polls = new ArrayList<>();
        }
        polls = user.getPolls();
        polls.add(poll);
        user.setPolls(polls);

        Poll savedPoll = pollService.create(poll);
        userService.save(user);

        Resource<Poll> resource = new Resource<>(savedPoll);
        resource.add(linkTo(methodOn(PollController.class).getPoll(savedPoll.getPoll_id())).withSelfRel());
        return resource;
    }

    // Fetch Polls
    @RequestMapping(method = RequestMethod.GET, produces =
            MediaType.APPLICATION_JSON_VALUE
    )
    public PagedResources<Poll> getPolls(Pageable pageable, PagedResourcesAssembler assembler) {
        Page<Poll> polls = pollService.findAll(pageable);
        return assembler.toResource(polls, pollResourceAssembler);

    }

    // Fetch Poll by poll_id
    @RequestMapping(value = "/{poll_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Resource<Poll> getPoll(@PathVariable("poll_id") Long poll_id) throws Exception {
        Poll poll = pollService.findOne(poll_id);
        Resource<Poll> resource = new Resource<>(poll);
        resource.add(linkTo(methodOn(PollController.class).getPoll(poll.getPoll_id())).withSelfRel());
        return resource;
    }

    // Fetch Poll by published
    @RequestMapping(params = "published",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public PagedResources<Poll> getPublishedPolls(@RequestParam("published") boolean published,
                                                  Pageable pageable, PagedResourcesAssembler assembler) {
        int flag = 0;
        if (published == true) {
            flag = 1;
        }
        Page<Poll> polls = pollService.findPublsihedPolls(pageable, flag);
        return assembler.toResource(polls, pollResourceAssembler);
    }

    // Publish the poll -Make it PUT
    @RequestMapping(value = "/{poll_id}", params = "published",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Resource<Poll> publishPoll(@PathVariable("poll_id") Long poll_id,
                                      @RequestParam("published") boolean published) throws Exception {
        int flag = 0;
        if (published == true) {
            flag = 1;
        }
        Poll poll = pollService.findOne(poll_id);
        poll.setPublished(flag);
        poll.setPublishedDate(new Date());

        Poll publishedPoll = pollService.publish(poll);
        Resource<Poll> resource = new Resource<>(publishedPoll);
        resource.add(linkTo(methodOn(PollController.class).getPoll(publishedPoll.getPoll_id())).withSelfRel());
        return resource;
    }

    // Fetch poll by category

    // Delete Poll
    @RequestMapping(value = "/{poll_id}",
            method = RequestMethod.DELETE
    )
    public void deletePoll(@PathVariable("poll_id") Long poll_id) throws Exception {
        pollService.delete(poll_id);
    }

    // Update a poll with yes / no
    @RequestMapping(value = "/{poll_id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Resource<Poll> poll(@PathVariable("poll_id") Long poll_id, Integer yes, Integer no) throws Exception {
        Poll poll = pollService.findOne(poll_id);
        if (poll.getPublished() == 0) {
            throw new Exception();
        }
        if (yes != 0 && no != 0) {
            throw new Exception();
        } else {
            if (yes == 1 || yes == 0 && no == 1 || no == 0) {
                yes += poll.getYes();
                no += poll.getNo();
                poll.setYes(yes);
                poll.setNo(no);
            } else {
                throw new Exception();
            }
        }

        Poll polledPoll = pollService.poll(poll);
        Resource<Poll> resource = new Resource<>(polledPoll);
        resource.add(linkTo(methodOn(PollController.class).getPoll(polledPoll.getPoll_id())).withSelfRel());
        return resource;
    }
}
