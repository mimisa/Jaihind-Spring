package jahind.entity;

import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;

/**
 * Created by Gaurav on 16/11/15.
 */

@Entity
@Table(name = "MEDIA")
public class Media extends ResourceSupport implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "MEDIA_ID")
    private Long media_id;

    @Column(name = "MEDIA_BLOB", nullable = false)
    private Blob media_blob;

    @ManyToOne
    @JoinColumn(name = "MEDIA_TYPE_ID")
    private Media_Type media_type;

    public Media() {

    }

    public Media(Blob media_blob) {
        this.media_blob = media_blob;
    }

    public Long getMedia_id() {
        return media_id;
    }

    public void setMedia_id(Long media_id) {
        this.media_id = media_id;
    }

    public Blob getMedia_blob() {
        return media_blob;
    }

    public void setMedia_blob(Blob media_blob) {
        this.media_blob = media_blob;
    }

    public Media_Type getMedia_type() {
        return media_type;
    }

    public void setMedia_type(Media_Type media_type) {
        this.media_type = media_type;
    }

}
