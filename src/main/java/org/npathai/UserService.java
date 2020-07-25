package org.npathai;

import java.util.*;

public class UserService {
    private Map<String, LinkedList<String>> postsByUser = new HashMap<>();

    public void save(String user, String post) {
        postsByUser.computeIfAbsent(user, u -> new LinkedList<>());
        postsByUser.get(user).addFirst(post);
    }

    public Optional<List<String>> postsBy(String user) {
        return Optional.ofNullable(postsByUser.get(user));
    }
}
