package elnaggar.malmoviesapp.data;

import java.io.Serializable;

/**
 * Created by Elnaggar on 19/03/2016.
 */
public class Review implements Serializable{
    private String content;
    private String author;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


}
