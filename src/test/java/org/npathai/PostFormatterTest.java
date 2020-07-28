package org.npathai;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PostFormatterTest {

    private final MutableClock mutableClock = new MutableClock();
    private static final Post POST = new Post("Alice", "Hi, I am Alice", LocalDateTime.now());
    private PostFormatter postFormatter;

    @BeforeEach
    public void initialize() {
        postFormatter = new PostFormatter(mutableClock);
    }

    @Test
    public void formatsPostAsUsernameAndMessagedSeparatedByDash() {
        String formattedPost = postFormatter.format(POST);
        assertThat(formattedPost).startsWith(POST.user + " - " + POST.message);
    }

    @Test
    public void showsHowLongAgoThePostWasPosted() {
        LocalDateTime now = LocalDateTime.now(mutableClock);

        assertThat(postFormatter.format(new Post("Alice", "Hello",
                now))).endsWith("(just now)");

        assertThat(postFormatter.format(new Post("Alice", "Hello",
                now.minusSeconds(1)))).endsWith("(1 second ago)");

        assertThat(postFormatter.format(new Post("Alice", "Hello",
                now.minusSeconds(10)))).endsWith("(10 seconds ago)");

        assertThat(postFormatter.format(new Post("Alice", "Hello",
                now.minusMinutes(1)))).endsWith("(1 minute ago)");

        assertThat(postFormatter.format(new Post("Alice", "Hello",
                now.minusMinutes(10)))).endsWith("(10 minutes ago)");

        assertThat(postFormatter.format(new Post("Alice", "Hello",
                now.minusHours(1)))).endsWith("(1 hour ago)");

        assertThat(postFormatter.format(new Post("Alice", "Hello",
                now.minusHours(10)))).endsWith("(10 hours ago)");

        assertThat(postFormatter.format(new Post("Alice", "Hello",
                now.minusDays(1)))).endsWith("(1 day ago)");

        assertThat(postFormatter.format(new Post("Alice", "Hello",
                now.minusDays(10)))).endsWith("(10 days ago)");
    }
}