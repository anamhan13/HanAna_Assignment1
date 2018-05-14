package server.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.image.Image;
import server.jdbc.ConnectionFactory;
import server.mappers.ArticleMapper;
import server.mappers.WriterMapper;
import server.model.Article;
import server.model.ArticleJSON;
import server.model.Writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;

public class WriteArticleCommand implements Command {

    private String author;
    private String title;
    private String articleAbstract;
    private String body;
    private String imageLocation;
    private Connection connection;

    public WriteArticleCommand(String author, String title, String articleAbstract, String body, String image) {
        this.author = author;
        this.title = title;
        this.articleAbstract = articleAbstract;
        this.body = body;
        this.imageLocation = image;
        this.connection = ConnectionFactory.getConnection();
    }

    @Override
    public Object execute() {
        ArticleJSON articleJSON;
        if (!imageLocation.isEmpty())
            articleJSON = new ArticleJSON(title, author, articleAbstract, body, true, imageLocation);
        else
            articleJSON = new ArticleJSON(title, author, articleAbstract, body, false, null);
        String location = "D:\\univer\\Anul 3\\semestrul 2\\Software Design\\HanAna_Assignment3\\Assignment3\\src\\main\\resources\\articles\\" + title+".json";
        Writer writer = (new WriterMapper(connection)).findByMail(author);
        Article article = new Article(title, writer, articleAbstract, location);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            File file = new File(location);
            file.createNewFile();
            java.io.Writer fileWriter = new FileWriter(file);
            String json;
            json = gson.toJson(articleJSON);
            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int inserted = (new ArticleMapper(connection)).insert(article);
        if (inserted != -1)
            return articleJSON;
        return null;
    }
}
