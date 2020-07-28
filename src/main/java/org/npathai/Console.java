package org.npathai;

import java.util.Scanner;

public class Console {
    private Scanner scanner = new Scanner(System.in);

    public String readLine() {
        System.out.print("> ");
        return scanner.nextLine();
    }

    public void writeLine(String line) {
        System.out.println(line);
    }

}
