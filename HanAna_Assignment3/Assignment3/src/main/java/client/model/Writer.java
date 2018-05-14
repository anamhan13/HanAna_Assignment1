package client.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Writer extends User implements Serializable {

    private List<Article> articles;
    private int id;

    public Writer(Role role, List<Article> articles) {
        super(role);
        this.articles = articles;
    }

    public Writer(String mail, String name, String password, Role role, List<Article> articles) {
        super(mail, name, password, role);
        this.articles = articles;
    }

    public Writer() {
        super(Role.WRITER);
        articles = new ArrayList<Article>();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

}
