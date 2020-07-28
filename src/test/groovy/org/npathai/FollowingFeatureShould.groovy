package org.npathai;

import org.npathai.infrastructure.TwitteratiSpec

import java.time.Duration;

class FollowingFeatureShould extends TwitteratiSpec {

    def "In order to see what my friends are up to, as a user, I would like to follow them and see their msgs on wall"() {
        given: "Bob follows Alice"
        application.receives("Alice -> Hi, I am Alice", NOW)
        application.receives("Bob -> Hola, I am Bob", NOW + Duration.ofSeconds(1).toMillis())
        application.receives("Alice -> It's a sunny day today!", NOW + Duration.ofSeconds(2).toMillis())

        application.receives("Bob follows Alice")

        when: "Bob checks his wall"
        application.receives("Bob wall", NOW)
        application.start()

        then: "Bob sees Alice's and his messages in reverse chronological order"
        application.displays("Alice - It's a sunny day today!", "Bob - Hola, I am Bob", "Alice - Hi, I am Alice")

    }
}
