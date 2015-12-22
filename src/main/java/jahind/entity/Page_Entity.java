package jahind.entity;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Gaurav on 18-12-2015.
 */
@Entity
@Table(name = "PAGE")
public class Page_Entity extends ResourceSupport implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "PAGE_ID")
    private Long page_id;

    @Column(name = "PAGE_NAME", nullable = false, unique = true)
    private String pageName;

    @Column(name = "DISPLAY_NAME", nullable = false, unique = true)
    private String display_name;

    @Column(name = "PAGE_VISBILITY", nullable = false)
    private Integer visibility;

    @JsonIgnore
    @OneToMany(mappedBy = "page", cascade = CascadeType.REMOVE)
    private List<Article> articles;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Page_Entity() {

    }

    public Long getPage_id() {
        return page_id;
    }

    public void setPage_id(Long page_id) {
        this.page_id = page_id;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
