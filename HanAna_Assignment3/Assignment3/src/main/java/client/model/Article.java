package client.model;

import java.io.Serializable;
import java.util.*;

public class Article extends Observable implements Serializable {

    private int id;
    private String title;
    private Writer author;
    private String articleAbstract;
    private String body;
    private List<Integer> articles;

    public Article(int id, String title, Writer author, String articleAbstract, String body) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.articleAbstract = articleAbstract;
        this.body = body;
        this.articles = new ArrayList<Integer>();
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<Integer> getArticles() {
        return articles;
    }

    public void setArticles(List<Integer> articles) {
        this.articles = articles;
    }

}
