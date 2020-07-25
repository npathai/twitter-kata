package org.npathai;

import java.util.regex.Pattern;

public class CommandFactory {
    private final UserService userService;
    private Pattern postCommandPattern = Pattern.compile(".*\\s->\\s.*");
    private Pattern followCommandPattern = Pattern.compile(".*\\sfollows\\s.*");
    private Pattern wallCommand = Pattern.compile(".*\\swall");

    public CommandFactory(UserService userService) {
        this.userService = userService;
    }

    public Command createCommand(String commandName) {
        if (postCommandPattern.matcher(commandName).matches()) {
            return new PostCommand(commandName, userService);
        } else if (followCommandPattern.matcher(commandName).matches()) {
            return new FollowCommand(commandName, userService);
        } else if (wallCommand.matcher(commandName).matches()) {
            return new WallCommand(commandName, userService);
        }
        return new ReadCommand(commandName, userService);
    }
}
