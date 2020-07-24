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
    Console mockConsole;

    @Mock
    CommandExecutor commandExecutor;

    Twitterati twitterati;

    @BeforeEach
    public void initialize() {
        MockitoAnnotations.initMocks(this);

        twitterati = new Twitterati(mockConsole, commandExecutor);
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
        when(mockConsole.readLine()).thenReturn(TIMELINE_COMMAND, QUIT);
        when(commandExecutor.execute(TIMELINE_COMMAND)).thenReturn(Arrays.asList(USER_POST_1));

        twitterati.start();

        verify(mockConsole).writeLine(USER_POST_1);
    }
}