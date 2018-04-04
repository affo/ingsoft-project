package gui;

import rmi.rmitter.control.RemoteController;
import rmi.rmitter.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by affo on 27/03/18.
 */
public class SwingGui extends UnicastRemoteObject
        implements FeedObserver, UserObserver, ActionListener {
    private final RemoteController controller;
    private JFrame mainFrame;
    private JTextArea textArea;
    private JTextField tokenTextField, usernameTextField, postTextField;
    private JButton loginButton;

    private String token;

    public SwingGui(RemoteController controller) throws RemoteException {
        super();
        this.controller = controller;
    }

    public void init() {
        mainFrame = new JFrame("RMItter");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        textArea = new JTextArea(10, 10);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        tokenTextField = new JTextField(10);
        usernameTextField = new JTextField(10);
        postTextField = new JTextField(20);
        loginButton = new JButton("Use token");

        JScrollPane scrollPane = new JScrollPane(textArea);

        JPanel northPanel = new JPanel();
        northPanel.add(usernameTextField);
        northPanel.add(tokenTextField);
        northPanel.add(loginButton);

        mainFrame.setLayout(new BorderLayout());
        mainFrame.add(northPanel, BorderLayout.NORTH);
        mainFrame.add(scrollPane, BorderLayout.CENTER);
        mainFrame.add(postTextField, BorderLayout.SOUTH);

        // ---- listeners
        loginButton.addActionListener(this);
        postTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    onEnterPressed();
                }
            }
        });
    }

    public void draw() {
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    private void writeToTextArea(String msg) {
        textArea.append(msg + "\n");
        //Make sure the new text is visible, even if there
        //was a selection in the text area.
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String username = usernameTextField.getText();
        String token = tokenTextField.getText();
        try {
            controller.observeUser(token, username, this, this);
            // disable loginButton if everything went well
            loginButton.setEnabled(false);
            writeToTextArea(">>> [OK]: logged as " + username);
            // ok, set the token for future calls
            this.token = token;
        } catch (RemoteException ex) {
            writeToTextArea(">>> [ERROR]: " + ex.getCause().getMessage());
        }
    }

    private void onEnterPressed() {
        if (token != null) {
            try {
                controller.post(token, postTextField.getText());
            } catch (RemoteException e) {
                writeToTextArea(">>> [ERROR]: " + e.getCause().getMessage());
            }
        }
        postTextField.setText(""); // clear text
    }

    // ---------------------------------- Remote events

    @Override
    public void onFolloweePost(Post post) throws RemoteException {
        writeToTextArea("@" + post.getPoster().getUsername() + " -> " + post.getContent());
    }

    @Override
    public void onHashtagUse(Hashtag hashtag, Post post) throws RemoteException {
        writeToTextArea("Hashtag " + hashtag + " used:");
        writeToTextArea(post.getContent());
    }

    @Override
    public void onNewPost(Post post) throws RemoteException {
        writeToTextArea(post.getContent());
    }

    @Override
    public void onMention(Post post) throws RemoteException {
        writeToTextArea("You were mentioned in:");
        writeToTextArea(post.getContent());
    }

    @Override
    public void onFollower(User user) throws RemoteException {
        writeToTextArea("New follower: " + user.getUsername());
    }

    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.getRegistry();

        // gets a reference for the remote controller
        final RemoteController controller = (RemoteController) registry.lookup("controller");
        SwingGui gui = new SwingGui(controller);
        gui.init();

        SwingUtilities.invokeLater(gui::draw);
    }
}
