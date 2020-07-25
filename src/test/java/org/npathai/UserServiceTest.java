package org.npathai;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest {

    private static final String NON_EXISTENT_USER = "Unknown";
    private static final String NEW_USER = "Alice";
    private static final String POST = "Hi";
    private static final String USER = "Tim";
    private static final String FIRST_POST = "Alice First Post";
    private static final String SECOND_POST = "Alice Second Post";

    private UserService userService;
    private MutableClock mutableClock;

    @BeforeEach
    public void initialize() {
        mutableClock = new MutableClock();
        userService = new UserService(mutableClock);
    }

    @Test
    public void returnsNoPostsWhenUserDoesNotExist() {
        assertThat(userService.postsBy(NON_EXISTENT_USER)).isEmpty();
    }

    @Test
    public void createsUserAndReturnsPostsWhenNewUserPostsAMessage() {
        userService.save(NEW_USER, POST);

        assertThat(userService.postsBy(NEW_USER))
                .hasValue(List.of(POST));
    }

    @Test
    public void returnsPostsInReverseChronologicalOrder() {
        userService.save(USER, FIRST_POST);
        userService.save(USER, SECOND_POST);

        assertThat(userService.postsBy(USER))
                .contains(List.of(SECOND_POST, FIRST_POST));
    }

    @Test
    public void returnsPostsByUserAndByUsersHeFollowsInReverseChronologicalOrder() {
        userService.save("Alice", FIRST_POST);

        afterDelayOf(Duration.ofSeconds(1));
        userService.save("Bob", "Bob First Post");

        afterDelayOf(Duration.ofSeconds(1));
        userService.save("Alice", SECOND_POST);
        userService.addFollowing("Bob", "Alice");

        assertThat(userService.wall("Bob")).contains(List.of(
                "Alice -> " + SECOND_POST,
                "Bob -> " + "Bob First Post",
                "Alice -> " + FIRST_POST
        ));
    }

    private void afterDelayOf(Duration amount) {
        mutableClock.advanceBy(amount);
    }
}