package org.npathai.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.npathai.MutableClock;
import org.npathai.service.UserService;

import java.time.Duration;
import java.time.LocalDateTime;
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
                .hasValue(List.of(new Post(USER_ALICE, POST, LocalDateTime.now(mutableClock))));
    }

    @Test
    public void returnsPostsInReverseChronologicalOrder() {
        userService.save(USER_TIM, ALICE_FIRST_POST);
        userService.save(USER_TIM, ALICE_SECOND_POST);

        assertThat(userService.postsBy(USER_TIM))
                .contains(List.of(new Post(USER_TIM, ALICE_SECOND_POST, LocalDateTime.now(mutableClock)),
                        new Post(USER_TIM, ALICE_FIRST_POST, LocalDateTime.now(mutableClock))));
    }

    @Test
    public void returnsPostsByUserAndByUsersHeFollowsInReverseChronologicalOrder() {
        LocalDateTime alicePost1CreationTime = LocalDateTime.now(mutableClock);
        userService.save("Alice", ALICE_FIRST_POST);

        LocalDateTime bobPostCreationTime = afterDelayOf(Duration.ofSeconds(1));
        userService.save("Bob", "Bob First Post");

        LocalDateTime alicePost2CreationTime = afterDelayOf(Duration.ofSeconds(1));
        userService.save("Alice", ALICE_SECOND_POST);
        userService.addFollowing("Bob", "Alice");

        assertThat(userService.wall("Bob")).contains(List.of(
                new Post("Alice", ALICE_SECOND_POST, alicePost2CreationTime),
                new Post("Bob", "Bob First Post", bobPostCreationTime),
                new Post("Alice", ALICE_FIRST_POST, alicePost1CreationTime)
        ));
    }

    private LocalDateTime afterDelayOf(Duration amount) {
        mutableClock.advanceBy(amount);
        return LocalDateTime.now(mutableClock);
    }
}