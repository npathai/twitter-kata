package org.npathai

import org.mockito.AdditionalAnswers
import org.mockito.InOrder
import org.mockito.Mockito
import spock.lang.Specification

import static org.mockito.Mockito.when

class PostingFeatureShould extends Specification {

    Fixture applicationFixture = new Fixture();

    def "As a user, to share my ideas, I would like to post messages and make them available for others to read"() {
        given: "Alice posts messages"
        def alicePost1 = "Hello, my name is Alice"
        def alicePost2 = "It's a lovely day today"

        def alicePostCommand1 = "Alice -> ${alicePost1}"
        def alicePostCommand2 = "Alice -> ${alicePost2}"
        applicationFixture.receives(alicePostCommand1, alicePostCommand2)

        when: "Bob visits Alice's timeline"
        def aliceTimelineCommand = "Alice"
        applicationFixture.receives(aliceTimelineCommand)
        applicationFixture.start()

        then: "Alice's messages are displayed in reverse-chronological order"
        applicationFixture.displays(alicePost2, alicePost1)
    }

    class Fixture {
        Console mockConsole = Mockito.mock(Console)

        def allCommands = [] as List
        Twitterati application = new Twitterati(mockConsole, new CommandExecutor(new CommandFactory( new UserService())))

        void receives(String... commands) {
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
