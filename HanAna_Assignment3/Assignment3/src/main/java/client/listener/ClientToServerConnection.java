package client.listener;

import client.gui.LoginWindow;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class ClientToServerConnection{

    private static int portNumber = 9990;
    private ServerListener serverListener;
    private Socket clientSocket;
    private static ObjectInputStream in;
    private static ObjectOutputStream out;

    public ClientToServerConnection() {
        try {
            clientSocket = new Socket("localhost", portNumber);
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
           // serverListener = new ServerListener(in);
            //serverListener.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ObjectInputStream getIn() {
        return in;
    }

    public static void setIn(ObjectInputStream in) {
        ClientToServerConnection.in = in;
    }

    public static ObjectOutputStream getOut() {
        return out;
    }

    public static void setOut(ObjectOutputStream out) {
        ClientToServerConnection.out = out;
    }

    /*
    public void establishConnection(String userInput) {
        try {
            if (userInput != null) {
                out.writeObject(userInput);
                System.out.println("echo: " + userInput);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
