package repository;

import java.util.List;

import model.Player;

public interface PlayerOperations {

	public Player find(String col, String mail);
	
	public List<Player> findAll();
	
	public int insert(Player player);
	
	public int update(Player player, String mail);
	
	public int delete(String mail);
}
