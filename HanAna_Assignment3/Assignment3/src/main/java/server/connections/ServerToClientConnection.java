package server.connections;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import server.commands.Command;
import server.commands.CommandFactory;
import server.mappers.WriterMapper;
import server.model.Writer;

public class ServerToClientConnection extends Thread {
	private Socket connection;
	private int ID;
	private ObjectOutputStream outStream;
	private ObjectInputStream inStream;

	public ServerToClientConnection(Socket connection, int iD) {
		super();
		this.connection = connection;
		ID = iD;
	}

	@Override
	public void run() {
		try {
			inStream = new ObjectInputStream(connection.getInputStream());
			outStream = new ObjectOutputStream(connection.getOutputStream());
            String received;

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

			while (!(received = (String)inStream.readObject() ).equals("exit")) {

                String[] args = received.split("  ");
                System.out.println(Arrays.toString(args));
                Command command = CommandFactory.getCommand(args);
                if (command != null) {
                    System.out.println(received);
                    Object object = command.execute();
                    String gsonString = gson.toJson(object);
                    if (object != null)
                        System.out.println(object.getClass().toString());
                    outStream.writeObject(gsonString);
                } else {
                    outStream.writeObject(null);
                }
            }
            outStream.writeObject("Connection about to close");

		} catch (SocketException e) {
			System.out.println("Client " + ID + " lost");
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	public Socket getConnection() {
		return connection;
	}

	public void setConnection(Socket connection) {
		this.connection = connection;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
	
	public void sendObj(Object msg) throws IOException{
		if(outStream!=null)
			outStream.writeObject(msg);
	}

}
