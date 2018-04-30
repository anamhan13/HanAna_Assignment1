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

import ro.utcn.sd.dao.MatchDao;
import ro.utcn.sd.model.Match;
import ro.utcn.sd.model.Player;
import ro.utcn.sd.model.Tournament;
import ro.utcn.sd.util.ConnectionFactory;

public class JdbcMatchDao implements MatchDao{
	
	protected static final Logger LOGGER = Logger.getLogger("Match");
	private Connection connection;
	
	public JdbcMatchDao() {
		this.connection = ConnectionFactory.getConnection();
	}
	
	public String createSelectQuery(String field) {
		StringBuilder sb = new StringBuilder();
		if (field != null)
			sb.append("SELECT * FROM matches  WHERE " + field + " = ?");
		else
			sb.append("SELECT * FROM matches");
		return sb.toString();
	}
	
	@Override
	public Match find(long id) {
		String query = createSelectQuery("idmatch");
		List<Match> list = null;
		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(query);
			statement.setLong(1, id);
			resultSet = statement.executeQuery();
			list = createMatchObjects(resultSet);

			if (list.size() != 0) {
				System.out.println("[MATCH/FIND BY ID] The requested entry was found!");
			}

		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "MatchRepository: find " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);

		}

		if (list == null) {
			System.out.println("[MATCH/FIND BY] Couldn't find the requested entry");
			return null;
		}
		return list.get(0);
	}

	@Override
	public void delete(Match objectToDelete) {
		PreparedStatement deleteStatement = null;
		String query = createDeleteQuery("idmatch");
		
		Match t = this.find(objectToDelete.getIdMatch());
		if (t == null) {
			System.out.println("[MATCH/DELETE] There is no such entry in this table");
		}
		
		try {
			deleteStatement = connection.prepareStatement(query);
			deleteStatement.setLong(1, objectToDelete.getIdMatch());
			deleteStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "[MATCH/DELETE] " + e.getMessage());
		} finally {
			ConnectionFactory.close(deleteStatement);
		}

		System.out.println("[MATCH/DELETE] Entry in table successfully deleted!");
	
	}

	private String createUpdateQuery(String col) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE matches SET ");
		sb.append("winner=?, player1_idPlayer=?, player2_idPlayer=?, tournament_idTournament=?");
		sb.append("WHERE " + col + "=?");
		return sb.toString();
	}
	
	@Override
	public void update(Match objectToUpdate) {
		PreparedStatement updateStatement = null;

		int updatedId = -1;
		String query = createUpdateQuery("idmatch");

		try {
			updateStatement = connection.prepareStatement(query);
			Match t = this.find(objectToUpdate.getIdMatch());

			if (t == null) {
				updatedId = -1;
				System.out.println("[MATCH/UPDATE] There is no such entry in this table");
			} else {

				updateStatement.setInt(1,objectToUpdate.getWinner());
				updateStatement.setLong(2, objectToUpdate.getPlayer1().getId());
				updateStatement.setLong(3, objectToUpdate.getPlayer2().getId());
				updateStatement.setLong(4, objectToUpdate.getTournament().getIdTournament());
				updateStatement.setLong(5, objectToUpdate.getIdMatch());
				updateStatement.executeUpdate();
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "[MATCH/UPDATE] " + e.getMessage());
		} finally {
			ConnectionFactory.close(updateStatement);
		}

		if (updatedId > 0) {
			System.out.println("[MATCH/UPDATE] Entry in table successfully updated!");
		}
	}
	
	private String createInsertQuery(Match t) {

		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO matches (winner, player1_idPlayer, player2_idPlayer, tournament_idTournament) VALUES (?,?,?,?)");
		return sb.toString();
	}

	@Override
	public void insert(Match objectToCreate) {
		PreparedStatement insertStatement = null;
		ResultSet rs = null;
		int insertedId = -1;

		try {
			insertStatement = connection.prepareStatement(createInsertQuery(objectToCreate),
					Statement.RETURN_GENERATED_KEYS);

			insertStatement.setInt(1, objectToCreate.getWinner());
			insertStatement.setLong(2, objectToCreate.getPlayer1().getId());
			insertStatement.setLong(3, objectToCreate.getPlayer2().getId());
			insertStatement.setLong(4, objectToCreate.getTournament().getIdTournament());
			insertStatement.executeUpdate();
			rs = insertStatement.getGeneratedKeys();
		
			

			if (rs.next()) {
				insertedId = rs.getInt(1);
				System.out.println("[MATCH/INSERT] Entry in table successfully inserted!");
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "[MATCH/INSERT] " + e.getMessage());
		} finally {
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close(rs);
		}

		if (insertedId == -1) {
			System.out.println("[MATCH/INSERT] Couldn't insert data in table");
		}
	}
	
	private String createDeleteQuery(String idS) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM matches WHERE " + idS + "=?");
		return sb.toString();
	}
	
	protected List<Match> createMatchObjects(ResultSet resultSet) {
		List<Match> list = new ArrayList<Match>();
		try {
			while (resultSet.next()) {
				Match instance = new Match();
				
				JdbcPlayerDao jdbcPlayerDao = new JdbcPlayerDao();
				Player p1 = jdbcPlayerDao.find(resultSet.getLong("player1_idPlayer"));
				Player p2 = jdbcPlayerDao.find(resultSet.getLong("player2_idPlayer"));
				
				JdbcTournamentDao jdbcTournamentDao = new JdbcTournamentDao();
				Tournament tournament = jdbcTournamentDao.find(resultSet.getLong("tournament_idTournament"));
				
				instance.setIdMatch(resultSet.getInt("idmatch"));
				instance.setPlayer1(p1);
				instance.setPlayer2(p2);
				instance.setTournament(tournament);
				
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
	public List<Match> findAll() {
		String query = createSelectQuery(null);
		List<Match> list = new ArrayList<Match>();
		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			list = createMatchObjects(resultSet);

			if (list.size() != 0) {
				System.out.println("[MATCH/FIND BY ID] The requested entry was found!");
			}

		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "MatchRepository: find " + e.getMessage());
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
	public List<Match> findByPlayer(Player player) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Match> findByTournament(Tournament tournament) {
		// TODO Auto-generated method stub
		return null;
	}
}
