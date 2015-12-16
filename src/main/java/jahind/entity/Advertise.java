package jahind.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;

/**
 * Created by Gaurav on 14-12-2015.
 */

@Entity
@Table(name = "ADVERTISE")
public class Advertise extends ResourceSupport implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "ADVERTISE_ID")
    private Long add_id;

    @Column(name = "ADVERTISE_NAME", nullable = false)
    private String add_name;

    @JsonIgnore
    @Column(name = "ADVERTISE_BLOB", nullable = false)
    private Blob add_blob;

    @Column(name = "PUBLISHED_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date publishedDate;

    @Column(name = "UNPUBLISHED_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date unpublishedDate;

    @Column(name = "REDIRECT_LINK", nullable = false)
    private String link;

    @Column(name = "PLACEMENT", nullable = false)
    private String placement;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Advertise() {

    }

    public Long getAdd_id() {
        return add_id;
    }

    public void setAdd_id(Long add_id) {
        this.add_id = add_id;
    }

    public String getAdd_name() {
        return add_name;
    }

    public void setAdd_name(String add_name) {
        this.add_name = add_name;
    }

    public Blob getAdd_blob() {
        return add_blob;
    }

    public void setAdd_blob(Blob add_blob) {
        this.add_blob = add_blob;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Date getUnpublishedDate() {
        return unpublishedDate;
    }

    public void setUnpublishedDate(Date unpublishedDate) {
        this.unpublishedDate = unpublishedDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPlacement() {
        return placement;
    }

    public void setPlacement(String placement) {
        this.placement = placement;
    }
}
