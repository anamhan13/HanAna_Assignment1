package ro.utcn.sd.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "matches")
public class Match {

	@Id
	@GeneratedValue
	private long idMatch;

	@OneToMany(cascade = CascadeType.MERGE)
	private Set<Game> games = new HashSet<Game>();

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn
	private Tournament tournament = new Tournament();

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(referencedColumnName = "idPlayer")
	private Player player1 = new Player();

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(referencedColumnName = "idPlayer")
	private Player player2 = new Player();

	@Column
	private int winner;

	public Match() {
		this.winner = -1;
		player1 = new Player();
		player2 = new Player();
		tournament = new Tournament();
	}

	public Match(Player p1, Player p2, Tournament tournament) {
		this.player1 = p1;
		this.player2 = p2;
		this.tournament = tournament;
		this.winner = -1;
	}

	public long getIdMatch() {
		return idMatch;
	}

	public void setIdMatch(long idMatch) {
		this.idMatch = idMatch;
	}

	public Set<Game> getGames() {
		return games;
	}

	public void setGames(Set<Game> games) {
		this.games = games;
	}

	public int getWinner() {
		return winner;
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}

	@Override
	public String toString() {
		return "Match [idMatch=" + idMatch + ",  idTournament=" + "]";
	}

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public Tournament getTournament() {
		return tournament;
	}

	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}

	public long getIdPlayer1() {
		return player1.getId();
	}

	public long getIdPlayer2() {
		return player2.getId();
	}

	public long getIdTournament() {
		return tournament.getIdTournament();
	}

}
