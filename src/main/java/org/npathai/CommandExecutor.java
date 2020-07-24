package org.npathai;

import java.util.Collections;
import java.util.List;

public class CommandExecutor {
    private final CommandFactory commandFactory;

    public CommandExecutor(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    public List<String> execute(String commandName) {
        Command command = commandFactory.createCommand(commandName);
        if (command == null) {
            return Collections.emptyList();
        }
        return command.execute();
    }
}
