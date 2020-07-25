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

import static org.mockito.Mockito.when

class TwitteratiSpec extends Specification {

    Fixture application = new Fixture();

    class Fixture {
        Console mockConsole = Mockito.mock(Console)

        def allCommands = [] as List
        Twitterati application = new Twitterati(mockConsole, new CommandExecutor(new CommandFactory( new UserService())))

        def receives(String... commands) {
            for (String command : commands) {
                allCommands << command
            }
        }

        void start() {
            allCommands << "q"
            when(mockConsole.readLine()).thenAnswer(AdditionalAnswers.returnsElementsOf(allCommands))
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
