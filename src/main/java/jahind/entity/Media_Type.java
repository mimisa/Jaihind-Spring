package jahind.entity;

import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Gaurav on 16/11/15.
 */

@Entity
@Table(name = "MEDIA_TYPE")
public class Media_Type extends ResourceSupport implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "MEDIA_TYPE_ID")
    private Long media_type_id;

    @Basic
    private String media_type_name;

    public Media_Type() {
    }

    public Media_Type(String media_type_name) {
        this.media_type_name = media_type_name;
    }

    public Long getMedia_type_id() {
        return media_type_id;
    }

    public void setMedia_type_id(Long media_type_id) {
        this.media_type_id = media_type_id;
    }

    public String getMedia_type_name() {
        return media_type_name;
    }

    public void setMedia_type_name(String media_type_name) {
        this.media_type_name = media_type_name;
    }
}