package client.main;

import client.gui.LoginWindow;
import client.listener.ClientToServerConnection;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainClient extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {

        ClientToServerConnection client = new ClientToServerConnection();

        Stage stage = new Stage();
        LoginWindow loginWindow = new LoginWindow(stage, client);
        loginWindow.display();
    }

}
