package org.npathai;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CommandFactoryTest {

    private static final String POST_COMMAND = "Alice -> Hi, I am Alice";
    private CommandFactory commandFactory;

    @BeforeEach
    public void initialize() {
        commandFactory = new CommandFactory();
    }

    @Test
    public void returnsPostCommand() {
        Command postCommand = commandFactory.createCommand(POST_COMMAND);

        assertThat(postCommand).isInstanceOf(PostCommand.class);
    }

    @Test
    public void returnsNullWhenThereIsNoMatchingCommand() {
        Command invalidCommand = commandFactory.createCommand("invalid");

        assertThat(invalidCommand).isNull();
    }
}