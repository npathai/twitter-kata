package org.npathai;

public class PostFormatter {

    public String format(Post post) {
        return post.user + " - " + post.message;
    }
}
