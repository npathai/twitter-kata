package org.npathai.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Post implements Comparable<Post> {
    private final String user;
    private final String message;
    private final LocalDateTime createdAt;

    public Post(String user, String message, LocalDateTime createdAt) {
        this.user = user;
        this.message = message;
        this.createdAt = createdAt;
    }

    public String message() {
        return message;
    }

    public String user() {
        return user;
    }

    public LocalDateTime createdAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(createdAt, post.createdAt) &&
                Objects.equals(user, post.user) &&
                Objects.equals(message, post.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, message, createdAt);
    }

    @Override
    public int compareTo(Post post) {
        return createdAt.compareTo(post.createdAt);
    }

    @Override
    public String toString() {
        return "Post{" +
                "user='" + user + '\'' +
                ", message='" + message + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
