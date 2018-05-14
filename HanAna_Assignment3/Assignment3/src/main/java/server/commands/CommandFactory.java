package server.commands;

public class CommandFactory {

    public static Command getCommand(String[] args) {
        try {
            if (args[0].equals("login"))
                return new LoginCommand(args[1], args[2]);

            if (args[0].equals("register"))
                return new RegisterCommand(args[1], args[2], args[3]);

            if (args[0].equals("view") && args[1].equals("articles"))
                return new ViewArticlesCommand();

            if (args[0].equals("validate"))
                return new ValidationCommand(args[1], args[2]);

            if (args[0].equals("check") && args[1].equals("admin"))
                return new CheckAdminCommand(args[2], args[3]);

            if (args[0].equals("view") && args[1].equals("related"))
                return new ViewWrittenArticlesCommand(args[2]);

            if (args[0].equals("submit"))
                return new WriteArticleCommand(args[1], args[2], args[3], args[4], args[5]);

            if (args[0].equals("find") && args[1].equals("article"))
                return new OpenArticleCommand(args[2]);
            return null;
        } catch (ArrayIndexOutOfBoundsException arrE) {
            return null;
        }

    }

}
