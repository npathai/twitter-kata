package org.npathai.domain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class User {

    private final String username;
    private LinkedList<Post> posts;
    private ArrayList<User> followedUsers;

    public User(String username) {
        this.username = username;
        this.posts = new LinkedList<>();
        this.followedUsers = new ArrayList<>();
    }

    public void addPost(Post post) {
        posts.addFirst(post);
    }

    public List<Post> posts() {
        return posts;
    }

    public void follows(User followedUser) {
        followedUsers.add(followedUser);
    }

    public List<User> followedUsers() {
        return followedUsers;
    }
}
