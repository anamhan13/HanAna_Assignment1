package server.commands;

import server.jdbc.ConnectionFactory;
import server.mappers.ArticleMapper;
import server.model.Article;

import java.sql.Connection;

public class OpenArticleCommand implements Command {

    private String title;
    private Connection connection;

    public OpenArticleCommand(String title) {
        this.title = title;
        connection = ConnectionFactory.getConnection();
    }

    @Override
    public Object execute() {
        Article article = (new ArticleMapper(connection)).findByTitle(title);
        if (article == null)
            return  null;
        return article.getArticleJSON();
    }
}
