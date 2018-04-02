package model;

public class Player {

	private static int noPlayers;
	private int idPlayer;
	private String name;
	private String mail;
	private String password;
	private String address;
	private boolean newPlayer;
	private boolean isAdmin;
	private static boolean adminExists;

	public Player(boolean admin) {

		if (adminExists) {
			this.isAdmin = false;
		} else {
			this.isAdmin = admin;
			if (admin)
				adminExists = true;
		}
	}

	public Player(String mail, String password, boolean admin) {

		this.mail = mail;
		this.password = password;

		if (adminExists) {
			this.isAdmin = false;
		} else {
			this.isAdmin = admin;
			if (admin)
				adminExists = true;
		}
	}

	public Player(String name, String mail, String password, String address, boolean admin) {

		this.mail = mail;
		this.password = password;
		this.address = address;
		this.name = name;

		if (adminExists) {
			this.isAdmin = false;
		} else {
			this.isAdmin = admin;
			if (admin)
				adminExists = true;
		}
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getIdPlayer() {
		return idPlayer;
	}

	public void setIdPlayer(int idPlayer) {
		this.idPlayer = idPlayer;
	}

	public static int getNoPlayers() {
		return noPlayers;
	}

	public static void setNoPlayers(int noPlayers) {
		Player.noPlayers = noPlayers;
	}

	public boolean isNewPlayer() {
		return newPlayer;
	}

	public void setNewPlayer(boolean newPlayer) {
		this.newPlayer = newPlayer;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		if (adminExists) {
			System.out.println("Admin already exists");
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

}
