package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.dao.connection.ConnectionFactory;

import model.Game;
import model.Match;

public class GameRepository implements GameOperations {

	protected static final Logger LOGGER = Logger.getLogger("Game");
	private Connection connection;

	public GameRepository(Connection connection) {
		this.connection = connection;
	}

	public String createSelectQuery(String field) {
		StringBuilder sb = new StringBuilder();
		if (field != null)
			sb.append("SELECT * FROM game  WHERE " + field + " = ?");
		else
			sb.append("SELECT * FROM game");
		return sb.toString();
	}

	public Game find(String col, int id) {
		String query = createSelectQuery("idgame");
		List<Game> list = new ArrayList<Game>();
		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			list = createBookObjects(resultSet);

			if (list.size() != 0) {
				System.out.println("[GAME/FIND] The requested entry was found!");
			}

		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "GameRepository: find " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);

		}

		if (list.size() == 0) {
			System.out.println("[GAME/FIND] Couldn't find the requested entry");
			return null;
		}
		return list.get(0);
	}
	
	public List<Game> findByMatch(int id) {
		String query = createSelectQuery("idmatch");
		List<Game> list = new ArrayList<Game>();
		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			list = createBookObjects(resultSet);

			if (list.size() != 0) {
				System.out.println("[GAME/FIND] The requested entry was found!");
			}

		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "GameRepository: find " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);

		}

		if (list.size() == 0) {
			System.out.println("[GAME/FIND] Couldn't find the requested entry");
			return null;
		}
		return list;
	}

	public List<Game> findAll() {
		String query = createSelectQuery(null);
		List<Game> list = new ArrayList<Game>();
		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			list = createBookObjects(resultSet);

			if (list.size() != 0) {
				System.out.println("[GAME/FIND] The requested entry was found!");
			}

		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "[GAME/FIND] " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
		}

		return list;
	}

	private String createInsertQuery(Game t) {

		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO game (score1, score2, date, idmatch) VALUES (?,?,?,?)");

		return sb.toString();
	}

	public int insert(Game game) {
		PreparedStatement insertStatement = null;
		ResultSet rs = null;
		int insertedId = -1;

		try {
			insertStatement = connection.prepareStatement(createInsertQuery(game), Statement.RETURN_GENERATED_KEYS);
			insertStatement.setInt(1, game.getScore1());
			insertStatement.setInt(2, game.getScore2());
			insertStatement.setDate(3, new java.sql.Date(game.getDate().getTime()));
			insertStatement.setInt(4, game.getIdMatch());
			insertStatement.executeUpdate();
			rs = insertStatement.getGeneratedKeys();

			if (rs.next()) {
				insertedId = rs.getInt(1);
				System.out.println("[GAME/INSERT] Entry in table successfully inserted!");
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "[GAME/INSERT] " + e.getMessage());
		} finally {
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close(rs);
		}

		if (insertedId == -1) {
			System.out.println("[GAME/INSERT] Couldn't insert data in table");
		}
		return insertedId;
	}

	private String createUpdateQuery(String col) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE game SET score1=?, score2=?, idmatch=?, date=? WHERE " + col + " = ?");
		return sb.toString();
	}

	public int update(Game game, int id) {
		PreparedStatement updateStatement = null;
		ResultSet rSet = null;
		int updatedId = -1;
		String query = this.createUpdateQuery("idgame");

		try {
			updateStatement = connection.prepareStatement(query);

			Game t = this.find("idgame", id);

			if (t == null) {
				updatedId = -1;
				System.out.println("[GAME/UPDATE] There is no such entry in this table");
			} else {
				MatchRepository matchRepository = new MatchRepository(connection);
				Match match = matchRepository.find("idmatch", game.getIdMatch());

				if (match != null) {
					updatedId = t.getIdMatch();

					updateStatement.setInt(1, game.getScore1());
					updateStatement.setInt(2, game.getScore2());
					updateStatement.setInt(3, game.getIdMatch());
					updateStatement.setDate(4, new java.sql.Date(game.getDate().getTime()));
					updateStatement.setInt(5, id);
					updateStatement.executeUpdate();

				} else {
					updatedId = -1;
					System.out.println("[MATCH/INSERT] 1st mail not found !");
				}
			}

		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "[GAME/UPDATE] " + e.getMessage());
		} finally {
			ConnectionFactory.close(updateStatement);
			ConnectionFactory.close(rSet);
		}

		if (updatedId > 0) {
			System.out.println("[GAME/UPDATE] Entry in table successfully updated!");
		}
		return updatedId;
	}
	
	private String createDeleteQuery(String idS) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM game WHERE " + idS + "=?");
		return sb.toString();
	}

	public int delete(int id) {
		PreparedStatement deleteStatement = null;
		String query = createDeleteQuery("idgame");
		int deletedId = -1;

		Game t = this.find("idgame", id);
		if (t == null) {
			System.out.println("[GAME/DELETE] There is no such entry in this table");
			return -1;
		}
		try {
			deleteStatement = connection.prepareStatement(query);
			deleteStatement.setInt(1, id);
			deleteStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "[GAME/DELETE] " + e.getMessage());
		} finally {
			ConnectionFactory.close(deleteStatement);
		}

		deletedId = t.getIdMatch();
		System.out.println("[GAME/DELETE] Entry in table successfully deleted!");
		return deletedId;
	}

	protected List<Game> createBookObjects(ResultSet resultSet) {
		List<Game> list = new ArrayList<Game>();
		try {
			while (resultSet.next()) {
				Game instance = new Game(0, 0, 0);

				instance.setIdGame(resultSet.getInt("idgame"));
				instance.setScore1(resultSet.getInt("score1"));
				instance.setScore2(resultSet.getInt("score2"));

				Date date = resultSet.getDate("date");

				instance.setDate(date);

				instance.setIdMatch(resultSet.getInt("idmatch"));

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
