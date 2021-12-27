package main.java.org.ce.ap.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

import main.java.org.ce.ap.client.exception.InvalidCommandException;
import main.java.org.ce.ap.client.impl.*;
import main.java.org.ce.ap.ServiceWordsEnum;

public class UserInterface {
    private Socket client;
    private OutputStream out ;
    private InputStream in;
    private  byte[] buffer = new byte[2048];
    private final ConsoleViewServiceImpl consoleViewService;
    private CommandParserServiceImpl commandParserService;
    public UserInterface(Socket client) throws IOException {
        this.client=client;
         this.out = client.getOutputStream();
         this.in = client.getInputStream();
         this.consoleViewService = new ConsoleViewServiceImpl();

    }
    public void run(){
        boolean exit=false;
        while (!exit) {
            consoleViewService.welcome();
            ServiceWordsEnum command = commandParserService.authentication();
            if(command.equals(ServiceWordsEnum.SIGNIN)){

            }

        }
    }

}
