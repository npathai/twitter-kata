package org.npathai;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CommandExecutorTest {

    private static final String TIMELINE_COMMAND = "Alice";
    private static final Post USER_POST_1 = new Post("Alice", "Hi, I am Alice", System.currentTimeMillis());

    @Mock
    CommandFactory commandFactory;
    private CommandExecutor executor;

    @BeforeEach
    public void initialize() {
        MockitoAnnotations.initMocks(this);
        executor = new CommandExecutor(commandFactory);
    }

    @Test
    public void returnsOutputOfCommandWhenThereIsAMatchingOne() {
        Command mockCommand = Mockito.mock(Command.class);
        when(commandFactory.createCommand(TIMELINE_COMMAND)).thenReturn(mockCommand);
        when(mockCommand.execute()).thenReturn(List.of(USER_POST_1));

        List<Post> output = executor.execute(TIMELINE_COMMAND);

        verify(mockCommand).execute();
        assertThat(output).containsExactly(USER_POST_1);
    }
    
    @Test
    public void returnsNoResponseWhenThereIsNoMatchingCommand() {
        when(commandFactory.createCommand(TIMELINE_COMMAND)).thenReturn(null);

        List<Post> output = executor.execute(TIMELINE_COMMAND);

        assertThat(output).isEmpty();
    }
}