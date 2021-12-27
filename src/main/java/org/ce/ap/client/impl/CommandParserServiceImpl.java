package main.java.org.ce.ap.client.impl;

import main.java.org.ce.ap.client.*;
import main.java.org.ce.ap.*;
import main.java.org.ce.ap.client.exception.*;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class CommandParserServiceImpl implements CommandParserService {
    private final ConsoleViewServiceImpl consoleViewService;
    private ConnectionServiceImpl connectionService;

    public CommandParserServiceImpl(Socket client) throws IOException {
        this.consoleViewService = new ConsoleViewServiceImpl();
        this.connectionService = new ConnectionServiceImpl(client);
    }

    public void run() throws IOException {
        boolean exit = false;
        while (!exit) {
            consoleViewService.welcome();
            ServiceWordsEnum command = authentication();
            if (command.equals(ServiceWordsEnum.SIGNIN)) {
                signIn();
            } else if (command.equals(ServiceWordsEnum.SIGNUP)) {
                signUp();
            } else
                break;
            mainMenu();
        }
    }


    public ServiceWordsEnum authentication() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String command = scanner.next();
            int index;
            try {
                index = Integer.parseInt(command);
            } catch (Exception e) {
                System.out.println("please enter a index!");
                continue;
            }
            if (index == 1)
                return ServiceWordsEnum.SIGNUP;
            if (index == 2)
                return ServiceWordsEnum.SIGNIN;
            if (index == 0)
                return ServiceWordsEnum.EXIT;
            else
                System.out.println("please enter a valid index");
        }
    }

    public void signIn() throws IOException {
        String username, password;
        boolean isValid = false;
        while (isValid) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("pls enter your username : ");
            username = scanner.nextLine();
            System.out.println();
            System.out.print("pls enter your password : ");
            password = scanner.nextLine();
            System.out.println();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", username);
            jsonObject.put("password", password);

            JSONObject request = new JSONObject();
            request.put("method", ServiceWordsEnum.SIGNIN);
            request.put("parameterValues", jsonObject);

            JSONObject response = connectionService.request(request);
            consoleViewService.processServerResponse(response);
            if (response.getString("hasError").equals("false"))
                isValid = true;
        }
    }

    public void signUp() throws IOException {
        String username, password, firstName, lastName, birthDateStr;
        LocalDate birthDate;
        boolean isValid = false;
        while (isValid) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("pls enter your firstname : ");
            firstName = scanner.nextLine();
            System.out.println("pls enter your lastname : ");
            lastName = scanner.nextLine();
            System.out.println("pls enter your username : ");
            username = scanner.nextLine();
            System.out.println("pls enter your password : ");
            password = scanner.nextLine();
            System.out.println("pls enter your birthdate(dd/MM/yyyy) : ");
            birthDateStr = scanner.nextLine();
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                birthDate = LocalDate.parse(birthDateStr, formatter);
            } catch (Exception e) {
                System.out.println("please enter your birthdate in valid format (dd/MM/yyyy)");
                continue;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("firstName", firstName);
            jsonObject.put("lastName", lastName);
            jsonObject.put("username", username);
            jsonObject.put("password", password);
            jsonObject.put("birthDate", birthDate);

            JSONObject request = new JSONObject();
            request.put("method", ServiceWordsEnum.SIGNUP);
            request.put("parameterValues", jsonObject);
            JSONObject response = connectionService.request(request);
            consoleViewService.processServerResponse(response);
            if (response.getString("hasError").equals("false"))
                isValid = true;
        }
    }

    private void mainMenu() throws IOException {
        boolean isExit = false;
        while (!isExit) {
            consoleViewService.mainMenu();
            ServiceWordsEnum command = mainMenuCommandAnalyzer();
            if (command.equals(ServiceWordsEnum.MANAGE_TWEETS))
                manageTweets();
            else if (command.equals(ServiceWordsEnum.MANAGE_FOLLOWS))
                manageFollows();
            else
                break;
        }
    }

    private ServiceWordsEnum mainMenuCommandAnalyzer() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String command = scanner.next();
            int index;
            try {
                index = Integer.parseInt(command);
            } catch (Exception e) {
                System.out.println("please enter a index!");
                continue;
            }
            if (index == 1)
                return ServiceWordsEnum.MANAGE_TWEETS;
            if (index == 2)
                return ServiceWordsEnum.MANAGE_FOLLOWS;
            if (index == 0)
                return ServiceWordsEnum.EXIT;
            System.out.println("enter a valid index");
        }
    }
    /*
    System.out.println("1 ) new tweet");
        System.out.println("2 ) remove a tweet");
        System.out.println("3 ) new retweet");
        System.out.println("4 ) new reply");
        System.out.println("5 ) remove a reply");
        System.out.println("6 ) like a tweet");
        System.out.println("7 ) unlike a tweet");
        System.out.println("8 ) show timeline");
        System.out.println("0 ) exit");
     */

    private void manageTweets() throws IOException {
        boolean isExit = false;
        while (!isExit) {
            consoleViewService.mainMenu();
            ServiceWordsEnum command = manageTweetsCommandAnalyzer();
            Scanner scanner = new Scanner(System.in);
            if (command.equals(ServiceWordsEnum.TWEET)) {
                boolean isValid = false;
                while (!isValid) {
                    System.out.println("pls enter the text :");
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("text", scanner.next());
                    JSONObject request = new JSONObject();
                    request.put("method", ServiceWordsEnum.TWEET);
                    request.put("parameterValues", jsonObject);
                    JSONObject response = connectionService.request(request);
                    consoleViewService.processServerResponse(response);
                    if (response.getString("hasError").equals("false"))
                        isValid = true;
                }
            } else if (command.equals(ServiceWordsEnum.REMOVETWEET)){
                JSONObject request = new JSONObject();
                request.put("method", ServiceWordsEnum.SHOW_MY_TWEETS);
                JSONObject response = connectionService.request(request);

            }
            else
                break;
        }
    }

    private ServiceWordsEnum manageTweetsCommandAnalyzer() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String command = scanner.next();
            int index;
            try {
                index = Integer.parseInt(command);
            } catch (Exception e) {
                System.out.println("please enter a index!");
                continue;
            }
            if (index == 1)
                return ServiceWordsEnum.TWEET;
            if (index == 2)
                return ServiceWordsEnum.REMOVETWEET;
            if (index == 3)
                return ServiceWordsEnum.RETWEET;
            if (index == 4)
                return ServiceWordsEnum.REPLY;
            if (index == 5)
                return ServiceWordsEnum.REMOVEREPLY;
            if (index == 6)
                return ServiceWordsEnum.LIKE;
            if (index == 7)
                return ServiceWordsEnum.DISLIKE;
            if (index == 8)
                return ServiceWordsEnum.TIMELINE;
            if (index == 0)
                return ServiceWordsEnum.EXIT;
            System.out.println("enter a valid index");
        }
    }

    private void manageFollows() {

    }
}
