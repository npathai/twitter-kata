package org.npathai;

import java.time.Clock;

public class TwitteratiMain {

    public static void main(String[] args) {
        Console console = new Console();
        Twitterati twitterati = new Twitterati(new View(console, new PostFormatter()), console,
                new CommandExecutor(new CommandFactory(new UserService(Clock.systemDefaultZone()))));

        twitterati.start();
    }
}
