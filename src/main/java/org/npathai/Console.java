package org.npathai;

public class Console {

    public String readLine() {
        return null;
    }

    public void writeLine(String line) {
        System.out.println(line);
    }

    public void display(Post post) {
        writeLine(post.user + " - " + post.message);
    }
}
