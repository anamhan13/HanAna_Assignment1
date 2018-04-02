package model;

import java.util.ArrayList;
import java.util.List;

public class Match {

	private int idMatch;
	private String mail1;
	private String mail2;
	private int idTournament;
	private List<Game> games;
	private Winner winner;

	public Match() {
		games = new ArrayList<Game>();
		this.winner = Winner.UNKNOWN;
	}

	public Match(String mail1, String mail2) {
		this.mail1 = mail1;
		this.mail2 = mail2;
		this.winner = Winner.UNKNOWN;
	}

	public Match(String mail1, String mail2, int idTour) {
		this.mail1 = mail1;
		this.mail2 = mail2;
		this.idTournament = idTour;
		this.winner = Winner.UNKNOWN;
	}

	public String getMail1() {
		return mail1;
	}

	public void setMail1(String mail1) {
		this.mail1 = mail1;
	}

	public String getMail2() {
		return mail2;
	}

	public void setMail2(String mail2) {
		this.mail2 = mail2;
	}

	public int getIdTournament() {
		return idTournament;
	}

	public void setIdTournament(int idTournament) {
		this.idTournament = idTournament;
	}

	public int getIdMatch() {
		return idMatch;
	}

	public void setIdMatch(int idMatch) {
		this.idMatch = idMatch;
	}

	public List<Game> getGames() {
		return games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}

	public Winner getWinner() {
		return winner;
	}

	public void setWinner(Winner winner) {
		this.winner = winner;
	}

	@Override
	public String toString() {
		return "Match [idMatch=" + idMatch + ", mail1=" + mail1 + ", mail2=" + mail2 + ", idTournament=" + idTournament
				+ "]";
	}

}
