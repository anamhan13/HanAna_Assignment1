package client.gui;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ArticleWindow {

    private Stage stage;
    private Scene scene;

    @FXML
    private TextArea titleArea;

    @FXML
    private TextArea abstractArea;

    @FXML
    private TextArea bodyArea;

    @FXML
    private ImageView imageView;

    private String title;
    private String abstractArticle;
    private String body;
    private String imageLocation;
    private boolean containsImage;

    public ArticleWindow(String title, String abstractArticle, String body, String image, boolean containsImage) {
        this.stage = new Stage();
        this.title = title;
        this.abstractArticle =abstractArticle;
        this.body = body;
        this.imageLocation = image;
        this.containsImage = containsImage;
    }

    private void initialize() {
        this.titleArea = (TextArea) scene.lookup("#titleArea");
        this.abstractArea = (TextArea) scene.lookup("#abstractArea");
        this.bodyArea = (TextArea) scene.lookup("#bodyArea");

        this.imageView = (ImageView) scene.lookup("#image");
    }

    public void display() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ArticleFXML.fxml"));
            scene = new Scene(root);
            initialize();

            titleArea.setText(title);
            abstractArea.setText(abstractArticle);
            bodyArea.setText(body);

            System.out.println(imageLocation);
            if (!imageLocation.equals(null)) {
                BufferedImage bufferedImage = ImageIO.read(new File(imageLocation));
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                imageView.setImage(image);
            }

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
