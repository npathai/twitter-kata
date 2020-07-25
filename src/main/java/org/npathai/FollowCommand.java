package org.npathai;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class FollowCommand implements Command {
    private final String command;
    private final UserService userService;

    public FollowCommand(String command, UserService userService) {
        this.command = Objects.requireNonNull(command);
        this.userService = Objects.requireNonNull(userService);
    }

    @Override
    public List<String> execute() {
        String[] parts = command.split(" follows ");
        String from = parts[0];
        String to = parts[1];
        userService.addFollowing(from, to);

        return Collections.emptyList();
    }
}
