package jahind.assembler;

import jahind.controller.PageController;
import jahind.entity.Page_Entity;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by Gaurav on 21-12-2015.
 */
@Component
public class PageEntityResourceAssembler extends ResourceAssemblerSupport<Page_Entity, Resource> {

    public PageEntityResourceAssembler() {
        super(PageController.class, Resource.class);
    }

    @Override
    public List<Resource> toResources(Iterable<? extends Page_Entity> pages) {
        List<Resource> resources = new ArrayList<Resource>();
        for (Page_Entity page : pages) {
            resources.add(new Resource<Page_Entity>(page, linkTo(PageController.class).slash(page.getPage_id()).withSelfRel()));
        }
        return resources;
    }

    @Override
    public Resource toResource(Page_Entity page) {
        return new Resource<Page_Entity>(page, linkTo(PageController.class).slash(page.getPage_id()).
                withSelfRel());
    }
}
