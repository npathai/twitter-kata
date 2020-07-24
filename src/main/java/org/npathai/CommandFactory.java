package org.npathai;

import java.util.regex.Pattern;

public class CommandFactory {
    private Pattern postCommandPattern = Pattern.compile(".*\\s->\\s.*");

    public Command createCommand(String commandName) {
        if (postCommandPattern.matcher(commandName).matches()) {
            return new PostCommand();
        }
        return null;
    }
}
