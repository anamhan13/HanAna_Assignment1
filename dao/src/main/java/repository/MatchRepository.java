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

import model.Match;
import model.Player;
import model.Tournament;

public class MatchRepository implements MatchOperations {

	protected static final Logger LOGGER = Logger.getLogger("Match");
	private Connection connection;

	public MatchRepository(Connection connection) {
		this.connection = connection;
	}

	public String createSelectQuery(String field) {
		StringBuilder sb = new StringBuilder();
		if (field != null)
			sb.append("SELECT * FROM `match`  WHERE " + field + " = ?");
		else
			sb.append("SELECT * FROM `match`");
		return sb.toString();
	}

	public Match find(String col, int id) {
		String query = createSelectQuery("idmatch");
		List<Match> list = new ArrayList<Match>();
		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
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

		if (list.size() == 0) {
			System.out.println("[MATCH/FIND BY ID] Couldn't find the requested entry");
			return null;
		}
		return list.get(0);
	}
	
	public List<Match> findByPlayer(String mail) {
		String query = "SELECT * FROM `match` WHERE mail1=? or mail2=?";
		List<Match> list = new ArrayList<Match>();
		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(query);
			statement.setString(1, mail);
			statement.setString(2, mail);
			resultSet = statement.executeQuery();
			list = createMatchObjects(resultSet);

			if (list.size() != 0) {
				System.out.println("[MATCH/FIND BY PLAYER] The requested entry was found!");
			}

		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "MatchRepository: find " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);

		}

		if (list.size() == 0) {
			System.out.println("[MATCH/FIND BY PLAYER] Couldn't find the requested entry");
			return null;
		}
		return list;
	}

	public List<Match> findByTournament(int id) {
		String query = createSelectQuery("idtournament");
		List<Match> list = new ArrayList<Match>();
		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			list = createMatchObjects(resultSet);

			if (list.size() != 0) {
				System.out.println("[MATCH/FIND BY TOURNAMENT] The requested entry was found!");
			}

		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "MatchRepository: find " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);

		}

		if (list.size() == 0) {
			System.out.println("[MATCH/FIND BY TOURNAMENT] Couldn't find the requested entry");
			return null;
		}
		return list;
	}
	
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

	private String createInsertQuery(Match t) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO `match` (mail1, mail2, idtournament) VALUES (");
		sb.append("(SELECT mail FROM player WHERE mail = ?), ");
		sb.append("(SELECT mail FROM player WHERE mail = ?), ");
		sb.append("(SELECT idtournament FROM tournament WHERE idtournament = ?))");
		return sb.toString();
	}

	public int insert(Match match) {
		PreparedStatement insertStatement = null;
		ResultSet rs = null;
		int insertedId = -1;

		try {
			insertStatement = connection.prepareStatement(createInsertQuery(match), Statement.RETURN_GENERATED_KEYS);

			PlayerRepository playerRepository = new PlayerRepository(connection);

			Player player1 = playerRepository.find("mail", match.getMail1());

			if (player1 != null) {
				Player player2 = playerRepository.find("mail", match.getMail2());

				if (player2 != null) {

					TournamentRepository tournamentRepository = new TournamentRepository(connection);
					Tournament tournament = tournamentRepository.find("idtournament", match.getIdTournament());

					if (tournament != null) {
						insertStatement.setString(1, match.getMail1());
						insertStatement.setString(2, match.getMail2());
						insertStatement.setInt(3, match.getIdTournament());

						insertStatement.executeUpdate();
						rs = insertStatement.getGeneratedKeys();

						if (rs.next()) {
							insertedId = rs.getInt(1);
							System.out.println("[MATCH/INSERT] Entry in table successfully inserted!");
						}
					} else {
						insertedId = -1;
						System.out.println("[MATCH/INSERT] Tournament not found !");
					}
				} else {
					insertedId = -1;
					System.out.println("[MATCH/INSERT] 2nd mail not found !");
				}
			} else {
				insertedId = -1;
				System.out.println("[MATCH/INSERT] 1st mail not found !");
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
		return insertedId;
	}

	private String createUpdateQuery(String col) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE `match` SET mail1=?, mail2=?, idtournament=? WHERE idmatch = ?");
		return sb.toString();
	}

	// FA-L SA MEARGA
	public int update(Match match, int id) {
		PreparedStatement updateStatement = null;
		ResultSet rSet = null;
		int updatedId = -1;
		String query = this.createUpdateQuery("idmatch");

		try {
			updateStatement = connection.prepareStatement(query);

			Match t = this.find("idmatch", id);
			
			if (t == null) {
				updatedId = -1;
				System.out.println("[MATCH/UPDATE] There is no such entry in this table");
			} else {

				PlayerRepository playerRepository = new PlayerRepository(connection);

				Player player1 = playerRepository.find("mail", match.getMail1());

				if (player1 != null) {
					Player player2 = playerRepository.find("mail", match.getMail2());

					if (player2 != null) {

						TournamentRepository tournamentRepository = new TournamentRepository(connection);
						Tournament tournament = tournamentRepository.find("idtournament", match.getIdTournament());

						if (tournament != null) {
							updatedId = t.getIdMatch();

							updateStatement.setString(1, match.getMail1());
							updateStatement.setString(2, match.getMail2());
							updateStatement.setInt(3, match.getIdTournament());
							updateStatement.setInt(4, id);
							updateStatement.executeUpdate();
						} else {
							updatedId = -1;
							System.out.println("[MATCH/INSERT] Tournament not found !");
						}
					} else {
						updatedId = -1;
						System.out.println("[MATCH/INSERT] 2nd mail not found !");
					}
				} else {
					updatedId = -1;
					System.out.println("[MATCH/INSERT] 1st mail not found !");
				}
			}

		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "[MATCH/UPDATE] " + e.getMessage());
		} finally {
			ConnectionFactory.close(updateStatement);
			ConnectionFactory.close(rSet);
		}

		if (updatedId > 0) {
			System.out.println("[MATCH/UPDATE] Entry in table successfully updated!");
		}
		return updatedId;
	}

	private String createDeleteQuery(String idS) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM `match` WHERE " + idS + "=?");
		return sb.toString();
	}

	public int delete(int id) {
		PreparedStatement deleteStatement = null;
		String query = createDeleteQuery("idmatch");
		int deletedId = -1;

		Match t = this.find("idmatch", id);
		if (t == null) {
			System.out.println("[MATCH/DELETE] There is no such entry in this table");
			return -1;
		}
		try {
			deleteStatement = connection.prepareStatement(query);
			deleteStatement.setInt(1, id);
			deleteStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "[MATCH/DELETE] " + e.getMessage());
		} finally {
			ConnectionFactory.close(deleteStatement);
		}

		deletedId = t.getIdMatch();
		System.out.println("[MATCH/DELETE] Entry in table successfully deleted!");
		return deletedId;
	}

	protected List<Match> createMatchObjects(ResultSet resultSet) {
		List<Match> list = new ArrayList<Match>();
		try {
			while (resultSet.next()) {
				Match instance = new Match();

				instance.setIdMatch(resultSet.getInt("idmatch"));
				instance.setMail1(resultSet.getString("mail1"));
				instance.setMail2(resultSet.getString("mail2"));
				instance.setIdTournament(resultSet.getInt("idtournament"));
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
