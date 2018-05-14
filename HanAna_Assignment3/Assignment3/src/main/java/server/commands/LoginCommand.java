package server.commands;

import server.jdbc.ConnectionFactory;
import server.mappers.WriterMapper;
import server.model.Writer;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class LoginCommand implements Command {

	private String username,password;
	private Connection connection;

	public LoginCommand(String username, String password) {
		super();
		this.username = username;
		this.password = password;
		connection = ConnectionFactory.getConnection();
	}

	public Object execute() {
        Writer writer = (new WriterMapper(connection)).findByMail(username);
        if (writer != null && writer.getPassword().equals(password))
		    return writer;
        return null;
	}

}
