package server.mappers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import server.jdbc.ConnectionFactory;
import server.model.Article;
import server.model.ArticleJSON;
import server.model.Writer;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArticleMapper {
    protected static final Logger LOGGER = Logger.getLogger("Article");
    private Connection connection;

    public ArticleMapper(Connection connection) {
        this.connection = connection;
    }

    public Article find(int id) {
        String query = "SELECT * FROM articles WHERE idarticle=?";
        List<Article> list = new ArrayList<Article>();
        ResultSet resultSet = null;
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            list = createArticleObjects(resultSet);

            if (list.size() != 0) {
                System.out.println("[Article/FIND] The requested entry was found!");
            }

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ArticleRepository: find " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);

        }

        if (list.size() == 0) {
            System.out.println("[Article/FIND] Couldn't find the requested entry");
            return null;
        }
        return list.get(0);
    }

    public Article findByTitle(String title) {
        String query = "SELECT * FROM articles WHERE title=?";
        List<Article> list = new ArrayList<Article>();
        ResultSet resultSet = null;
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, title);
            resultSet = statement.executeQuery();
            list = createArticleObjects(resultSet);

            if (list.size() != 0) {
                System.out.println("[Article/FIND] The requested entry was found!");
            }

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ArticleRepository: find " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);

        }

        if (list.size() == 0) {
            System.out.println("[Article/FIND] Couldn't find the requested entry");
            return null;
        }
        return list.get(0);
    }

    public List<Article> findByAuthor(String author) {
        String query = "SELECT * FROM articles WHERE author=?";
        List<Article> list = new ArrayList<Article>();
        ResultSet resultSet = null;
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, author);
            resultSet = statement.executeQuery();
            list = createArticleObjects(resultSet);

            if (list.size() != 0) {
                System.out.println("[Article/FIND] The requested entry was found!");
            }

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ArticleRepository: find " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);

        }

        if (list.size() == 0) {
            System.out.println("[Article/FIND] Couldn't find the requested entry");
            return null;
        }
        return list;
    }

    public List<Article> findAll() {
        String query = "SELECT * FROM articles";
        List<Article> list = new ArrayList<Article>();
        ResultSet resultSet = null;
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            list = createArticleObjects(resultSet);

            if (list.size() != 0) {
                System.out.println("[Article/FIND] The requested entry was found!");
            }

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "[Article/FIND] " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
        }

        return list;
    }

    public int insert(Article article) {
        PreparedStatement insertStatement = null;
        ResultSet rs = null;
        int insertedId = -1;
        String query = "INSERT INTO articles (title, author, articleabstract, location) VALUES (?,?,?,?)";

        try {

            WriterMapper writerMapper = new WriterMapper(connection);
            Writer writer = writerMapper.findByMail(article.getAuthor().getMail());
            if (writer != null) {
                insertStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                insertStatement.setString(1, article.getTitle());
                insertStatement.setString(2, article.getAuthor().getMail());
                insertStatement.setString(3, article.getArticleAbstract());
                insertStatement.setString(4, article.getLocation());
                insertStatement.executeUpdate();
                rs = insertStatement.getGeneratedKeys();

                if (rs.next()) {
                    insertedId = rs.getInt(1);
                    System.out.println("[Article/INSERT] Entry in table successfully inserted!");
                }
            } else {
                insertedId = -1;
            }

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "[Article/INSERT] " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(rs);
        }

        if (insertedId == -1) {
            System.out.println("[Article/INSERT] Couldn't insert data in table");
        }
        return insertedId;
    }

    public int update(Article article) {
        PreparedStatement updateStatement = null;
        ResultSet rSet = null;
        int updatedId = -1;
        String query = "UPDATE Article SET title=?, author=?, articleabstract=?, location=? WHERE idarticle = ?";

        try {
            updateStatement = connection.prepareStatement(query);

            if (article == null) {
                updatedId = -1;
                System.out.println("[Article/UPDATE] There is no such entry in this table");
            } else {
                updateStatement.setString(1, article.getTitle());
                updateStatement.setString(2, article.getAuthor().getMail());
                updateStatement.setString(3, article.getArticleAbstract());
                updateStatement.setString(4, article.getLocation());
                updateStatement.setInt(5, article.getId());
                updateStatement.executeUpdate();
                updatedId = article.getId();
            }

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "[Article/UPDATE] " + e.getMessage());
        } finally {
            ConnectionFactory.close(updateStatement);
            ConnectionFactory.close(rSet);
        }

        if (updatedId > 0) {
            System.out.println("[Article/UPDATE] Entry in table successfully updated!");
        }
        return updatedId;
    }

    public int delete(int id) {
        PreparedStatement deleteStatement = null;
        String query = "DELETE FROM article WHERE idarticle=?";
        int deletedId = -1;

        Article t = this.find(id);
        if (t == null) {
            System.out.println("[Article/DELETE] There is no such entry in this table");
            return -1;
        }
        try {
            deleteStatement = connection.prepareStatement(query);
            deleteStatement.setInt(1, id);
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "[Article/DELETE] " + e.getMessage());
        } finally {
            ConnectionFactory.close(deleteStatement);
        }

        deletedId = t.getId();
        System.out.println("[Article/DELETE] Entry in table successfully deleted!");
        return deletedId;
    }

    protected List<Article> createArticleObjects(ResultSet resultSet) {
        List<Article> list = new ArrayList<Article>();
        try {
            while (resultSet.next()) {
                Article instance = new Article();

                WriterMapper writerMapper = new WriterMapper(connection);

                instance.setId(resultSet.getInt("idarticle"));
                instance.setTitle(resultSet.getString("title"));
                Writer author = writerMapper.findByMail(resultSet.getString("author"));
                instance.setAuthor(author);
                instance.setArticleAbstract(resultSet.getString("articleabstract"));
                //instance.setlocation(resultSet.getString("location"));
                instance.setLocation(resultSet.getString("location"));

                String location = resultSet.getString("location");

                Gson gson = new GsonBuilder().create();
                JsonReader reader = new JsonReader(new FileReader(location));
                ArticleJSON data = gson.fromJson(reader, ArticleJSON.class);
                instance.setArticleJSON(data);

                list.add(instance);
            }
            return list;
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}


