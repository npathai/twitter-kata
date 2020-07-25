package org.npathai;

import java.time.Clock;
import java.util.*;
import java.util.stream.Collectors;

public class UserService {
    private final Clock clock;
    private Map<String, LinkedList<Post>> postsByUser = new HashMap<>();
    private Map<String, String> followingByUser = new HashMap<>();

    public UserService(Clock clock) {
        this.clock = clock;
    }

    public void save(String user, String post) {
        postsByUser.computeIfAbsent(user, u -> new LinkedList<>());
        postsByUser.get(user).addFirst(new Post(user, post, clock.millis()));
    }

    public Optional<List<String>> postsBy(String user) {
        LinkedList<Post> userPosts = postsByUser.get(user);
        if (userPosts == null) {
            return Optional.empty();
        }

        return Optional.of(userPosts
                .stream().map(post -> post.message)
                .collect(Collectors.toList()));
    }

    public void addFollowing(String from, String to) {
        followingByUser.put(from, to);
    }

    public Optional<List<String>> wall(String user) {
        LinkedList<Post> userPosts = postsByUser.get(user);
        if (userPosts == null) {
            return Optional.of(Collections.emptyList());
        }

        ArrayList<Post> wallPosts = new ArrayList<>();
        wallPosts.addAll(userPosts);

        String followedUser = followingByUser.get(user);
        if (followedUser != null) {
            wallPosts.addAll(postsByUser.get(followedUser));
        }

        List<String> sortedPosts = wallPosts.stream()
                .sorted(Comparator.reverseOrder())
                .map(post -> post.user + " -> " + post.message)
                .collect(Collectors.toList());

        return Optional.of(sortedPosts);
    }

    class Post implements Comparable<Post> {
        String user;
        String message;
        long createdAt;

        public Post(String user, String message, long createdAt) {
            this.user = user;
            this.message = message;
            this.createdAt = createdAt;
        }

        @Override
        public int compareTo(Post post) {
            return Long.compare(createdAt, post.createdAt);
        }
    }
}
