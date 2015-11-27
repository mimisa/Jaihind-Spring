package jahind.controller;

import jahind.entity.Media_Type;
import jahind.service.MediaTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by Gaurav on 16/11/15.
 */


@RestController
@RequestMapping(value = "/api/mediaTypes")
public class MediaTypeController {

    @Autowired
    MediaTypeService mediaTypeService;

    // Fetch Media_Type with id
    @RequestMapping(value = "/{media_type_id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Media_Type> getMediaType(@PathVariable("media_type_id") Long media_type_id) {
        Media_Type mediaType = mediaTypeService.findOne(media_type_id);
        mediaType.add(linkTo(methodOn(MediaTypeController.class).getMediaType(media_type_id)).withSelfRel());
        return new ResponseEntity<Media_Type>(mediaType, HttpStatus.OK);
    }

    // Insert Media_Type
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Media_Type> createMediaType(@RequestBody Media_Type mediaType) {
        Media_Type savedMediaType = mediaTypeService.create(mediaType);
        mediaType.add(linkTo(methodOn(MediaTypeController.class).getMediaType(savedMediaType.getMedia_type_id()))
                .withSelfRel());
        return new ResponseEntity<Media_Type>(savedMediaType, HttpStatus.CREATED);
    }


}
