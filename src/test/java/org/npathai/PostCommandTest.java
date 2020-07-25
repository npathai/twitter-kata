package org.npathai;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

class PostCommandTest {

    private static final String POST_COMMAND = "Alice -> Hi, I am Alice";
    private static final String USER = "Alice";
    private static final String POST = "Hi, I am Alice";

    @Mock
    UserService userService;

    @BeforeEach
    public void initialize() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void storesPostsAssociatedWithUser() {
        PostCommand postCommand = new PostCommand(POST_COMMAND, userService);
        postCommand.execute();

        verify(userService).save(USER, POST);
    }

    @Test
    public void returnsNoOutput() {
        PostCommand postCommand = new PostCommand(POST_COMMAND, userService);

        assertThat(postCommand.execute()).isEmpty();
    }
}