package ro.utcn.sd.dao.impl.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ro.utcn.sd.dao.GameDao;
import ro.utcn.sd.model.Game;
import ro.utcn.sd.model.Match;
import ro.utcn.sd.util.ConnectionFactory;

public class JdbcGameDao  implements GameDao{
	
	protected static final Logger LOGGER = Logger.getLogger("Game");
	private Connection connection;
	
	public JdbcGameDao() {
		this.connection = ConnectionFactory.getConnection();
	}
	
	public String createSelectQuery(String field) {
		StringBuilder sb = new StringBuilder();
		if (field != null)
			sb.append("SELECT * FROM games  WHERE " + field + " = ?");
		else
			sb.append("SELECT * FROM games");
		return sb.toString();
	}
	
	@Override
	public Game find(long id) {
		String query = createSelectQuery("idgame");
		List<Game> list = null;
		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(query);
			statement.setLong(1, id);
			resultSet = statement.executeQuery();
			list = createGameObjects(resultSet);

			if (list.size() != 0) {
				System.out.println("[GAME/FIND BY ID] The requested entry was found!");
			}

		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "GameRepository: find " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);

		}

		if (list == null) {
			System.out.println("[GAME/FIND BY] Couldn't find the requested entry");
			return null;
		}
		return list.get(0);
	}

	@Override
	public void delete(Game objectToDelete) {
		PreparedStatement deleteStatement = null;
		String query = createDeleteQuery("idgame");
		
		Game t = this.find(objectToDelete.getIdGame());
		if (t == null) {
			System.out.println("[GAME/DELETE] There is no such entry in this table");
		}
		
		try {
			deleteStatement = connection.prepareStatement(query);
			deleteStatement.setLong(1, objectToDelete.getIdGame());
			deleteStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "[GAME/DELETE] " + e.getMessage());
		} finally {
			ConnectionFactory.close(deleteStatement);
		}

		System.out.println("[GAME/DELETE] Entry in table successfully deleted!");
	
	}

	private String createUpdateQuery(String col) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE games SET ");
		sb.append("date=?, score1=?, score2=?, winner=?, match_idMatch=?");
		sb.append("WHERE " + col + "=?");
		return sb.toString();
	}
	
	@Override
	public void update(Game objectToUpdate) {
		PreparedStatement updateStatement = null;

		int updatedId = -1;
		String query = createUpdateQuery("idgame");

		try {
			updateStatement = connection.prepareStatement(query);
			Game t = this.find(objectToUpdate.getIdGame());

			if (t == null) {
				updatedId = -1;
				System.out.println("[GAME/UPDATE] There is no such entry in this table");
			} else {

				updateStatement.setDate(1, (Date) objectToUpdate.getDate());
				updateStatement.setInt(2,objectToUpdate.getScore1());
				updateStatement.setInt(3, objectToUpdate.getScore2());
				updateStatement.setInt(4, objectToUpdate.getWinner());
				updateStatement.setLong(5, objectToUpdate.getMatch().getIdMatch());
				updateStatement.setLong(6, objectToUpdate.getIdGame());
				updateStatement.executeUpdate();
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "[GAME/UPDATE] " + e.getMessage());
		} finally {
			ConnectionFactory.close(updateStatement);
		}

		if (updatedId > 0) {
			System.out.println("[GAME/UPDATE] Entry in table successfully updated!");
		}
	}
	
	private String createInsertQuery(Game t) {

		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO games (date, score1, score2, winner, match_idMatch) VALUES (?,?,?,?,?)");
		return sb.toString();
	}

	@Override
	public void insert(Game objectToCreate) {
		PreparedStatement insertStatement = null;
		ResultSet rs = null;
		int insertedId = -1;

		try {
			insertStatement = connection.prepareStatement(createInsertQuery(objectToCreate),
					Statement.RETURN_GENERATED_KEYS);

			insertStatement.setDate(1, (Date) objectToCreate.getDate());
			insertStatement.setInt(2,objectToCreate.getScore1());
			insertStatement.setInt(3, objectToCreate.getScore2());
			insertStatement.setInt(4, objectToCreate.getWinner());
			insertStatement.setLong(5, objectToCreate.getMatch().getIdMatch());
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
	}
	
	private String createDeleteQuery(String idS) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM games WHERE " + idS + "=?");
		return sb.toString();
	}
	
	protected List<Game> createGameObjects(ResultSet resultSet) {
		List<Game> list = new ArrayList<Game>();
		try {
			while (resultSet.next()) {
				Game instance = new Game();
				
				JdbcMatchDao jdbcMatchDao = new JdbcMatchDao();
				Match match = jdbcMatchDao.find(resultSet.getLong("match_idMatch"));
				
				instance.setIdGame(resultSet.getLong("idGame"));
				instance.setWinner(resultSet.getInt("winner"));
				instance.setScore1(resultSet.getInt("score1"));
				instance.setScore2(resultSet.getInt("score2"));
				instance.setMatch(match);
				
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
	public List<Game> findAll() {
		String query = createSelectQuery(null);
		List<Game> list = new ArrayList<Game>();
		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			list = createGameObjects(resultSet);

			if (list.size() != 0) {
				System.out.println("[GAME/FIND BY ID] The requested entry was found!");
			}

		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "GameRepository: find " + e.getMessage());
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

}
