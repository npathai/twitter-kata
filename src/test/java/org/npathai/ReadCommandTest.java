package org.npathai;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class ReadCommandTest {

    private static final String USER = "Alice";
    private static final String UNKNOWN_USER = "Invalid";
    private static final Post POST = new Post(USER, "Hi, I am Alice", 0L);

    @Mock
    UserService userService;

    @BeforeEach
    public void initialize() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void returnsPostsByUserWhenUserExists() {
        ReadCommand readCommand = new ReadCommand(USER, userService);
        when(userService.postsBy(USER)).thenReturn(Optional.of(
                List.of(POST)));

        assertThat(readCommand.execute()).containsExactly(POST);
    }
    
    @Test
    public void returnsEmptyListWhenUserIsUnknown() {
        ReadCommand readCommand = new ReadCommand(UNKNOWN_USER, userService);
        when(userService.postsBy(UNKNOWN_USER)).thenReturn(Optional.empty());

        assertThat(readCommand.execute()).isEmpty();
    }
}