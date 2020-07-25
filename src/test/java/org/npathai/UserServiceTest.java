package org.npathai;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest {

    private static final String NON_EXISTENT_USER = "Unknown";
    private static final String NEW_USER = "Alice";
    private static final String POST = "Hi";
    private static final String USER = "Tim";
    private static final String FIRST_POST = "Hola";
    private static final String SECOND_POST = "Adios";

    private UserService userService;

    @BeforeEach
    public void initialize() {
        userService = new UserService();
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
}