package jahind.entity;

import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Gaurav on 14-12-2015.
 */

@Entity
@Table(name = "POLL")
public class Poll extends ResourceSupport implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "POLL_ID")
    private Long poll_id;

    @Column(name = "POLL_QUESTION", nullable = false)
    private String question;

    @Column(name = "YES", nullable = false)
    private Integer yes;

    @Column(name = "NO", nullable = false)
    private Integer no;

    @Column(name = "CREATED", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "CATEGORY", nullable = false)
    private String category;

    @Column(name = "PUBLISHED", nullable = true)
    private Integer published;

    @Column(name = "PUBLISHED_DATE", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date publishedDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Poll() {
    }

    public Long getPoll_id() {
        return poll_id;
    }

    public void setPoll_id(Long poll_id) {
        this.poll_id = poll_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getYes() {
        return yes;
    }

    public void setYes(Integer yes) {
        this.yes = yes;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Integer getPublished() {
        return published;
    }

    public void setPublished(Integer published) {
        this.published = published;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
