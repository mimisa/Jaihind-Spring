package jahind.entity;

/**
 * Created by Gaurav on 18/11/15.
 */
public class Greeting {

    private final long id;

    private final String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

}
