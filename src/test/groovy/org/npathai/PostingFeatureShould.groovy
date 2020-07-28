package org.npathai


import org.npathai.infrastructure.TwitteratiSpec

class PostingFeatureShould extends TwitteratiSpec {

    def "As a user, to share my ideas, I would like to post messages and make them available for others to read"() {
        given: "Alice posts messages"
        def alicePost1 = "Hello, my name is Alice"
        def alicePost2 = "It's a lovely day today"

        def alicePostCommand1 = "Alice -> ${alicePost1}"
        def alicePostCommand2 = "Alice -> ${alicePost2}"
        application.receives(alicePostCommand1, alicePostCommand2)

        when: "Bob visits Alice's timeline"
        def aliceTimelineCommand = "Alice"
        application.receives(aliceTimelineCommand)
        application.start()

        then: "Alice's messages are displayed in reverse-chronological order"
        application.displays("Alice - ${alicePost2}", "Alice - ${alicePost1}")
    }

}
