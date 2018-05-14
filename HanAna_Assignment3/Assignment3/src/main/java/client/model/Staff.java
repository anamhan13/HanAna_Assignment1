package client.model;

import java.io.Serializable;


/**
 * The persistent class for the staff database table.
 * 
 */

public class Staff implements Serializable {
	private static final long serialVersionUID = 1L;
	public static enum Type {ADMIN,SECRETARY};

	private int id;

	private String password;

	private Type role;

	private String username;

	public Staff() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Type getRole() {
		return this.role;
	}

	public void setRole(Type role) {
		this.role = role;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "Staff [id=" + id + ", password=" + password + ", role=" + role + ", username=" + username + "]";
	}

}