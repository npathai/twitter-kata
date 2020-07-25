package org.npathai;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class WallCommand implements Command {

    private final String command;
    private final UserService userService;

    public WallCommand(String command, UserService userService) {
        this.command = Objects.requireNonNull(command);
        this.userService = Objects.requireNonNull(userService);
    }

    @Override
    public List<String> execute() {
        String[] parts = command.split(" wall");
        String user = parts[0];
        return userService.wall(user)
                .orElse(Collections.emptyList());
    }
}
