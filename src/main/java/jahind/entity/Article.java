package jahind.entity;

import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Gaurav on 16/11/15.
 */

@Entity
@Table(name = "ARTICLE")
public class Article extends ResourceSupport implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "ARTICLE_ID")
    private Long article_id;

    @Column(name = "ARTICLE_NAME", nullable = false, unique = true)
    private String article_name;

    @Column(name = "ARTICLE_CONTENT", length = 2147483647, nullable = false)
    private String article_content;

    @Column(name = "ARTICLE_CREATED", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "ARTICLE_PUBLISHED", nullable = false)
    private Integer article_published;

    public Article() {
    }

    public Article(String article_content, Integer article_published, String article_name) {
        this.article_content = article_content;
        this.article_published = article_published;
        this.article_name = article_name;
       // this.article_created = new Date();
    }

    public Long getArticle_id() {
        return article_id;
    }

    public void setArticle_id(Long article_id) {
        this.article_id = article_id;
    }

    public String getArticle_name() {
        return article_name;
    }

    public void setArticle_name(String article_name) {
        this.article_name = article_name;
    }

    public String getArticle_content() {
        return article_content;
    }

    public void setArticle_content(String article_content) {
        this.article_content = article_content;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Integer getArticle_published() {
        return article_published;
    }

    public void setArticle_published(Integer article_published) {
        this.article_published = article_published;
    }
}
