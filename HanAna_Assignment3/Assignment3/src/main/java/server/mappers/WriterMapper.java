package server.mappers;

import server.jdbc.ConnectionFactory;
import server.model.Writer;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WriterMapper implements Serializable {

    protected static final Logger LOGGER = Logger.getLogger("Writer");
    private Connection connection;

    public WriterMapper(Connection connection) {
        this.connection = connection;
    }

    public Writer find(int id) {
        String query = "SELECT * FROM writers WHERE id=?";
        List<Writer> list = new ArrayList<Writer>();
        ResultSet resultSet = null;
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            list = createBookObjects(resultSet);

            if (list.size() != 0) {
                System.out.println("[Writer/FIND] The requested entry was found!");
            }

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "WriterRepository: find " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
        }

        if (list.size() == 0) {
            System.out.println("[Writer/FIND] Couldn't find the requested entry");
            return null;
        }
        return list.get(0);
    }

    public Writer findByMail(String mail) {
        String query = "SELECT * FROM writers WHERE mail=?";
        List<Writer> list = new ArrayList<Writer>();
        ResultSet resultSet = null;
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, mail);
            resultSet = statement.executeQuery();
            list = createBookObjects(resultSet);

            if (list.size() != 0) {
                System.out.println("[Writer/FIND] The requested entry was found!");
            }

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "WriterRepository: find " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);

        }

        if (list.size() == 0) {
            System.out.println("[Writer/FIND] Couldn't find the requested entry");
            return null;
        }
        return list.get(0);
    }

    public List<Writer> findAll() {
        String query = "SELECT * FROM writers";
        List<Writer> list = new ArrayList<Writer>();
        ResultSet resultSet = null;
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            list = createBookObjects(resultSet);

            if (list.size() != 0) {
                System.out.println("[Writer/FIND] The requested entry was found!");
            }

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "[Writer/FIND] " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
        }

        return list;
    }

    public int insert(Writer writer) {
        PreparedStatement insertStatement = null;
        ResultSet rs = null;
        int insertedId = -1;
        String query = "INSERT INTO writers (mail, name, password) VALUES (?,?,?)";

        try {
            insertStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, writer.getMail());
            insertStatement.setString(2, writer.getName());
            insertStatement.setString(3, writer.getPassword());
            insertStatement.executeUpdate();
            rs = insertStatement.getGeneratedKeys();

            if (rs.next()) {
                insertedId = rs.getInt(1);
                System.out.println("[Writer/INSERT] Entry in table successfully inserted!");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "[Writer/INSERT] " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(rs);
        }

        if (insertedId == -1) {
            System.out.println("[Writer/INSERT] Couldn't insert data in table");
        }
        return insertedId;
    }

    public int update(Writer writer) {
        PreparedStatement updateStatement = null;
        ResultSet rSet = null;
        int updatedId = -1;
        String query = "UPDATE Writer SET title=?, author=?, Writerabstract=?, body=? WHERE idWriter = ?";

        try {
            updateStatement = connection.prepareStatement(query);

            if (writer == null) {
                updatedId = -1;
                System.out.println("[Writer/UPDATE] There is no such entry in this table");
            } else {
                updateStatement.setString(1, writer.getMail());
                updateStatement.setString(2, writer.getName());
                updateStatement.setString(3, writer.getPassword());
                updateStatement.executeUpdate();
                updatedId = writer.getId();
            }

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "[Writer/UPDATE] " + e.getMessage());
        } finally {
            ConnectionFactory.close(updateStatement);
            ConnectionFactory.close(rSet);
        }

        if (updatedId > 0) {
            System.out.println("[Writer/UPDATE] Entry in table successfully updated!");
        }
        return updatedId;
    }

    public int delete(int id) {
        PreparedStatement deleteStatement = null;
        String query = "DELETE FROM writers WHERE idWriter=?";
        int deletedId = -1;

        Writer t = this.find(id);
        if (t == null) {
            System.out.println("[Writer/DELETE] There is no such entry in this table");
            return -1;
        }
        try {
            deleteStatement = connection.prepareStatement(query);
            deleteStatement.setInt(1, id);
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "[Writer/DELETE] " + e.getMessage());
        } finally {
            ConnectionFactory.close(deleteStatement);
        }

        deletedId = t.getId();
        System.out.println("[Writer/DELETE] Entry in table successfully deleted!");
        return deletedId;
    }

    protected List<Writer> createBookObjects(ResultSet resultSet) {
        List<Writer> list = new ArrayList<Writer>();
        try {
            while (resultSet.next()) {
                Writer instance = new Writer();

                instance.setId(resultSet.getInt("idWriter"));
                instance.setName(resultSet.getString("name"));
                instance.setMail(resultSet.getString("mail"));
                instance.setPassword(resultSet.getString("password"));

                list.add(instance);
            }
            return list;
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
