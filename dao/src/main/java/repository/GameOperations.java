package repository;

import java.util.List;

import model.Game;

public interface GameOperations {

	public Game find(String col, int id);
	
	public List<Game> findByMatch(int id);
	
	public List<Game> findAll();
	
	public int insert(Game game);
	
	public int update(Game game, int id);
	
	public int delete(int id);
	
}
