package server.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

public class Article extends Observable implements Serializable {

    private int id;
    private String title;
    private Writer author;
    private String articleAbstract;
   // private String body;
    private Set<Integer> articles;
    private String location;
    private ArticleJSON articleJSON;

    public Article(String title, Writer author, String articleAbstract, String location) {
        this.title = title;
        this.author = author;
        this.articleAbstract = articleAbstract;
        //this.body = body;
        this.articles = new HashSet<>();
        this.location = location;
    }

    public Article(String title, Writer author, String articleAbstract, String location, ArticleJSON articleJSON) {
        this.title = title;
        this.author = author;
        this.articleAbstract = articleAbstract;
        //this.body = body;
        this.location = location;
        this.articleJSON = articleJSON;
    }

    public Article() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Writer getAuthor() {
        return author;
    }

    public void setAuthor(Writer author) {
        this.author = author;
    }

    public String getArticleAbstract() {
        return articleAbstract;
    }

    public void setArticleAbstract(String articleAbstract) {
        this.articleAbstract = articleAbstract;
    }

    /*
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
    */

    public Set<Integer> getArticles() {
        return articles;
    }

    public void setArticles(Set<Integer> articles) {
        this.articles = articles;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ArticleJSON getArticleJSON() {
        return articleJSON;
    }

    public void setArticleJSON(ArticleJSON articleJSON) {
        this.articleJSON = articleJSON;
    }
}
