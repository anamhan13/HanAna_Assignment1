package ro.utcn.sd.dao.impl.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ro.utcn.sd.dao.PlayerDao;
import ro.utcn.sd.model.Player;
import ro.utcn.sd.util.ConnectionFactory;

public class JdbcPlayerDao implements PlayerDao{
	
	protected static final Logger LOGGER = Logger.getLogger("Player");
	private Connection connection;
	
	public JdbcPlayerDao() {
		this.connection = ConnectionFactory.getConnection();
	}
	
	public String createSelectQuery(String field) {
		StringBuilder sb = new StringBuilder();
		if (field != null)
			sb.append("SELECT * FROM players  WHERE " + field + " = ?");
		else
			sb.append("SELECT * FROM players");
		return sb.toString();
	}
	
	@Override
	public Player find(long id) {
		String query = createSelectQuery("idplayer");
		List<Player> list = null;
		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(query);
			statement.setLong(1, id);
			resultSet = statement.executeQuery();
			list = createPlayerObjects(resultSet);

			if (list.size() != 0) {
				System.out.println("[PLAYER/FIND BY ID] The requested entry was found!");
			}

		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "PlayerRepository: find " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);

		}

		if (list == null) {
			System.out.println("[PLAYER/FIND BY] Couldn't find the requested entry");
			return null;
		}
		return list.get(0);
	}

	@Override
	public void delete(Player objectToDelete) {
		PreparedStatement deleteStatement = null;
		String query = createDeleteQuery("idplayer");
		
		Player t = this.find(objectToDelete.getId());
		if (t == null) {
			System.out.println("[PLAYER/DELETE] There is no such entry in this table");
		}
		
		try {
			deleteStatement = connection.prepareStatement(query);
			deleteStatement.setLong(1, objectToDelete.getId());
			deleteStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "[PLAYER/DELETE] " + e.getMessage());
		} finally {
			ConnectionFactory.close(deleteStatement);
		}

		System.out.println("[PLAYER/DELETE] Entry in table successfully deleted!");
	
	}

	private String createUpdateQuery(String col) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE players SET ");
		sb.append("balance=?, isAdmin=?, mail=?, name=?, password=?");
		sb.append("WHERE " + col + "=?");
		return sb.toString();
	}
	
	@Override
	public void update(Player objectToUpdate) {
		PreparedStatement updateStatement = null;

		int updatedId = -1;
		String query = createUpdateQuery("idplayer");

		try {
			updateStatement = connection.prepareStatement(query);
			Player t = this.find(objectToUpdate.getId());

			if (t == null) {
				updatedId = -1;
				System.out.println("[PLAYER/UPDATE] There is no such entry in this table");
			} else {

				updateStatement.setDouble(1, objectToUpdate.getBalance());
				updateStatement.setBoolean(2,objectToUpdate.isAdmin());
				updateStatement.setString(3, objectToUpdate.getMail());
				updateStatement.setString(4, objectToUpdate.getName());
				updateStatement.setString(5, objectToUpdate.getPassword());
				updateStatement.setLong(6, objectToUpdate.getId());
				updateStatement.executeUpdate();
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "[PLAYER/UPDATE] " + e.getMessage());
		} finally {
			ConnectionFactory.close(updateStatement);
		}

		if (updatedId > 0) {
			System.out.println("[PLAYER/UPDATE] Entry in table successfully updated!");
		}
	}
	
	private String createInsertQuery(Player t) {

		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO players (balance, isAdmin, mail, name, password) VALUES (?,?,?,?,?)");
		return sb.toString();
	}

	@Override
	public void insert(Player objectToCreate) {
		PreparedStatement insertStatement = null;
		ResultSet rs = null;
		int insertedId = -1;

		try {
			insertStatement = connection.prepareStatement(createInsertQuery(objectToCreate),
					Statement.RETURN_GENERATED_KEYS);

			insertStatement.setDouble(1, objectToCreate.getBalance());
			insertStatement.setBoolean(2, objectToCreate.isAdmin());
			insertStatement.setString(3, objectToCreate.getMail());
			insertStatement.setString(4, objectToCreate.getName());
			insertStatement.setString(5, objectToCreate.getPassword());
			insertStatement.executeUpdate();
			rs = insertStatement.getGeneratedKeys();
		
			

			if (rs.next()) {
				insertedId = rs.getInt(1);
				System.out.println("[PLAYER/INSERT] Entry in table successfully inserted!");
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "[PLAYER/INSERT] " + e.getMessage());
		} finally {
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close(rs);
		}

		if (insertedId == -1) {
			System.out.println("[PLAYER/INSERT] Couldn't insert data in table");
		}
	}
	
	private String createDeleteQuery(String idS) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM players WHERE " + idS + "=?");
		return sb.toString();
	}
	
	protected List<Player> createPlayerObjects(ResultSet resultSet) {
		List<Player> list = new ArrayList<Player>();
		try {
			while (resultSet.next()) {
				Player instance = new Player();
				
				instance.setId(resultSet.getInt("idPlayer"));
				instance.setAdmin(resultSet.getBoolean("isAdmin"));
				instance.setBalance(resultSet.getDouble("balance"));
				instance.setMail(resultSet.getString("mail"));
				instance.setName(resultSet.getString("name"));
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

	@Override
	public void closeConnection() {

	}

	@Override
	public List<Player> findAll() {
		String query = createSelectQuery(null);
		List<Player> list = new ArrayList<Player>();
		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			list = createPlayerObjects(resultSet);

			if (list.size() != 0) {
				System.out.println("[PLAYER/FIND BY ID] The requested entry was found!");
			}

		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "PlayerRepository: find " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
		}

		return list;
	}

	@Override
	public void deleteById(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Player findByMail(String mail) {
		// TODO Auto-generated method stub
		return null;
	}
	
}