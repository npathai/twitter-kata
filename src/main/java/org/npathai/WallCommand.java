package org.npathai;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
                .map(wallPosts -> wallPosts.stream()
                        .map(post -> post.user + " -> " + post.message)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }
}
