package client.gui;

import client.listener.ClientToServerConnection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import server.model.Article;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class UserWindow {

    private Stage stage;
    private Scene scene;
    private String username;

    @FXML
    private Button submitButton;

    @FXML
    private Button viewButton;

    @FXML
    private TextField titleField;

    @FXML
    private TextField abstractField;

    @FXML
    private TextField bodyField;

    @FXML
    private ListView<String> articleListView;

    @FXML
    private Button selectImage;

    @FXML
    private TextArea imageArea;

    private String imageLocation;

    private ClientToServerConnection clientToServerConnection;
    private Gson gson;

    private Alert alertSuccess;
    private Alert alertFail;

    public UserWindow(String username, ClientToServerConnection clientToServerConnection) {
        this.username = username;
        this.clientToServerConnection = clientToServerConnection;
        this.gson = new GsonBuilder().create();
        this.stage = new Stage();
    }

    private void initialize() {
        this.viewButton = (Button) scene.lookup("#viewButton");
        this.submitButton = (Button) scene.lookup("#submitButton");
        this.selectImage = (Button) scene.lookup("#selectImage");

        this.titleField = (TextField) scene.lookup("#title");
        this.abstractField = (TextField) scene.lookup("#abstract");
        this.bodyField = (TextField) scene.lookup("#body");

        this.articleListView = (ListView) scene.lookup("#articleView");

        this.imageArea = (TextArea) scene.lookup("#imageArea");

        alertFail = new Alert(Alert.AlertType.ERROR);
        alertSuccess = new Alert(Alert.AlertType.INFORMATION);
    }

    private void handleSelectImageButton() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilterJPG =
                new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
        FileChooser.ExtensionFilter extFilterjpg =
                new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG =
                new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        FileChooser.ExtensionFilter extFilterpng =
                new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters()
                .addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng);

        //Show open file dialog
        File file = fileChooser.showOpenDialog(null);

        try {
            if (file != null) {
                BufferedImage bufferedImage = ImageIO.read(file);
                //Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                imageLocation = file.getAbsolutePath();
                imageArea.setText(imageLocation);
            } else {
                imageLocation = null;
                return;
            }
        } catch (IOException ex) {
           return;
        }
    }

    private void handleSubmitButton() {
        String titleString = titleField.getText();
        String abstractString = abstractField.getText();
        String bodyString = bodyField.getText();

        if (!titleString.trim().isEmpty()) {
            if (!abstractString.trim().isEmpty()) {
                if (!bodyString.trim().isEmpty()) {
                    try {
                        ClientToServerConnection.getOut().writeObject("submit  " + username + "  " + titleString + "  " + abstractString + "  " + bodyString + "  " + imageLocation);
                        alertSuccess.setContentText("Article successfully submitted");
                        alertSuccess.showAndWait();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    alertFail.setContentText("Body cannot be empty");
                    alertFail.showAndWait();
                }
            } else {
                alertFail.setContentText("Abstract cannot be empty");
                alertFail.showAndWait();
            }
        } else {
            alertFail.setContentText("Title cannot be empty");
            alertFail.showAndWait();
        }
    }

    private void handleViewArticlesButton() {
        try {
            articleListView.getItems().clear();
            ClientToServerConnection.getOut().writeObject("view  related  " + username);
            String o = (String) ClientToServerConnection.getIn().readObject();
            List<Article> articles = gson.fromJson(o, new TypeToken<List<Article>>() {}.getType());
            if (articles != null) {
                for (Article article : articles)
                    articleListView.getItems().add(article.getTitle());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void display() {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/UserFXML.fxml"));
            scene = new Scene(root);
            initialize();

            viewButton.setOnAction(event -> handleViewArticlesButton());

            submitButton.setOnAction(event -> handleSubmitButton());

            selectImage.setOnAction(event -> handleSelectImageButton());


            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
