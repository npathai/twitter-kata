package org.npathai;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ReadCommand implements Command {

    private final String command;
    private final UserService userService;

    public ReadCommand(String command, UserService userService) {
        this.command = Objects.requireNonNull(command);
        this.userService = Objects.requireNonNull(userService);
    }

    @Override
    public List<String> execute() {
        return userService.postsBy(command)
                .orElse(Collections.emptyList());
    }
}
