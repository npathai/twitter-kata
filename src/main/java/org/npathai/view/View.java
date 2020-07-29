package org.npathai.view;

import org.npathai.command.PostFormatter;
import org.npathai.domain.Post;

public class View {

    private final Console console;
    private final PostFormatter postFormatter;

    public View(Console console, PostFormatter postFormatter) {
        this.console = console;
        this.postFormatter = postFormatter;
    }

    public void display(Post post) {
        console.writeLine(postFormatter.format(post));
    }
}
