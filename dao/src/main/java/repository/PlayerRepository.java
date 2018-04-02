package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.dao.connection.ConnectionFactory;

import model.Player;

public class PlayerRepository implements PlayerOperations {

	protected static final Logger LOGGER = Logger.getLogger("Player");
	private Connection connection;

	public PlayerRepository(Connection connection) {
		this.connection = connection;
	}

	public String createSelectQuery(String field) {
		StringBuilder sb = new StringBuilder();
		if (field != null)
			sb.append("SELECT * FROM player WHERE " + field + " = ?");
		else
			sb.append("SELECT * FROM player");
		return sb.toString();
	}

	public Player find(String col, String mail) {
		String query = createSelectQuery("mail");
		List<Player> list = new ArrayList<Player>();
		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(query);
			statement.setString(1, mail);
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

		if (list.size() == 0) {
			System.out.println("[PLAYER/FIND BY] Couldn't find the requested entry");
			return null;
		}
		return list.get(0);
	}
	
	public Player findById(int id) {
		String query = createSelectQuery("idplayer");
		List<Player> list = new ArrayList<Player>();
		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
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

		if (list.size() == 0) {
			System.out.println("[PLAYER/FIND BY] Couldn't find the requested entry");
			return null;
		}
		return list.get(0);
	}


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

	private String createInsertQuery(Player t) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO player (idplayer, name, mail, password, address, isadmin) VALUES (?,?,?,?,?,?)");
		return sb.toString();
	}

	public int insert(Player player) {
		PreparedStatement insertStatement = null;
		ResultSet rs = null;
		int insertedId = -1;

		List<Player> players = new ArrayList<Player>();
		players = findAll();

		try {
			insertStatement = connection.prepareStatement(createInsertQuery(player), Statement.RETURN_GENERATED_KEYS);
			
			player.setIdPlayer(players.size() + 1);
			insertStatement.setInt(1, player.getIdPlayer());
			insertStatement.setString(2, player.getName());
			insertStatement.setString(3, player.getMail());
			insertStatement.setString(4, player.getPassword());
			insertStatement.setString(5, player.getAddress());
			insertStatement.setBoolean(6, player.isAdmin());

			int rows = insertStatement.executeUpdate();
			rs = insertStatement.getGeneratedKeys();

			if (rows != 0) {
				insertedId = player.getIdPlayer();
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
		} else
			player.setIdPlayer(insertedId);
		return insertedId;
	}

	private String createUpdateQuery(String col) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE player SET ");
		sb.append("name=?, password=?, address=?");
		sb.append("WHERE " + col + "=?");
		return sb.toString();
	}

	public int update(Player game, String mail) {
		PreparedStatement updateStatement = null;

		int updatedId = -1;
		String query = createUpdateQuery("mail");

		try {
			updateStatement = connection.prepareStatement(query);
			Player t = this.find("mail", mail);

			if (t == null) {
				updatedId = -1;
				System.out.println("[PLAYER/UPDATE] There is no such entry in this table");
			} else {

				updatedId = t.getIdPlayer();
				updateStatement.setString(1, game.getName());
				updateStatement.setString(2, game.getPassword());
				updateStatement.setString(3, game.getAddress());
				updateStatement.setString(4, mail);
			}
			updateStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "[PLAYER/UPDATE] " + e.getMessage());
		} finally {
			ConnectionFactory.close(updateStatement);
		}

		if (updatedId > 0) {
			System.out.println("[PLAYER/UPDATE] Entry in table successfully updated!");
		}
		return updatedId;
	}

	private String createDeleteQuery(String idS) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM player WHERE " + idS + "=?");
		return sb.toString();
	}

	public int delete(String mail) {
		PreparedStatement deleteStatement = null;
		String query = createDeleteQuery("mail");
		int deletedId = -1;

		Player t = this.find("mail", mail);
		if (t == null) {
			System.out.println("[PLAYER/DELETE] There is no such entry in this table");
			return -1;
		}
		try {
			deleteStatement = connection.prepareStatement(query);
			deleteStatement.setString(1, mail);
			deleteStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "[PLAYER/DELETE] " + e.getMessage());
		} finally {
			ConnectionFactory.close(deleteStatement);
		}

		deletedId = t.getIdPlayer();
		System.out.println("[PLAYER/DELETE] Entry in table successfully deleted!");
		return deletedId;
	}

	protected List<Player> createPlayerObjects(ResultSet resultSet) {
		List<Player> list = new ArrayList<Player>();
		try {
			while (resultSet.next()) {
				Player instance = new Player(false);

				instance.setIdPlayer(resultSet.getInt("idplayer"));
				instance.setName(resultSet.getString("name"));
				instance.setMail(resultSet.getString("mail"));
				instance.setPassword(resultSet.getString("password"));
				instance.setAddress(resultSet.getString("address"));
				instance.setAdmin(resultSet.getBoolean("isadmin"));

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
