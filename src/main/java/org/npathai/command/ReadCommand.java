package org.npathai.command;

import org.npathai.domain.Post;
import org.npathai.service.UserService;

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
    public List<Post> execute() {
        return userService.postsBy(command)
//                .map(posts -> posts.stream().map(post -> post.message()).collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }
}
