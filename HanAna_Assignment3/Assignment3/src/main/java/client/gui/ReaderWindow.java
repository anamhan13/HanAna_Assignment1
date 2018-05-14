package client.gui;

import client.listener.ClientToServerConnection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import server.model.Article;
import server.model.ArticleJSON;

import java.io.IOException;
import java.util.List;

public class ReaderWindow {

    private Scene scene;
    private Stage primaryStage;

    private Gson gson;
    private ClientToServerConnection clientToServerConnection;

    private static Alert alertSuccess;
    private static Alert alertFail;

    @FXML
    private ListView<String> articleListView;

    @FXML
    private Button viewArticlesButton;


    public ReaderWindow(ClientToServerConnection clientToServerConnection) {
        this.primaryStage = new Stage();
        this.clientToServerConnection = clientToServerConnection;
        this.gson = new GsonBuilder().create();
    }

    private void initialize() {
        this.articleListView = (ListView) scene.lookup("#articles");
        this.viewArticlesButton = (Button) scene.lookup("#view");

        alertFail = new Alert(Alert.AlertType.ERROR);
        alertSuccess = new Alert(Alert.AlertType.INFORMATION);
    }

    private void handleViewArticlesButton() {
        try {
            articleListView.getItems().clear();
            ClientToServerConnection.getOut().writeObject("view  articles");
            String o = (String) ClientToServerConnection.getIn().readObject();
            List<Article> articles = gson.fromJson(o, new TypeToken<List<Article>>(){}.getType());

            if (articles != null) {
                for (Article article: articles)
                    articleListView.getItems().add(article.getTitle());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void handleDoubleClickArticle() {
        String articleTitle = articleListView.getSelectionModel().getSelectedItem();
        try {
            ClientToServerConnection.getOut().writeObject("find  article  " + articleTitle);
            String o = (String) ClientToServerConnection.getIn().readObject();
            ArticleJSON article = gson.fromJson(o, ArticleJSON.class);
            ArticleWindow articleWindow;
            if (article != null) {
                if (!article.getImage().isEmpty()) {
                    articleWindow = new ArticleWindow(article.getTitle(), article.getAbstractArticle(), article.getBody(), article.getImage(), true);
                    articleWindow.display();
                } else {
                    articleWindow = new ArticleWindow(article.getTitle(), article.getAbstractArticle(), article.getBody(), null, false);
                    articleWindow.display();
                }
            }
        }  catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void display() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ReaderFXML.fxml"));
            scene = new Scene(root);
            initialize();

            handleViewArticlesButton();

            viewArticlesButton.setOnAction(event -> handleViewArticlesButton());

            articleListView.setOnMouseClicked(mouseEvent -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if(mouseEvent.getClickCount() == 2){
                        handleDoubleClickArticle();
                    }
                }
            });

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
