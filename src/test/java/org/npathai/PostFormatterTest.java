package org.npathai;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PostFormatterTest {

    private static final Post POST = new Post("Alice", "Hi, I am Alice", System.currentTimeMillis());

    @Test
    public void formatsPostAsUsernameAndMessagedSeparatedByDash() {
        PostFormatter postFormatter = new PostFormatter();
        String formattedPost = postFormatter.format(POST);
        assertThat(formattedPost).isEqualTo(POST.user + " - " + POST.message);
    }
}