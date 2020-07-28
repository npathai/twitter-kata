package org.npathai;

import java.util.List;

public class Twitterati {

    private final View view;
    private final Console console;
    private final CommandExecutor commandExecutor;

    public Twitterati(View view, Console console, CommandExecutor commandExecutor) {
        this.view = view;
        this.console = console;
        this.commandExecutor = commandExecutor;
    }

    public void start() {
        String command;
        while (!(command = console.readLine()).equals("q")) {
            List<Post> posts = commandExecutor.execute(command);
            for (Post post : posts) {
                view.display(post);
            }
        }
    }
}
