package client.gui;

import client.listener.ClientToServerConnection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import server.model.Writer;

import java.io.IOException;

public class LoginWindow {

    private Scene scene;
    private Stage primaryStage;

    private Gson gson;
    private ClientToServerConnection clientToServerConnection;

    private static Alert alertSuccess;
    private static Alert alertFail;

    @FXML
    private TextField userField;

    @FXML
    private TextField passField;

    @FXML
    private Button loginButton;

    @FXML
    private Button readerButton;

    public LoginWindow(Stage stage, ClientToServerConnection clientToServerConnection) {
        this.primaryStage = stage;
        this.clientToServerConnection = clientToServerConnection;
        this.gson = new GsonBuilder().create();
    }

    private void initialize() {
        userField = (TextField) scene.lookup("#userField");
        passField = (TextField) scene.lookup("#passField");

        loginButton = (Button) scene.lookup("#loginButton");
        readerButton = (Button) scene.lookup("#readerButton");

        alertFail = new Alert(Alert.AlertType.ERROR);
        alertSuccess = new Alert(Alert.AlertType.INFORMATION);
    }

    private void handleLoginButtonAction(TextField user, TextField pass) {

        String userNameString = user.getText();
        String passwordString = pass.getText();

        if (!userNameString.trim().isEmpty()) {
            if (!passwordString.trim().isEmpty()) {

                try {
                    ClientToServerConnection.getOut().writeObject("login  "+userNameString+"  "+passwordString);
                    String o = (String) ClientToServerConnection.getIn().readObject();
                    Writer writer = gson.fromJson(o, Writer.class);

                    if (writer != null) {
                        alertSuccess.setContentText("Successful login");
                        alertSuccess.showAndWait();

                        ClientToServerConnection.getOut().writeObject("check  admin  "+userNameString+"  "+passwordString);
                        o = (String) ClientToServerConnection.getIn().readObject();
                        Boolean admin = gson.fromJson(o, Boolean.class);
                        if (!admin) {
                            UserWindow userWindow = new UserWindow(userNameString, clientToServerConnection);
                            userWindow.display();
                        } else {
                             AdminWindow adminWindow = new AdminWindow();
                            // adminWindow.display();
                        }
                    } else {
                        alertFail.setContentText("Incorrect username and/or password");
                        alertFail.showAndWait();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            } else {
                alertFail.setContentText("No field can be empty");
                alertFail.showAndWait();
            }
        } else {
            alertFail.setContentText("No field can be empty");
            alertFail.showAndWait();
        }
    }

    private void handleReaderButton() {
        ReaderWindow readerWindow = new ReaderWindow(clientToServerConnection);
        readerWindow.display();
    }

    public void display() {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/LoginFXML.fxml"));
            scene = new Scene(root);
            initialize();

            loginButton.setOnAction(event -> handleLoginButtonAction(userField, passField));
            readerButton.setOnAction(event -> handleReaderButton());

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
