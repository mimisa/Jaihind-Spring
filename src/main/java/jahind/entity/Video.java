package jahind.entity;

import javax.persistence.*;

/**
 * Created by Gaurav on 10-12-2015.
 */

@Entity
@Table(name = "VIDEO")
public class Video {

    @Id
    @GeneratedValue
    @Column(name = "VIDEO_ID")
    private Long video_id;

    @Column(name = "VIDEO_NAME", nullable = false, unique = true)
    private String video_name;

    @Column(name = "VIDEO_URL", nullable = false)
    private String video_url;

    @Column(name = "VIDEO_CATEGORY", nullable = false)
    private String vCategory;

    public Video() {
    }

    public Long getVideo_id() {
        return video_id;
    }

    public void setVideo_id(Long video_id) {
        this.video_id = video_id;
    }

    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getvCategory() {
        return vCategory;
    }

    public void setvCategory(String vCategory) {
        this.vCategory = vCategory;
    }

}
