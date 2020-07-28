package org.npathai;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class PostCommand implements Command {

    private final String command;
    private final UserService userService;

    public PostCommand(String command, UserService userService) {
        this.command = Objects.requireNonNull(command);
        this.userService = Objects.requireNonNull(userService);
    }

    @Override
    public List<Post> execute() {
        String[] parts = command.split(" -> ");
        String user = parts[0];
        String post = parts[1];
        userService.save(user, post);
        return Collections.emptyList();
    }
}
