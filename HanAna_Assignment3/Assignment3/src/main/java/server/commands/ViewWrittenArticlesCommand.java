package server.commands;

import server.jdbc.ConnectionFactory;
import server.mappers.ArticleMapper;
import server.model.Article;

import java.sql.Connection;
import java.util.List;

public class ViewWrittenArticlesCommand implements Command {

    private String username;
    private Connection connection;

    public ViewWrittenArticlesCommand(String username) {
        this.username=username;
        connection = ConnectionFactory.getConnection();
    }

    @Override
    public Object execute() {
        List<Article> articles = (new ArticleMapper(connection)).findByAuthor(username);
        if (articles != null) {
            return articles;
        }
        return null;
    }
}
