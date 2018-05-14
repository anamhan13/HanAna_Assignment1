package server.model;

public class ArticleJSON {

    private String title;
    private String author;
    private String abstractArticle;
    private String body;
    private boolean containsImage;
    private String image;

    public ArticleJSON(String title, String author, String abstractArticle, String body, boolean containsImage, String image) {
        this.title = title;
        this.author = author;
        this.abstractArticle = abstractArticle;
        this.body = body;
        this.containsImage = containsImage;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAbstractArticle() {
        return abstractArticle;
    }

    public void setAbstractArticle(String abstractArticle) {
        this.abstractArticle = abstractArticle;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isContainsImage() {
        return containsImage;
    }

    public void setContainsImage(boolean containsImage) {
        this.containsImage = containsImage;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
