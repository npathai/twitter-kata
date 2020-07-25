package org.npathai;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

class FollowCommandTest {

    private static final String FOLLOW_COMMAND = "Bob follows Alice";
    private static final String USER_BOB = "Bob";
    private static final String USER_ALICE = "Alice";

    @Mock
    UserService userService;

    @BeforeEach
    public void initialize() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void storesThatAUserIsFollowingOtherUser() {
        FollowCommand followCommand = new FollowCommand(FOLLOW_COMMAND, userService);
        followCommand.execute();

        verify(userService).addFollowing(USER_BOB, USER_ALICE);
    }

    @Test
    public void doesNotReturnAnyMessages() {
        FollowCommand followCommand = new FollowCommand(FOLLOW_COMMAND, userService);

        assertThat(followCommand.execute()).isEmpty();
    }
}