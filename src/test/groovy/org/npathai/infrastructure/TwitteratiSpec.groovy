package org.npathai.infrastructure

import org.mockito.AdditionalAnswers
import org.mockito.InOrder
import org.mockito.Mockito
import org.npathai.CommandExecutor
import org.npathai.CommandFactory
import org.npathai.Console
import org.npathai.Twitterati
import org.npathai.UserService
import spock.lang.Specification

import java.time.Clock

import static org.mockito.Mockito.when

class TwitteratiSpec extends Specification {
    static final long NOW = System.currentTimeMillis()

    Fixture application = new Fixture();

    class Fixture {
        Console mockConsole = Mockito.mock(Console)
        Clock mockClock = Mockito.mock(Clock)

        def allCommands = []
        def allTimes = []

        Twitterati application = new Twitterati(mockConsole, new CommandExecutor(
                new CommandFactory(new UserService(Clock.systemDefaultZone()))))

        def receives(String... commands) {
            for (String command : commands) {
                allCommands << command
            }
        }

        def receives(String command, long receivedTime) {
            allCommands << command
            allTimes << receivedTime
        }

        void start() {
            allCommands << "q"
            when(mockConsole.readLine()).thenAnswer(AdditionalAnswers.returnsElementsOf(allCommands))

            if (allTimes.isEmpty()) {
                allTimes << Clock.systemDefaultZone().millis()
            }
            when(mockClock.millis()).thenAnswer(AdditionalAnswers.returnsElementsOf(allTimes))
            application.start()
        }

        void displays(String... messages) {
            InOrder inOrder = Mockito.inOrder(mockConsole)
            for (String message : messages) {
                inOrder.verify(mockConsole).writeLine(message)
            }
        }
    }
}
