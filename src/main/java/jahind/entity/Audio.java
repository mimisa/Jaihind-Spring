package jahind.entity;

import javax.persistence.*;
import java.sql.Blob;

/**
 * Created by Gaurav on 01-12-2015.
 */

@Entity
@Table(name = "AUDIO")
public class Audio {

    @Id
    @GeneratedValue
    private Long audio_id;

    @Column(name = "AUDIO_NAME", nullable = false)
    private String audio_name;

    @Column(name = "AUDIO_BLOB", nullable = false)
    private Blob audio_blob;

    @Column(name = "IMAGE_TYPE", nullable = false)
    private String audio_type;

    @ManyToOne
    @JoinColumn(name = "MEDIA_TYPE_ID")
    private Media_Type mediaType;

    public Audio() {
    }

    public Long getAudio_id() {
        return audio_id;
    }

    public void setAudio_id(Long audio_id) {
        this.audio_id = audio_id;
    }

    public String getAudio_name() {
        return audio_name;
    }

    public void setAudio_name(String audio_name) {
        this.audio_name = audio_name;
    }

    public Blob getAudio_blob() {
        return audio_blob;
    }

    public void setAudio_blob(Blob audio_blob) {
        this.audio_blob = audio_blob;
    }

    public String getAudio_type() {
        return audio_type;
    }

    public void setAudio_type(String audio_type) {
        this.audio_type = audio_type;
    }

    public Media_Type getMediaType() {
        return mediaType;
    }

    public void setMediaType(Media_Type mediaType) {
        this.mediaType = mediaType;
    }
}
