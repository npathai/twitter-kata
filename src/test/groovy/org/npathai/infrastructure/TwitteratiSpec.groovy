package org.npathai.infrastructure

import org.mockito.AdditionalAnswers
import org.mockito.InOrder
import org.mockito.Mockito
import org.npathai.*
import org.npathai.command.CommandExecutor
import org.npathai.command.CommandFactory
import org.npathai.view.Console
import org.npathai.command.PostFormatter
import org.npathai.service.UserService
import org.npathai.view.View
import spock.lang.Specification

import java.time.Instant
import java.time.ZonedDateTime

import static org.mockito.Mockito.spy
import static org.mockito.Mockito.when

class TwitteratiSpec extends Specification {
    Fixture application = new Fixture();

    class Fixture {
        Console mockConsole = Mockito.mock(Console)
        MutableClock mockClock = spy(new MutableClock())
        ZonedDateTime now = ZonedDateTime.now(mockClock)

        View view = new View(mockConsole, new PostFormatter(mockClock))

        def allCommands = []
        List<Instant> allTimes = []

        Twitterati application = new Twitterati(view, mockConsole, new CommandExecutor(
                new CommandFactory(new UserService(mockClock))))

        def receives(String... commands) {
            for (String command : commands) {
                allCommands << command
            }
        }

        def receives(String command, ZonedDateTime receivedTime) {
            allCommands << command
            allTimes << Instant.from(receivedTime)
        }

        void start() {
            allCommands << "q"
            when(mockConsole.readLine()).thenAnswer(AdditionalAnswers.returnsElementsOf(allCommands))

            if (!allTimes.isEmpty()) {
                when(mockClock.instant()).thenAnswer(AdditionalAnswers.returnsElementsOf(allTimes))
            }
            application.start()
        }

        void displays(String... messages) {
            InOrder inOrder = Mockito.inOrder(mockConsole)
            for (String message : messages) {
                inOrder.verify(mockConsole).writeLine(message)
            }
        }

        ZonedDateTime now() {
            now
        }
    }
}
