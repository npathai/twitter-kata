package org.npathai;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

class WallCommandTest {

    private static final String WALL_COMMAND = "Bob wall";
    private static final String POST = "A Post";
    public static final String USER = "Bob";
    public static final String UNKNOWN_USER = "Invalid";

    @Mock
    UserService userService;

    @BeforeEach
    public void initialize() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void returnsListOfPostsWhenUserExists() {
        WallCommand wallCommand = new WallCommand(WALL_COMMAND, userService);
        given(userService.wall(USER)).willReturn(Optional.of(List.of(POST)));

        assertThat(wallCommand.execute()).isEqualTo(List.of(POST));
    }

    @Test
    public void returnEmptyListWhenUserDoesNotExist() {
        WallCommand wallCommand = new WallCommand(WALL_COMMAND, userService);
        given(userService.wall(UNKNOWN_USER)).willReturn(Optional.empty());

        assertThat(wallCommand.execute()).isEmpty();
    }
}