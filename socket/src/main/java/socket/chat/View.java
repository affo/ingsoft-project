package socket.chat;

import chat.model.*;

import java.util.Scanner;

/**
 * Created by affo on 08/03/18.
 */
public class View implements MessageReceivedObserver, GroupChangeListener {
    private Scanner fromKeyBoard;
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

    public void displayGroups() {
        int commaController = 1;
        System.out.print("[");
        for (Group group : controller.getGroups()) {
            System.out.print(group.getName());
            if (commaController < controller.getGroups().size()) {
                System.out.print(",");
                commaController++;
            }

        }
        System.out.println("]");
    }

    public boolean isGroupsEmpty() {
        return controller.getGroups().isEmpty();
    }

    public void chooseGroupOption(int choice) {
        if (choice == 1) {
            Group group = controller.createGroup();
            displayText("Welcome to " + group.getName());
            displayText("Write :q to quit the group\n");

            group.observe(this);
        } else if (choice == 2) {
            Group group;
            do {
                System.out.print("Write name of the group you'd like to join: ");
                String selectedGroupName = userInput();
                group = controller.chooseGroup(selectedGroupName);
            } while (group == null);
            displayText("Welcome to " + group.getName());
            displayText("Write :q to quit the group\n");

            group.observe(this);
        }
    }

    public boolean isValidUserChoice(int userChoice) {
        if (userChoice != 1 && userChoice != 2) return false;
        return true;
    }

    public void createFirstEverGroup() {
        Group group = controller.createGroup();
        displayText("Welcome to " + group.getName());
        displayText("Write :q to quit the group\n");

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

    public void chooseGroupPhase() throws IllegalArgumentException {
        int userChoice;
        if (isGroupsEmpty()) {
            displayText("First group will be created");
            createFirstEverGroup();
        } else {
            // lazy String formatting
            System.out.print("1 - Create new Group\n2 - Join existent group: ");
            displayGroups();
            userChoice = Integer.parseInt(userInput());
            if (!isValidUserChoice(userChoice)) {
                throw new IllegalArgumentException();
            }
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
