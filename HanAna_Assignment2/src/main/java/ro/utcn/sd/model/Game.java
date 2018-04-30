package ro.utcn.sd.model;

import java.util.Date;

import javax.persistence.*;


@Entity
@Table(name = "games")
public class Game {

	@Id
	@GeneratedValue
	private long idGame;

	@Column
	private int score1;

	@Column
	private int score2;

	@Column
	private int winner;

	@OneToOne(cascade=CascadeType.MERGE)
	@JoinColumn
	private Match match = new Match();
	
	@Column
	private Date date;
	
	public Game() {
		this.winner = -1;
		
		long millis=System.currentTimeMillis();  
		this.date=new java.util.Date(millis); 
	} 

	public Game(int score1, int score2, Match m) {
		this.score1 = score1;
		this.score2 = score2;
		this.match = m;
		this.winner =-1;
		
		long millis=System.currentTimeMillis();  
		this.date=new java.util.Date(millis); 
	}

	public Game(int score1, int score2, Date date,Match m) {
		this.score1 = score1;
		this.score2 = score2;
		this.date = date;
		this.match = m;
		this.winner =-1;
	}

	public int getScore1() {
		return score1;
	}

	public void setScore1(int score1) {
		this.score1 = score1;
	}

	public int getScore2() {
		return score2;
	}

	public void setScore2(int score2) {
		this.score2 = score2;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getIdGame() {
		return idGame;
	}

	public void setIdGame(long idGame) {
		this.idGame = idGame;
	}

	public int getWinner() {
		return winner;
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}
	
	public long getIdMatch(){ 
		return match.getIdMatch();	
	}
	
	public void setIdMatch(long id) {
		match.setIdMatch(id);
	}
	
}
