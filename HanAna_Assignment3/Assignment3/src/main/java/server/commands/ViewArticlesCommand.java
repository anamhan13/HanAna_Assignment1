package server.commands;

import server.jdbc.ConnectionFactory;
import server.mappers.ArticleMapper;
import server.model.Article;

import java.sql.Connection;
import java.util.List;

public class ViewArticlesCommand implements Command {
    private Connection connection;
    public ViewArticlesCommand() {
        connection = ConnectionFactory.getConnection();
    }

    public List<Article> execute() {
        List<Article> articles =  (new ArticleMapper(connection)).findAll();
        if (articles.size() == 0)
            return null;
        return articles;
    }
}
