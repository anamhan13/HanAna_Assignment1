package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Tournament {

	private int idTournament;
	private String name;
	private String status;
	private Date dateStart;
	private Date dateFinish;
	private String place;
	private List<Match> matchesList;
	private List<Player> playersList;
	private String winner;

	public Tournament() {
		matchesList = new ArrayList<Match>();
		playersList = new ArrayList<Player>();
	}

	public Tournament(String name, String status, Date dateStart, Date dateFinish, String place) {
		this.name = name;
		this.status = status;
		this.dateStart = dateStart;
		this.dateFinish = dateFinish;
		this.place = place;
	}

	public int getIdTournament() {
		return idTournament;
	}

	public void setIdTournament(int idTournament) {
		this.idTournament = idTournament;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = new String(status);
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateFinish() {
		return dateFinish;
	}

	public void setDateFinish(Date dateFinish) {
		this.dateFinish = dateFinish;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public List<Match> getMatchesList() {
		return matchesList;
	}

	public void setMatchesList(List<Match> matchesList) {
		this.matchesList = matchesList;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

	public List<Player> getPlayersList() {
		return playersList;
	}

	public void setPlayersList(List<Player> playersList) {
		this.playersList = playersList;
	}

}
