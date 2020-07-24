package org.npathai;

import java.util.List;

public class Twitterati {

    private final Console console;
    private final CommandExecutor commandExecutor;

    public Twitterati(Console console, CommandExecutor commandExecutor) {
        this.console = console;
        this.commandExecutor = commandExecutor;
    }

    public void start() {
        String command;
        while (!(command = console.readLine()).equals("q")) {
            List<String> output = commandExecutor.execute(command);
            for (String outputLine : output) {
                console.writeLine(outputLine);
            }
        }
    }
}
