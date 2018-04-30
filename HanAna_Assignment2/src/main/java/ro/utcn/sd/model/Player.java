package ro.utcn.sd.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "players")
public class Player {

	@Id
	@GeneratedValue
	private long idPlayer;
	
	@Column
	private String mail;

	@Column
	private String name;

	@Column
	private String password;

	@Column
	private boolean isAdmin;
	
	@Column
	private double balance;

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "playersList", cascade = CascadeType.MERGE)
	private Set<Tournament> tournamentsList = new HashSet<Tournament>();
	
	@Transient
	private static boolean adminExists;
	
	public Player() {} 
	
	public Player(boolean admin) {

		if (adminExists) {
			this.isAdmin = false;
		} else {
			this.isAdmin = admin;
			if (admin)
				adminExists = true;
		}
	}

	public Player(String name, String mail, String password, String address, boolean admin, double balance) {

		this.mail = mail;
		this.password = password;
		this.name = name;
		this.balance = balance;

		if (adminExists) {
			this.isAdmin = false;
		} else {
			this.isAdmin = admin;
			if (admin)
				adminExists = true;
		}
	}

	public long getId() {
		return idPlayer;
	}

	public void setId(long id) {
		this.idPlayer = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		if (adminExists) {
			this.isAdmin = false;
		} else {
			this.isAdmin = isAdmin;
			if (isAdmin)
				adminExists = true;
		}
	}

	public static boolean isAdminExists() {
		return adminExists;
	}

	public static void setAdminExists(boolean adminExists) {
		Player.adminExists = adminExists;
	}

	public Set<Tournament> getTournamentsList() {
		return tournamentsList;
	}

	public void setTournamentsList(Set<Tournament> tournamentsList) {
		this.tournamentsList = tournamentsList;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
}
