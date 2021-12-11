package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.exceptions.InvalidCharacterNumberException;

public class Main {
    public static void main(String[] args) throws InvalidCharacterNumberException, InterruptedException {
        Test test = new Test();
//        test.test_Authentication();
//        test.test_tweeting();
        test.test_timeline();
    }
}
