package org.npathai;

import java.time.Clock;

public class TwitteratiMain {

    public static void main(String[] args) {
        Twitterati twitterati = new Twitterati(new Console(),
                new CommandExecutor(new CommandFactory(new UserService(Clock.systemDefaultZone()))));

        twitterati.start();
    }
}
