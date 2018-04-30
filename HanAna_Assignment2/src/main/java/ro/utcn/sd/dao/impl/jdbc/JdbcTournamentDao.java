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

import ro.utcn.sd.dao.TournamentDao;
import ro.utcn.sd.model.Tournament;
import ro.utcn.sd.util.ConnectionFactory;

public class JdbcTournamentDao implements TournamentDao {

	protected static final Logger LOGGER = Logger.getLogger("Tournament");
	private Connection connection;

	public JdbcTournamentDao() {
		this.connection = ConnectionFactory.getConnection();
	}

	public String createSelectQuery(String field) {
		StringBuilder sb = new StringBuilder();
		if (field != null)
			sb.append("SELECT * FROM tournaments  WHERE " + field + " = ?");
		else
			sb.append("SELECT * FROM tournaments");
		return sb.toString();
	}

	@Override
	public Tournament find(long id) {
		String query = createSelectQuery("idtournament");
		List<Tournament> list = null;
		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(query);
			statement.setLong(1, id);
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

	private String createDeleteQuery(String idS) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM tournaments WHERE " + idS + "=?");
		return sb.toString();
	}

	@Override
	public void delete(Tournament objectToDelete) {
		PreparedStatement deleteStatement = null;
		String query = createDeleteQuery("idtournament");

		Tournament t = this.find(objectToDelete.getIdTournament());
		if (t == null) {
			System.out.println("[TOURNAMENT/DELETE] There is no such entry in this table");
		}

		try {
			deleteStatement = connection.prepareStatement(query);
			deleteStatement.setLong(1, objectToDelete.getIdTournament());
			deleteStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "[TOURNAMENT/DELETE] " + e.getMessage());
		} finally {
			ConnectionFactory.close(deleteStatement);
		}

		System.out.println("[TOURNAMENT/DELETE] Entry in table successfully deleted!");

	}

	private String createUpdateQuery(String col) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE tournaments SET ");
		sb.append("name=?, status=?, datestart=?, datefinish=?, place=?, entryFee=?, prize=?, winner=?");
		sb.append("WHERE " + col + "=?");
		return sb.toString();
	}

	@Override
	public void update(Tournament objectToUpdate) {
		PreparedStatement updateStatement = null;

		int updatedId = -1;
		String query = createUpdateQuery("idtournament");

		try {
			updateStatement = connection.prepareStatement(query);
			Tournament t = this.find(objectToUpdate.getIdTournament());

			if (t == null) {
				updatedId = -1;
				System.out.println("[TOURNAMENT/UPDATE] There is no such entry in this table");
			} else {

				updateStatement.setString(1, objectToUpdate.getName());
				updateStatement.setString(2, objectToUpdate.getStatus().toString());
				updateStatement.setDate(3, new java.sql.Date(objectToUpdate.getDateStart().getTime()));
				updateStatement.setDate(4, new java.sql.Date(objectToUpdate.getDateFinish().getTime()));
				updateStatement.setString(5, objectToUpdate.getPlace());
				updateStatement.setDouble(6, objectToUpdate.getEntryFee());
				updateStatement.setDouble(7, objectToUpdate.getPrize());
				updateStatement.setInt(8, objectToUpdate.getWinner());
				updateStatement.setLong(9, objectToUpdate.getIdTournament());

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
	}

	private String createInsertQuery(Tournament t) {

		StringBuilder sb = new StringBuilder();
		sb.append(
				"INSERT INTO tournaments (name, status, datestart, datefinish, place, entryFee, prize, winner) VALUES (?,?,?,?,?,?,?,?)");
		return sb.toString();
	}

	@Override
	public void insert(Tournament objectToCreate) {
		PreparedStatement insertStatement = null;
		ResultSet rs = null;
		int insertedId = -1;

		try {
			insertStatement = connection.prepareStatement(createInsertQuery(objectToCreate),
					Statement.RETURN_GENERATED_KEYS);

			insertStatement.setString(1, objectToCreate.getName());
			insertStatement.setString(2, objectToCreate.getStatus().toString());
			insertStatement.setDate(3, new java.sql.Date(objectToCreate.getDateStart().getTime()));
			insertStatement.setDate(4, new java.sql.Date(objectToCreate.getDateFinish().getTime()));
			insertStatement.setString(5, objectToCreate.getPlace());
			insertStatement.setDouble(6, objectToCreate.getEntryFee());
			insertStatement.setDouble(7, objectToCreate.getPrize());
			insertStatement.setInt(8, objectToCreate.getWinner());
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
	}

	protected List<Tournament> createTournamentObjects(ResultSet resultSet) {
		List<Tournament> list = new ArrayList<Tournament>();
		try {
			while (resultSet.next()) {
				Tournament instance = new Tournament();

				instance.setIdTournament(resultSet.getInt("idTournament"));
				instance.setName(resultSet.getString("name"));
				instance.setStatus(resultSet.getString("status"));
				instance.setDateStart(resultSet.getDate("dateStart"));
				instance.setDateFinish(resultSet.getDate("dateFinish"));
				instance.setEntryFee(resultSet.getDouble("etryFee"));
				instance.setPlace(resultSet.getString("place"));
				instance.setPrize(resultSet.getDouble("prize"));
				instance.setWinner(resultSet.getInt("winner"));

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

	@Override
	public void deleteById(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Tournament> findByStatus(String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tournament findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tournament> findByType(String type) {
		// TODO Auto-generated method stub
		return null;
	}
}
