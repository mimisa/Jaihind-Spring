package jahind.entity;

import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Gaurav on 11-12-2015.
 */

@Entity
@Table(name = "POLL")
public class Poll extends ResourceSupport implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "POLL_ID")
    private Long poll_id;

    @Column(name = "ARTICLE_NAME", nullable = false, unique = true)
    private String poll_question;

    @Column(name = "YES", nullable=true)
    private Integer yes;

    @Column(name = "NO", nullable=true)
    private Integer no;

    public Poll(){

    }

    public Long getPoll_id() {
        return poll_id;
    }

    public void setPoll_id(Long poll_id) {
        this.poll_id = poll_id;
    }

    public String getPoll_question() {
        return poll_question;
    }

    public void setPoll_question(String poll_question) {
        this.poll_question = poll_question;
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
}
