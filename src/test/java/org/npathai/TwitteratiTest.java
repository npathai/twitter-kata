package org.npathai;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.mockito.Mockito.*;

class TwitteratiTest {

    private static final String USER_POST_1 = "Alice -> Hello, I am Alice";
    private static final String QUIT = "q";
    private static final String TIMELINE_COMMAND = "Alice";

    @Mock
    View mockView;

    @Mock
    Console mockConsole;

    @Mock
    CommandExecutor commandExecutor;

    Twitterati twitterati;

    @BeforeEach
    public void initialize() {
        MockitoAnnotations.initMocks(this);

        twitterati = new Twitterati(mockView, mockConsole, commandExecutor);
    }

    @Test
    public void shouldReadFromConsoleTillQuitCommand() {
        when(mockConsole.readLine()).thenReturn(USER_POST_1, QUIT);

        twitterati.start();

        verify(mockConsole, times(2)).readLine();
    }

    @Test
    public void executesTheCommands() {
        when(mockConsole.readLine()).thenReturn(USER_POST_1, QUIT);

        twitterati.start();

        verify(commandExecutor).execute(USER_POST_1);
    }

    @Test
    public void writesOutputOfCommandOnConsole() {
        Post post = new Post("Alice", "Hi, I am Alice", System.currentTimeMillis());
        when(mockConsole.readLine()).thenReturn(TIMELINE_COMMAND, QUIT);

        when(commandExecutor.execute(TIMELINE_COMMAND)).thenReturn(Arrays.asList(post));
        twitterati.start();

        verify(mockView).display(post);
    }
}