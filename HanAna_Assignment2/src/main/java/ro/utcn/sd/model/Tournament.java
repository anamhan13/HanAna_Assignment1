package ro.utcn.sd.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "tournaments")
public class Tournament {

	@Column
	private int winner;

	@Id
	@GeneratedValue
	private long idTournament;

	@Column
	private String name;

	@Column
	private String status;

	@Column
	private Date dateStart;

	@Column
	private Date dateFinish;
	
	@Column
	private double entryFee;
	
	@Column
	private double prize;
	
	@Column 
	private boolean paid;

	@OneToMany(cascade=CascadeType.MERGE)
	private Set<Match> matches = new HashSet<>();
	

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable(name = "enrolments", joinColumns = @JoinColumn(name = "idTournament"), inverseJoinColumns = @JoinColumn(name = "idPlayer"))
	private Set<Player> playersList = new HashSet<Player>();

	@Column
	private String place;

	@SuppressWarnings("deprecation")
	public Tournament() {
		this.winner = -1;
		long millis=System.currentTimeMillis();  
		dateStart = new java.util.Date(millis);
		dateFinish = new Date(dateStart.getTime());
		dateFinish.setMonth(dateFinish.getMonth()+1);
		entryFee = 0;
		prize = 0;
	}

	public Tournament(String name, String status, Date dateStart, Date dateFinish, String place, boolean paid) {
		this.name = name;
		this.status = status;
		this.dateStart = dateStart;
		this.dateFinish = dateFinish;
		this.place = place;
		this.winner = -1;
		this.paid = paid;
		if (!this.paid) { 
			entryFee = 0;
			prize = 0;
		} 
	}

	public long getIdTournament() {
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

	public int getWinner() {
		return winner;
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}

	public Set<Player> getPlayersList() {
		return playersList;
	}

	public void setPlayersList(Set<Player> playersList) {
		this.playersList = playersList;
	}

	public double getEntryFee() {
		return entryFee;
	}

	public void setEntryFee(double entryFee) {
		if (this.paid)
			this.entryFee = entryFee;
	}

	public double getPrize() {
		return prize;
	}

	public void setPrize(double prize) {
		if (this.paid)
			this.prize = prize;
	}

	public Set<Match> getMatches() {
		return matches;
	}

	public void setMatches(Set<Match> matches) {
		this.matches = matches;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}


}
