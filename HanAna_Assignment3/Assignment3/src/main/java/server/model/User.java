package server.model;

public abstract class User {

    private String mail;
    private String name;
    private String password;
    private final Role role;

    public User(Role role) {
        this.role = role;
    }

    public User(String mail, String name, String password, Role role) {
        this.mail = mail;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

}
