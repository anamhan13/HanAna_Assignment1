package model;

import java.util.Date;

public class Game {

	private int idGame;
	private int score1;
	private int score2;
	private int idMatch;
	private Date date;
	private Winner winner;

	public Game(int idMatch, int score1, int score2) {
		this.idMatch = idMatch;
		this.score1 = score1;
		this.score2 = score2;
		this.winner = Winner.UNKNOWN;
	}

	public Game(int idMatch, int score1, int score2, Date date) {
		this.idMatch = idMatch;
		this.score1 = score1;
		this.score2 = score2;
		this.date = date;
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

	public int getIdMatch() {
		return idMatch;
	}

	public void setIdMatch(int idMatch) {
		this.idMatch = idMatch;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getIdGame() {
		return idGame;
	}

	public void setIdGame(int idGame) {
		this.idGame = idGame;
	}

	public Winner getWinner() {
		return winner;
	}

	public void setWinner(Winner winner) {
		this.winner = winner;
	}

	
}
