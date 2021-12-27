package main.java.org.ce.ap.client.impl;
import  main.java.org.ce.ap.client.*;
import org.json.JSONObject;

public class ConsoleViewServiceImpl implements ConsoleViewService{
    @Override
    public void welcome() {
        System.out.println("Hi :) welcome to our Twitter ");
        System.out.println("1 ) sign up");
        System.out.println("2 ) sign in");
        System.out.println("0 ) exit");
    }
    public void mainMenu(){
        System.out.println("1 ) manage tweets ");
        System.out.println("2 ) manage followers & followings ");
        System.out.println("0 ) exit");
    }
    public void manageTweetsMenu(){
        System.out.println("1 ) new tweet");
        System.out.println("2 ) remove a tweet");
        System.out.println("3 ) new retweet");
        System.out.println("4 ) new reply");
        System.out.println("5 ) remove a reply");
        System.out.println("6 ) like a tweet");
        System.out.println("7 ) unlike a tweet");
        System.out.println("8 ) show timeline");
        System.out.println("0 ) exit");
    }
    public void processServerResponse(JSONObject response){

    }
}
