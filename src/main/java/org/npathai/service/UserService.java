package org.npathai.service;

import org.npathai.domain.User;
import org.npathai.domain.Post;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class UserService {
    private final Map<String, User> userByName = new HashMap<>();
    private final Clock clock;

    public UserService(Clock clock) {
        this.clock = clock;
    }

    public void save(String username, String post) {
        User user = userBy(username);
        user.addPost(new Post(username, post, LocalDateTime.now(clock)));
    }

    private User userBy(String username) {
        return getUserBy(username)
                .orElseGet(() -> createUser(username));
    }

    private User createUser(String username) {
        User user = new User(username);
        userByName.put(username, user);
        return user;
    }

    private Optional<User> getUserBy(String username) {
        return Optional.ofNullable(userByName.get(username));
    }

    public Optional<List<Post>> postsBy(String username) {
        Optional<User> optionalUser = getUserBy(username);
        if (optionalUser.isEmpty()) {
            return Optional.empty();
        }

        List<Post> userPosts = optionalUser.get().posts();
        return Optional.of(new ArrayList<>(userPosts));
    }

    public void addFollowing(String fromUsername, String toUsername) {
        User fromUser = userBy(fromUsername);
        fromUser.follows(userBy(toUsername));
    }

    public Optional<List<Post>> wall(String username) {
        Optional<User> optionalUser = getUserBy(username);
        if (optionalUser.isEmpty()) {
            return Optional.of(Collections.emptyList());
        }

        List<Post> userPosts = optionalUser.get().posts();
        ArrayList<Post> wallPosts = new ArrayList<>(userPosts);

        optionalUser.get().followedUsers()
                .forEach(followedUser -> wallPosts.addAll(followedUser.posts()));

        List<Post> sortedPosts = wallPosts.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        return Optional.of(sortedPosts);
    }

}
