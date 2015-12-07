package jahind.entity;

import javax.persistence.*;

/**
 * Created by Gaurav on 3/12/15.
 */

@Entity
@Table(name = "ARTICLE_IMAGE")
public class Article_Image {

    @Id
    @GeneratedValue
    @Column(name = "ARTICLE_IMAGE_ID")
    private Long ai_id;

    @JoinColumn(name = "ARTICLE_ID", referencedColumnName = "ARTICLE_ID")
    @ManyToOne
    private Article article_id;

    @JoinColumn(name = "IMAGE_ID", referencedColumnName = "IMAGE_ID")
    @ManyToOne
    private Image image_id;


    public Article_Image() {

    }

    public Long getAi_id() {
        return ai_id;
    }

    public void setAi_id(Long ai_id) {
        this.ai_id = ai_id;
    }

    public Article getArticle_id() {
        return article_id;
    }

    public void setArticle_id(Article article_id) {
        this.article_id = article_id;
    }

    public Image getImage_id() {
        return image_id;
    }

    public void setImage_id(Image image_id) {
        this.image_id = image_id;
    }
}


