package org.npathai;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest {

    private static final String NON_EXISTENT_USERNAME = "Unknown";
    private static final String USERNAME_ALICE = "Alice";
    private static final String USER_ALICE = "Alice";
    private static final String POST = "Hi";
    private static final String USER_TIM = "Tim";
    private static final String ALICE_FIRST_POST = "Alice First Post";
    private static final String ALICE_SECOND_POST = "Alice Second Post";

    private UserService userService;
    private MutableClock mutableClock;

    @BeforeEach
    public void initialize() {
        mutableClock = new MutableClock();
        userService = new UserService(mutableClock);
    }

    @Test
    public void returnsNoPostsWhenUserDoesNotExist() {
        assertThat(userService.postsBy(NON_EXISTENT_USERNAME)).isEmpty();
    }

    @Test
    public void createsUserAndReturnsPostsWhenNewUserPostsAMessage() {
        userService.save(USER_ALICE, POST);

        assertThat(userService.postsBy(USER_ALICE))
                .hasValue(List.of(new Post(USER_ALICE, POST, mutableClock.millis())));
    }

    @Test
    public void returnsPostsInReverseChronologicalOrder() {
        userService.save(USER_TIM, ALICE_FIRST_POST);
        userService.save(USER_TIM, ALICE_SECOND_POST);

        assertThat(userService.postsBy(USER_TIM))
                .contains(List.of(new Post(USER_ALICE, ALICE_SECOND_POST, mutableClock.millis()),
                        new Post(USER_ALICE, ALICE_FIRST_POST, mutableClock.millis())));
    }

    @Test
    public void returnsPostsByUserAndByUsersHeFollowsInReverseChronologicalOrder() {
        userService.save("Alice", ALICE_FIRST_POST);

        afterDelayOf(Duration.ofSeconds(1));
        userService.save("Bob", "Bob First Post");

        afterDelayOf(Duration.ofSeconds(1));
        userService.save("Alice", ALICE_SECOND_POST);
        userService.addFollowing("Bob", "Alice");

        assertThat(userService.wall("Bob")).contains(List.of(
                "Alice -> " + ALICE_SECOND_POST,
                "Bob -> " + "Bob First Post",
                "Alice -> " + ALICE_FIRST_POST
        ));
    }

    private void afterDelayOf(Duration amount) {
        mutableClock.advanceBy(amount);
    }
}