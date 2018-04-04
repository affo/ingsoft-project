package rmi.rmitter.view;

import rmi.rmitter.control.RemoteController;
import rmi.rmitter.model.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/**
 * Created by affo on 19/03/18.
 */
public class TextView extends UnicastRemoteObject
        implements RemoteBaseView, UserObserver, FeedObserver {
    public static final String COMMAND_PREFIX = ":";
    public static final String POST_COMMAND = COMMAND_PREFIX + "post";
    public static final String FOLLOW_USER_COMMAND = COMMAND_PREFIX + "fuser";
    public static final String FOLLOW_HASHTAG_COMMAND = COMMAND_PREFIX + "fhashtag";
    public static final String QUIT_COMMAND = COMMAND_PREFIX + "q";

    private final Scanner in;
    private final RemoteController controller;
    private String token;

    public TextView(RemoteController controller) throws RemoteException {
        this.controller = controller;
        this.in = new Scanner(System.in);
    }

    private void printHelp() {
        System.out.println(">>> Available commands:");
        System.out.println("\t" + POST_COMMAND + "\t\tcreate a post");
        System.out.println("\t" + FOLLOW_USER_COMMAND + "\t\tfollow an user");
        System.out.println("\t" + FOLLOW_HASHTAG_COMMAND + "\tfollow a hashtag");
        System.out.println("\t" + QUIT_COMMAND + "\t\t\tquit");
    }

    private String nextCommand() {
        String command;

        do {
            System.out.println(">>> Next command:");
            command = in.nextLine();
            if (!command.startsWith(":")) {
                printHelp();
            }
        } while (!command.startsWith(":"));

        return command;
    }

    public void run() throws RemoteException {
        System.out.println("\t\tWelcome to RMItter!");

        String username;
        do {
            System.out.println(">>> Please provide username:");
            username = in.nextLine();
            if (!username.isEmpty()) {
                token = controller.login(username, this);
                System.out.println(">>> This is your token:");
                System.out.println(token);
                System.out.println(">>> Use it with other clients");
                System.out.println();
            }
        } while (username.isEmpty());

        // subscribe to notifications
        controller.observeUser(token, username, this, this);

        String command;
        do {
            command = nextCommand();
            int firstSpace = command.indexOf(" ");

            if (!command.startsWith(QUIT_COMMAND) && firstSpace >= 0) {
                String method = command.substring(0, firstSpace);
                String arg = command.substring(firstSpace + 1, command.length());

                try {
                    switch (method) {
                        case POST_COMMAND:
                            controller.post(token, arg);
                            break;
                        case FOLLOW_USER_COMMAND:
                            controller.followUser(token, arg);
                            break;
                        case FOLLOW_HASHTAG_COMMAND:
                            controller.followHashtag(token, arg, this);
                            break;
                        default:
                            printHelp();
                    }
                } catch (RemoteException re) {
                    error(re.getCause().getMessage());
                }
            }
        } while (!command.startsWith(QUIT_COMMAND));

        controller.logout(token);
        System.out.println("\t\tBYE!");
    }

    @Override
    public void error(String message) throws RemoteException {
        System.err.println(">>> " + message);
    }

    @Override
    public void ack(String message) throws RemoteException {
        System.out.println(">>> " + message);
    }

    @Override
    public void onNewPost(Post post) throws RemoteException {
        System.out.println(">>> " + post.getPoster().getUsername() + ":");
        System.out.println(post.getContent());
    }

    @Override
    public void onMention(Post post) throws RemoteException {
        System.out.println(">>> You were mentioned in a post:");
        System.out.println(post.getContent());
    }

    @Override
    public void onFollower(User follower) throws RemoteException {
        System.out.println(">>> You have a new follower: " + follower.getUsername());
    }

    @Override
    public void onFolloweePost(Post post) throws RemoteException {
        System.out.println(">>> A user you follow posted:");
        System.out.println(post.getPoster().getUsername() + " -> " + post.getContent());
    }

    @Override
    public void onHashtagUse(Hashtag hashtag, Post post) throws RemoteException {
        System.out.println(">>> Hashtag used: " + hashtag.toString());
        System.out.println(post.getContent());
    }
}
