package server.commands;

public class CheckAdminCommand implements Command {

    private String username;
    private String password;

    public CheckAdminCommand(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Object execute() {
        if (username.equals("anahan@gmail.com") && password.equals("banAna123?"))
            return Boolean.TRUE;
        return Boolean.FALSE;
    }
}
