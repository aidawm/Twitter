package main.java.org.ce.ap.server;

import main.java.org.ce.ap.ServiceWordsEnum;

public class UserAccountRunnable implements Runnable{
    private UserAccount userAccount;
    private ServiceWordsEnum key;

    public UserAccountRunnable(UserAccount userAccount, ServiceWordsEnum key) {
        this.userAccount = userAccount;
        this.key = key;
    }

    @Override
    public void run() {
//        switch (key){
//            case TWEET:
//                userAccount.addNewTweet();
//                    REMOVETWEET,
//                    RETWEET,
//                    REMOVERETWEET,
//                    LIKE,
//                    DISLIKE,
//                    REPLY,
//                    REMOVEREPLY
//        }

    }
}
