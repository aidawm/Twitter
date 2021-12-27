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
    private Socket client;
    private OutputStream out;
    private InputStream in;
    private byte[] buffer = new byte[2048];
    private final ConsoleViewServiceImpl consoleViewService;

    public CommandParserServiceImpl(Socket client) throws IOException {
        this.client = client;
        this.out = client.getOutputStream();
        this.in = client.getInputStream();
        this.consoleViewService = new ConsoleViewServiceImpl();
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
            out.write(jsonObject.toString().getBytes());
            int read = in.read(buffer);
            String str = new String(buffer, 0, read);
            JSONObject response = new JSONObject(str);
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
            out.write(jsonObject.toString().getBytes());
            int read = in.read(buffer);
            String str = new String(buffer, 0, read);
            JSONObject response = new JSONObject(str);
            consoleViewService.processServerResponse(response);
            if (response.getString("hasError").equals("false"))
                isValid = true;
        }
    }

    private void mainMenu() {
        boolean isExit = false;
        while (!isExit) {
            consoleViewService.mainMenu();
            ServiceWordsEnum command = mainMenuCommandAnalyzer();
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
            if(index==1)
                return ServiceWordsEnum.MANAGE_TWEETS;
            if(index==2)
                return ServiceWordsEnum.MANAGE_FOLLOWS;
            if(index==0)
                return ServiceWordsEnum.EXIT;
            System.out.println("enter a valid index");
        }
    }
}
