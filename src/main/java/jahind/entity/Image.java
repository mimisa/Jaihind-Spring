package jahind.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;

/**
 * Created by Gaurav on 30/11/15.
 */
@Entity
@Table(name = "IMAGE")
public class Image extends ResourceSupport implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "IMAGE_ID")
    private Long image_id;

    @Column(name = "IMAGE_NAME", nullable = false)
    private String image_name;

    @JsonIgnore
    @Column(name = "IMAGE_BLOB", nullable = false)
    private Blob image_blob;

    @Column(name = "IMAGE_TYPE", nullable = true)
    private String image_type;

    @ManyToOne
    @JoinColumn(name = "MEDIA_TYPE_ID")
    private Media_Type media_type;

    public Image() {

    }

    public Long getImage_id() {
        return image_id;
    }

    public void setImage_id(Long image_id) {
        this.image_id = image_id;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public Blob getImage_blob() {
        return image_blob;
    }

    public void setImage_blob(Blob image_blob) {
        this.image_blob = image_blob;
    }

    public String getImage_type() {
        return image_type;
    }

    public void setImage_type(String image_type) {
        this.image_type = image_type;
    }

    public Media_Type getMedia_type() {
        return media_type;
    }

    public void setMedia_type(Media_Type media_type) {
        this.media_type = media_type;
    }
}
