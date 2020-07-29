package org.npathai.command;

import org.npathai.domain.Post;

import java.util.List;

public interface Command {
    List<Post> execute();
}
