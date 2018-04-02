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

import model.Tournament;

public class TournamentRepository implements TournamentOperations {

	protected static final Logger LOGGER = Logger.getLogger("Tournament");
	private Connection connection;

	public TournamentRepository(Connection connection) {
		this.connection = connection;
	}

	public String createSelectQuery(String field) {
		StringBuilder sb = new StringBuilder();
		if (field != null)
			sb.append("SELECT * FROM tournament  WHERE " + field + " = ?");
		else
			sb.append("SELECT * FROM tournament");
		return sb.toString();
	}

	public Tournament find(String col, int id) {
		String query = createSelectQuery("idtournament");
		List<Tournament> list = null;
		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			list = createTournamentObjects(resultSet);

			if (list.size() != 0) {
				System.out.println("[TOURNAMENT/FIND BY ID] The requested entry was found!");
			}

		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "TournamentRepository: find " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);

		}

		if (list == null) {
			System.out.println("[TOURNAMENT/FIND BY] Couldn't find the requested entry");
			return null;
		}
		return list.get(0);
	}

	public List<Tournament> findAll() {
		String query = createSelectQuery(null);
		List<Tournament> list = new ArrayList<Tournament>();
		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			list = createTournamentObjects(resultSet);

			if (list.size() != 0) {
				System.out.println("[TOURNAMENT/FIND BY ID] The requested entry was found!");
			}

		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "TournamentRepository: find " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
		}

		return list;
	}

	private String createInsertQuery(Tournament t) {

		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO tournament (name, status, datestart, datefinish, place) VALUES (?,?,?,?,?)");
		return sb.toString();
	}

	public int insert(Tournament tournament) {
		PreparedStatement insertStatement = null;
		ResultSet rs = null;
		int insertedId = -1;

		try {
			insertStatement = connection.prepareStatement(createInsertQuery(tournament),
					Statement.RETURN_GENERATED_KEYS);

			insertStatement.setString(1, tournament.getName());
			insertStatement.setString(2, tournament.getStatus().toString());
			insertStatement.setDate(3, new java.sql.Date(tournament.getDateStart().getTime()));
			insertStatement.setDate(4, new java.sql.Date(tournament.getDateFinish().getTime()));
			insertStatement.setString(5, tournament.getPlace());
			insertStatement.executeUpdate();
			rs = insertStatement.getGeneratedKeys();
		
			

			if (rs.next()) {
				insertedId = rs.getInt(1);
				System.out.println("[TOURNAMENT/INSERT] Entry in table successfully inserted!");
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "[TOURNAMENT/INSERT] " + e.getMessage());
		} finally {
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close(rs);
		}

		if (insertedId == -1) {
			System.out.println("[TOURNAMENT/INSERT] Couldn't insert data in table");
		}
		return insertedId;
	}

	private String createUpdateQuery(String col) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE tournament SET ");
		sb.append("name=?, status=?, datestart=?, datefinish=?, place=?");
		sb.append("WHERE " + col + "=?");
		return sb.toString();
	}

	public int update(Tournament tournament, int id) {
		PreparedStatement updateStatement = null;

		int updatedId = -1;
		String query = createUpdateQuery("idtournament");

		try {
			updateStatement = connection.prepareStatement(query);
			Tournament t = this.find("idtournament", id);
			updatedId = id;

			if (t == null) {
				updatedId = -1;
				System.out.println("[TOURNAMENT/UPDATE] There is no such entry in this table");
			} else {

				updateStatement.setString(1, tournament.getName());
				updateStatement.setString(2, tournament.getStatus().toString());
				updateStatement.setDate(3, new java.sql.Date(tournament.getDateStart().getTime()));
				updateStatement.setDate(4, new java.sql.Date(tournament.getDateFinish().getTime()));
				updateStatement.setString(5, tournament.getPlace());
				updateStatement.setInt(6, id);

				updateStatement.executeUpdate();
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "[TOURNAMENT/UPDATE] " + e.getMessage());
		} finally {
			ConnectionFactory.close(updateStatement);
		}

		if (updatedId > 0) {
			System.out.println("[TOURNAMENT/UPDATE] Entry in table successfully updated!");
		}
		return updatedId;
	}

	private String createDeleteQuery(String idS) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM tournament WHERE " + idS + "=?");
		return sb.toString();
	}

	public int delete(int id) {
		PreparedStatement deleteStatement = null;
		String query = createDeleteQuery("idtournament");
		int deletedId = -1;

		Tournament t = this.find("idtournament", id);
		if (t == null) {
			System.out.println("[TOURNAMENT/DELETE] There is no such entry in this table");
			return -1;
		}
		
		try {
			deleteStatement = connection.prepareStatement(query);
			deleteStatement.setInt(1, id);
			deleteStatement.executeUpdate();
			deletedId = id;
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "[TOURNAMENT/DELETE] " + e.getMessage());
		} finally {
			ConnectionFactory.close(deleteStatement);
		}

		System.out.println("[TOURNAMENT/DELETE] Entry in table successfully deleted!");
		return deletedId;
	}

	protected List<Tournament> createTournamentObjects(ResultSet resultSet) {
		List<Tournament> list = new ArrayList<Tournament>();
		try {
			while (resultSet.next()) {
				Tournament instance = new Tournament();

				instance.setIdTournament(resultSet.getInt("idtournament"));
				instance.setName(resultSet.getString("name"));
				instance.setStatus(resultSet.getString("status"));
				instance.setDateStart(resultSet.getDate("datestart"));
				instance.setDateFinish(resultSet.getDate("datefinish"));
				instance.setPlace(resultSet.getString("place"));

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
