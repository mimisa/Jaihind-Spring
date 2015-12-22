package jahind.controller;

import jahind.assembler.PageEntityResourceAssembler;
import jahind.entity.Page_Entity;
import jahind.entity.User;
import jahind.service.PageService;
import jahind.service.UserService;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by Gaurav on 18-12-2015.
 */
@RestController
@RequestMapping(value = "/api/categories")
public class PageController {

    @Autowired
    private PageService pageService;

    @Autowired
    private PageEntityResourceAssembler pageEntityResourceAssembler;

    @Autowired
    private UserService userService;

    // Insert Page_Entity
    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource<Page_Entity> insertPage(String page_name, String display_name) throws Exception {
        Page_Entity pageEntity = new Page_Entity();
        pageEntity.setPageName(page_name);
        pageEntity.setDisplay_name(display_name);
        pageEntity.setVisibility(1);

        User user = userService.findOne(1);
        pageEntity.setUser(user);
        List<Page_Entity> pageEntities = null;
        if (user.getPageEntities().isEmpty() || user.getPageEntities().size() == 0) {
            pageEntities = new ArrayList<>();
        } else {
            pageEntities = user.getPageEntities();
        }

        pageEntities.add(pageEntity);
        user.setPageEntities(pageEntities);
        userService.save(user);
        Page_Entity savedPageEntity = pageService.save(pageEntity);

        Resource<Page_Entity> resource = new Resource<>(savedPageEntity);

        resource.add(linkTo(methodOn(PageController.class).getPage(pageEntity.getPage_id())).withSelfRel());

        return resource;
    }


    // Fetch Page_Entity with ID
    @RequestMapping(
            value = "/{page_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource<Page_Entity> getPage(@PathVariable("page_id") Long page_id) throws Exception {
        if (pageService.findOne(page_id) == null) {
            throw new Exception();
        }
        Page_Entity pageEntity = pageService.findOne(page_id);
        Resource<Page_Entity> resource = new Resource<>(pageEntity);
        resource.add(linkTo(methodOn(PageController.class).getPage(pageEntity.getPage_id())).withSelfRel());

        return resource;
    }

    // Fetch All Pages
    /*@RequestMapping(method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PagedResources<Page_Entity> getPages(Pageable pageable, PagedResourcesAssembler assembler) {
        Page<Page_Entity> pages = pageService.findAll(pageable);
        return assembler.toResource(pages, pageEntityResourceAssembler);
    }*/
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPages(Pageable pageable, HttpServletRequest req) throws Exception {
        Page<Page_Entity> pages = pageService.findAll(pageable);
        String IP = req.getServerName();
        int Port = req.getServerPort();
        JSONArray jsonArray = new JSONArray();
        for (Page_Entity page : pages) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("page_id", page.getPage_id());
            jsonObject.put("pageName", page.getPageName());
            jsonObject.put("display_name", page.getDisplay_name());
            jsonObject.put("visbility", page.getVisibility());
            jsonObject.put("link", "http://" + IP + ":" + Port + "/Jaihind/api/categories/" + page.getPage_id());

            jsonArray.put(jsonObject);
        }

        return jsonArray.toString();
    }

    //Fetch By PageName
    @RequestMapping(params = "page_name",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource<Page_Entity> getByPageName(@RequestParam("page_name") String page_name) throws Exception {
        if (pageService.findByPageName(page_name) == null) {
            throw new Exception();
        }
        Page_Entity pageEntity = pageService.findByPageName(page_name);
        Resource<Page_Entity> resource = new Resource<>(pageEntity);
        resource.add(linkTo(methodOn(PageController.class).getPage(pageEntity.getPage_id())).withSelfRel());

        return resource;
    }

    // Make Page Visibile
}
