package org.npathai;

import org.npathai.command.CommandExecutor;
import org.npathai.command.CommandFactory;
import org.npathai.view.Console;
import org.npathai.command.PostFormatter;
import org.npathai.service.UserService;
import org.npathai.view.View;

import java.time.Clock;

public class TwitteratiMain {

    public static void main(String[] args) {
        Console console = new Console();
        Twitterati twitterati = new Twitterati(new View(console, new PostFormatter(Clock.systemDefaultZone())), console,
                new CommandExecutor(new CommandFactory(new UserService(Clock.systemDefaultZone()))));

        twitterati.start();
    }
}
