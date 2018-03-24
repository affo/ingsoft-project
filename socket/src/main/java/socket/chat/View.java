package socket.chat;

import chat.model.*;

import java.util.Scanner;

/**
 * Created by affo on 08/03/18.
 */
public class View implements MessageReceivedObserver, GroupChangeListener {
    private Scanner fromKeyBoard;
    private boolean firstTimeStarted = true;
    // ----- The view is composed with the controller (strategy)
    private final ClientController controller;

    public View(ClientController controller) {
        this.controller = controller;
        this.fromKeyBoard = new Scanner(System.in);
    }

    private String userInput() {
        return fromKeyBoard.nextLine();
    }

    public void displayText(String text) {
        System.out.println(">>> " + text);
    }

    public void chooseGroupOption(int choice) {
        if (choice == 1) {
            Group group = controller.createGroup();
            displayText("Welcome to " + group.getName());

            group.observe(this);
        }

        else if (choice == 2) {
            Group group = controller.chooseGroup();
            displayText("Welcome to " + group.getName());

            group.observe(this);
        }
    }

    public void createFirstEverGroup() {
        Group group = controller.createGroup();
        displayText("Welcome to " + group.getName());

        group.observe(this);
    }

    public void chooseUsernamePhase() {
        User user;
        do {
            displayText("Provide username:");
            String userInput = userInput();
            user = controller.createUser(userInput);
        } while (user == null);

        displayText("You are " + user.getUsername());

        user.listenToMessages(this);
    }

    public void chooseGroupPhase() {
        if (firstTimeStarted) {
            createFirstEverGroup();
            firstTimeStarted = false;
        } else {
            int userChoice = 0;
            do {
                System.out.println("1 - Create new Group\n2 - Join existing Group");
                userChoice = Integer.parseInt(userInput());
            } while (userChoice != 1 || userChoice != 2 || userChoice == 0);
            chooseGroupOption(userChoice);
        }

    }

    public void messagingPhase() {
        controller.startMessaging();
        String content;
        do {
            content = userInput();
            controller.sendMessage(content);
        } while (!content.startsWith(":q"));
    }

    // ----- The view observes the state and reacts (the observable pushes the pieces of interesting state)

    @Override
    public void onMessage(Message message) {
        displayText(message.toString());
    }

    @Override
    public void onJoin(User user) {
        displayText(user.getUsername() + " joined the group");
    }

    @Override
    public void onLeave(User user) {
        displayText(user.getUsername() + " left the group");
    }
}
