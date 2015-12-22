package jahind.assembler;

import jahind.controller.ArticleController;
import jahind.entity.Article;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by Gaurav on 17/11/15.
 */
@Component
public class ArticleResourceAssembler extends ResourceAssemblerSupport<Article, Resource> {

    public ArticleResourceAssembler() {
        super(ArticleController.class, Resource.class);
    }

    @Override
    public List<Resource> toResources(Iterable<? extends Article> articles) {
        List<Resource> resources = new ArrayList<Resource>();
        for (Article article : articles) {
            resources.add(new Resource<Article>(article, linkTo(ArticleController.class).slash(article.getArticle_id()).withSelfRel()));
        }
        return resources;
    }

    @Override
    public Resource toResource(Article article) {
        return new Resource<Article>(article, linkTo(ArticleController.class).slash(article.getArticle_id()).
                withSelfRel());
    }
}
