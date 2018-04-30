package ro.utcn.sd.dao;

import java.util.List;

import ro.utcn.sd.model.Tournament;

public interface TournamentDao extends Dao<Tournament> {

	Tournament find(long id);
	
	List<Tournament> findByStatus(String status);
	
	Tournament findByName(String name);
	
	List<Tournament> findByType(String type);

	void delete(Tournament objectToDelete);

	void update(Tournament objectToUpdate);

	void insert(Tournament objectToCreate);

	void deleteById(long id);

	void closeConnection();

}