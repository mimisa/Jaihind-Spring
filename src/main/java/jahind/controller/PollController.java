package jahind.controller;

import jahind.entity.Poll;
import jahind.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by Gaurav on 11-12-2015.
 */

@RestController
@RequestMapping(value = "/api/polls")
public class PollController {


    @Autowired
    private PollService pollService;

    //Insert new poll
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource<Poll> createPoll(String poll_question) throws Exception {

        Poll poll = new Poll();
        poll.setPoll_question(poll_question);
        ;
        poll.setYes(0);
        poll.setNo(0);
        Poll savedPoll = pollService.create(poll);

        Resource<Poll> resource = new Resource<>(savedPoll);
        resource.add(linkTo(methodOn(PollController.class).getPoll(savedPoll.getPoll_id())).withSelfRel());

        return resource;
    }

    // Fetch Poll based on poll_id
    @RequestMapping(value = "/{poll_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource<Poll> getPoll(@PathVariable("poll_id") Long poll_id) throws Exception {

        if (pollService.findOne(poll_id) == null) {
            //return new ResponseEntity<Poll>(HttpStatus.NOT_FOUND);
        }

        Poll poll = pollService.findOne(poll_id);
        Resource<Poll> resource = new Resource<>(poll);
        resource.add(linkTo(methodOn(PollController.class).getPoll(poll.getPoll_id())).withSelfRel());

        return resource;
    }

    //Update Or To Poll
    @RequestMapping(value = "/{poll_id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource<Poll> poll(@PathVariable("poll_id") Long poll_id, Integer yes, Integer no) throws Exception {
        if (pollService.findOne(poll_id) == null) {
            //return new ResponseEntity<Poll>(HttpStatus.NOT_FOUND);
        }

        Poll poll = pollService.findOne(poll_id);
        if (yes == 1 || yes == 0) {
            yes += poll.getYes();
            poll.setYes(yes);
        } else {
            throw new Exception();
        }

        if (no == 1 || no == 0) {
            no += poll.getNo();
            poll.setNo(no);
        } else {
            throw new Exception();
        }


        Poll savedPoll = pollService.poll(poll);

        Resource<Poll> resource = new Resource<>(savedPoll);
        resource.add(linkTo(methodOn(PollController.class).getPoll(savedPoll.getPoll_id())).withSelfRel());

        return resource;

    }

    //Delete poll
    @RequestMapping(value = "/{poll_id}", method = RequestMethod.DELETE)
    public void deleteVideo(@PathVariable("poll_id") Long poll_id) throws Exception {
        pollService.delete(poll_id);
    }
}
