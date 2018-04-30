package ro.utcn.sd.dao;

import ro.utcn.sd.model.Game;

public interface GameDao extends Dao<Game> {

	Game find(long id);

    void delete(Game objectToDelete);

    void update(Game objectToUpdate);

    void insert(Game objectToCreate);

    void deleteById(long id);

    void closeConnection();

}