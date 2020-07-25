package org.npathai;

import org.npathai.infrastructure.TwitteratiSpec;

class FollowingFeatureShould extends TwitteratiSpec {
    static final String FIRST_POST_BY_ALICE = "Alice -> Hi, I am Alice"
    static final String FIRST_POST_BY_BOB = "Bob -> Hola, I am Bob"
    static final String SECOND_POST_BY_ALICE = "Alice -> It's a sunny day today!"
    static final String BOB_FOLLOWS_ALICE_COMMAND = "Bob follows Alice"
    static final String BOB_WALL_COMMAND = "Bob wall"

    def "In order to see what my friends are up to, as a user, I would like to follow them and see their msgs on wall"() {
        given: "Bob follows Alice"
        application.receives(FIRST_POST_BY_ALICE, FIRST_POST_BY_BOB, SECOND_POST_BY_ALICE)
        application.receives(BOB_FOLLOWS_ALICE_COMMAND)

        when: "Bob checks his wall"
        application.receives(BOB_WALL_COMMAND)

        then: "Bob sees Alice's and his messages in reverse chronological order"
        application.displays(SECOND_POST_BY_ALICE, FIRST_POST_BY_BOB, FIRST_POST_BY_ALICE)

    }
}
