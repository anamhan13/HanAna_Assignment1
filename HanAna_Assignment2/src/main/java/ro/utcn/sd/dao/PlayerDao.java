package ro.utcn.sd.dao;

import ro.utcn.sd.model.Player;

public interface PlayerDao extends Dao<Player> {

	Player find(long id);
	
	Player findByMail(String mail);

    void delete(Player objectToDelete);

    void update(Player objectToUpdate);

    void insert(Player objectToCreate);

    void deleteById(long id);

    void closeConnection();

}