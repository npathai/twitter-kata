package org.npathai;

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
