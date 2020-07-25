package org.npathai;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

class CommandFactoryTest {

    private static final String POST_COMMAND = "Alice -> Hi, I am Alice";
    private static final String FOLLOW_COMMAND = "Bob follows Alice";
    private static final String WALL_COMMAND = "Bob wall";

    private CommandFactory commandFactory;

    @Mock
    UserService userService;

    @BeforeEach
    public void initialize() {
        MockitoAnnotations.initMocks(this);
        commandFactory = new CommandFactory(userService);
    }

    @Test
    public void returnsPostCommand() {
        Command postCommand = commandFactory.createCommand(POST_COMMAND);

        assertThat(postCommand).isInstanceOf(PostCommand.class);
    }

    @Test
    public void returnsFollowCommand() {
        Command followCommand = commandFactory.createCommand(FOLLOW_COMMAND);

        assertThat(followCommand).isInstanceOf(FollowCommand.class);
    }

    @Test
    public void returnsWallCommand() {
        Command wallCommand = commandFactory.createCommand(WALL_COMMAND);

        assertThat(wallCommand).isInstanceOf(WallCommand.class);
    }

    @Test
    public void returnsReadCommandWhenThereIsNoMatchingCommand() {
        Command readCommand = commandFactory.createCommand("non-matching-command");

        assertThat(readCommand).isInstanceOf(ReadCommand.class);
    }
}