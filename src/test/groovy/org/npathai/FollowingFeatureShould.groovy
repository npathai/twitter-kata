package org.npathai

import org.npathai.infrastructure.TwitteratiSpec

class FollowingFeatureShould extends TwitteratiSpec {

    def "In order to see what my friends are up to, as a user, I would like to follow them and see their msgs on wall"() {
        given: "Bob follows Alice"
        application.receives("Alice -> Hi, I am Alice", application.now().minusSeconds(2))
        application.receives("Bob -> Hola, I am Bob", application.now().minusSeconds(1))
        application.receives("Alice -> It's a sunny day today!", application.now())

        application.receives("Bob follows Alice")

        when: "Bob checks his wall"
        application.receives("Bob wall", application.now())
        application.start()

        then: "Bob sees Alice's and his messages in reverse chronological order"
        application.displays("Alice - It's a sunny day today! (just now)", "Bob - Hola, I am Bob (1 second ago)",
                "Alice - Hi, I am Alice (2 seconds ago)")
    }

    def "In order to see what my friends are up to, as a user, I would like to follow multiple friends and se their msgs on wall"() {
        given: "Bob follows Alice and Tim"
        application.receives("Alice -> Hi, I am Alice", application.now().minusSeconds(4))
        application.receives("Bob -> Hola, I am Bob", application.now().minusSeconds(2))
        application.receives("Tim -> Namaste, I am Tim", application.now().minusSeconds(1))
        application.receives("Alice -> It's a sunny day today!", application.now())

        application.receives("Bob follows Alice")
        application.receives("Bob follows Tim")

        when: "Bob checks his wall"
        application.receives("Bob wall", application.now())
        application.start()

        then: "Bob sees Alice's, Tim's and his messages in reverse chronological order"
        application.displays(
                "Alice - It's a sunny day today! (just now)",
                "Tim - Namaste, I am Tim (1 second ago)",
                "Bob - Hola, I am Bob (2 seconds ago)",
                "Alice - Hi, I am Alice (4 seconds ago)")
    }
}
