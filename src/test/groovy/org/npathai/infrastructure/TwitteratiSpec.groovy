package org.npathai.infrastructure

import org.mockito.AdditionalAnswers
import org.mockito.InOrder
import org.mockito.Mockito
import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.Answer
import org.npathai.*
import spock.lang.Specification

import java.time.Clock

import static org.mockito.Matchers.any
import static org.mockito.Mockito.doCallRealMethod
import static org.mockito.Mockito.doNothing
import static org.mockito.Mockito.when

class TwitteratiSpec extends Specification {
    static final long NOW = System.currentTimeMillis()

    Fixture application = new Fixture();

    class Fixture {
        Console mockConsole = Mockito.spy(Console)
        Clock mockClock = Mockito.mock(Clock)

        def allCommands = []
        def allTimes = []

        Twitterati application = new Twitterati(mockConsole, new CommandExecutor(
                new CommandFactory(new UserService(mockClock))))

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
            doCallRealMethod().when(mockConsole).display(any(Post.class))

            allCommands << "q"
            when(mockConsole.readLine()).thenAnswer(AdditionalAnswers.returnsElementsOf(allCommands))

            if (allTimes.isEmpty()) {
                when(mockClock.millis()).thenAnswer(new Answer<Long>() {
                    @Override
                    Long answer(InvocationOnMock invocation) throws Throwable {
                        return System.currentTimeMillis()
                    }
                })
            } else {
                when(mockClock.millis()).thenAnswer(AdditionalAnswers.returnsElementsOf(allTimes))
            }
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
