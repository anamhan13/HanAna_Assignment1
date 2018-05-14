package server.commands;

import server.jdbc.ConnectionFactory;
import server.mappers.WriterMapper;
import server.model.Writer;

import java.sql.Connection;

public class RegisterCommand implements Command {

    private String name;
    private String mail;
    private String password;
    private Connection connection;

    public RegisterCommand(String name, String mail, String password) {
        this.name = name;
        this.mail = mail;
        this.password = password;
        connection = ConnectionFactory.getConnection();
    }

    public Object execute() {
        WriterMapper writerMapper = new WriterMapper(connection);
        Writer writer = new Writer();
        writer.setName(name);
        writer.setMail(mail);
        writer.setPassword(password);
        if (writerMapper.insert(writer) != -1) {
            return writerMapper.findByMail(mail);
        }
        return null;
    }
}
