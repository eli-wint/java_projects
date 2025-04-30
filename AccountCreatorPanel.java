// Make saveable passwords, link passwords to account names and make account tabs.
/* GOALS:
 * - Make custom account tabs
 * - Allow user to create txt files
 * - Allow user to edit txt files
 * - Allow user to log out
 * - Allow user to save files
 * - Allow users to share files
 * - Edit username
 */

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class AccountCreatorPanel {

    public static String accountName;
    public static String accountPassword;
    public static boolean nameHasBeenRead = false;
    public static List<String> accountNames = new ArrayList<>();
    public static JTextField textInput = new JTextField(10);
    public static JPanel panel = new JPanel(new GridLayout(0, 3));
    public static Random random = new Random();
    private static Map<String, AccountCreator> accountMap = new HashMap<>();
    public static boolean startupNotComplete = true;
    public static boolean accountCreation = false;
    public static String entryInput = textInput.getText();
    public static List<String> tempUsedNames = new ArrayList<>();
    public static boolean isLoggingIn = false;
    public static boolean isCheckingPassword = false;
    public static String passedUsername;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Account Info");
        frame.setSize(500, 125);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Enter Username: ");
        JLabel warningLabel = new JLabel("INVALID INPUT");
        JLabel suggestionLabel = new JLabel("Enter a username.");
        JButton enterButton = new JButton("Continue");
        JButton createAccountButton = new JButton("Create Account");
        JButton loginButton = new JButton("Login");
        JComboBox<String> menu = new JComboBox<>();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        //JTabbedPane accountTab = new JTabbedPane();
        //JMenuBar accountMenu = new JMenuBar();
        //JMenuItem home = new JMenuItem();
        textInput.setSize(100, 10);

        panel.setLayout(new GridBagLayout());

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        panel.add(createAccountButton, gridBagConstraints);
        gridBagConstraints.gridy = 1;
        panel.add(loginButton, gridBagConstraints);

        //panel.add(label);
        //panel.add(textInput);
        //panel.add(enterButton);
        frame.getContentPane().add(BorderLayout.NORTH, panel);
        frame.setVisible(true);

        FileIO.FileProcessor("Nothing", "AccountUsernames.txt");

        textInput.getDocument().addDocumentListener(new DocumentListener() {
            private void handleDocumentUpdate() {
                String inputText = textInput.getText();
                if (isLoggingIn) {
                    updateLogin();
                } else {
                    // for making account
                    updateLabel();
                }
            }
        
            @Override
            public void insertUpdate(DocumentEvent e) {
                handleDocumentUpdate();
            }
        
            @Override
            public void removeUpdate(DocumentEvent e) {
                handleDocumentUpdate();
            }
        
            @Override
            public void changedUpdate(DocumentEvent e) {
                handleDocumentUpdate();
            }

            // Method to update the label with the current text in the text field
            private void updateLabel() {
                if ((accountNames.contains(textInput.getText().trim().toLowerCase())) || accountNames.contains(textInput.getText().trim().toLowerCase().replace("[", "")) || tempUsedNames.contains(textInput.getText())) {
                    int randomNum = random.nextInt(10) + 1;
                    suggestionLabel.setText("<html>'" + textInput.getText() + "'" + " is taken. <br>Try: " + textInput.getText() + randomNum);
                } else if (!(textInput.getText().isEmpty())) {
                    suggestionLabel.setText("'" + textInput.getText() + "'" + " is available.");
                } else if (textInput.getText().isEmpty()) {
                    suggestionLabel.setText("Enter a username.");
                }
            }

            private void updateLogin() {
                FileIO.ReadFile("AccountUsernames.txt", true);
                List<String> validUsernames = FileIO.validUsernames;
                if (validUsernames.contains(textInput.getText().toLowerCase())) {
                    suggestionLabel.setText("Valid Username");
                } else {
                    suggestionLabel.setText("Invalid Username");
                }
            }
        });

        loginButton.addActionListener((ActionEvent b) -> {
            if (b.getSource() == loginButton) {
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 0;
                isLoggingIn = true;
                FileIO.ReadFile("AccountUsernames.txt", true);
                panel.remove(loginButton);
                panel.remove(createAccountButton);
                label.setText("Username");
                panel.add(label, gridBagConstraints);
                gridBagConstraints.gridx = 1;
                panel.add(textInput, gridBagConstraints);
                gridBagConstraints.gridx = 2;
                panel.add(enterButton,gridBagConstraints);
                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = 1;
                panel.add(suggestionLabel,gridBagConstraints);
                frame.revalidate();
                frame.repaint();
            }
        });

        createAccountButton.addActionListener((ActionEvent a) -> {
            if (a.getSource() == createAccountButton) {
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 0;
                accountCreation = true;
                nameHasBeenRead = false;
                label.setText("Enter Username: ");
                textInput.setText("");
                panel.remove(label);
                panel.remove(loginButton);
                panel.remove(createAccountButton);
                panel.add(label,gridBagConstraints);
                gridBagConstraints.gridx = 1;
                panel.add(textInput, gridBagConstraints);
                gridBagConstraints.gridx = 2;
                panel.add(enterButton);
                panel.add(suggestionLabel);
                frame.revalidate();
                frame.repaint();
            }
        });

        enterButton.addActionListener((ActionEvent e) -> {
            if (e.getSource() == enterButton) {
                if ((textInput.getText().isEmpty() || (accountNames.contains(textInput.getText())) && (nameHasBeenRead == false) || tempUsedNames.contains(textInput.getText())) && !(isLoggingIn)) {
                    frame.getContentPane().add(BorderLayout.SOUTH, warningLabel);
                    frame.revalidate();
                } else if (!(isLoggingIn) && (nameHasBeenRead == false) && !(accountNames.contains(textInput.getText().trim().toLowerCase().replace("[", "")))) {
                    accountName = textInput.getText();
                    panel.remove(suggestionLabel);
                    panel.remove(warningLabel);
                    nameHasBeenRead = true;
                    label.setText("Enter Password: ");
                    textInput.setText("");
                    frame.getContentPane().remove(warningLabel);
                    frame.revalidate();
                    frame.repaint(0);
                } else if (!(isLoggingIn) && (nameHasBeenRead == true) && !(textInput.getText().isEmpty())) {
                    FileIO.accountCreation = true;
                    accountPassword = textInput.getText();
                    String writeToFile = accountName + " : " + accountPassword;
                    accountNames.add((accountName + " : " + accountPassword));
                    tempUsedNames.add(accountName);
                    FileIO.FileProcessor(writeToFile, "AccountUsernames.txt");
                    // ------------------------------------------------------------------------------------------------------------------------------------------------
                    int id = AccountCreator.makeAccountID(); // Generate a new ID based on existing entries
                    String accountID = "acc" + id;
                    AccountCreator newAccount = new AccountCreator(accountName, accountPassword);
                    accountMap.put(accountID, newAccount);
                    textInput.setText("");
                    label.setText("<html>" + "Account Created!" + "<br>" + "Username: " + accountName + "<br>" + "Password: " + accountPassword);
                    createAccountButton.setText("Create New Account");
                    panel.remove(textInput);
                    panel.remove(enterButton);
                    panel.remove(suggestionLabel);
                    panel.add(createAccountButton);
                    panel.add(loginButton);
                    frame.revalidate();
                    frame.repaint();
                    FileIO.accountCreation = false;
                } else if (isLoggingIn == true) {
                    List<String> validUsernames = FileIO.validUsernames;
                    FileIO.ReadFile("AccountUsernames.txt", true);
                    if (validUsernames.contains(textInput.getText().toLowerCase())) {

                        panel.remove(textInput);
                        panel.remove(enterButton);
                        panel.remove(suggestionLabel);
                        panel.remove(label);
                        
                        panel.add(label);
                        panel.add(textInput);
                        panel.add(enterButton);

                        // if it is the username + "  " + the password
                        isCheckingPassword = true;
                        passedUsername = textInput.getText();
                        System.out.println("Good username");
                        textInput.setText("");
                        label.setText("Enter Password");
                        frame.remove(warningLabel);
                        frame.revalidate();
                        frame.repaint();
                    }
                        if (isCheckingPassword) {
                            System.out.println("Checking Password");
                            System.out.println(passedUsername + "  " + textInput.getText());
                            System.out.println(FileIO.ReadFile("AccountUsernames.txt", false));
                            if (FileIO.ReadFile("AccountUsernames.txt", false).contains(passedUsername.toLowerCase() + "  " + textInput.getText().toLowerCase())){
                                textInput.setText("");
                                System.out.println("Password is correct!");
                                panel.remove(textInput);
                                panel.remove(enterButton);
                                gridBagConstraints.gridx = 0;
                                gridBagConstraints.gridy = 1;
                                gridBagConstraints.anchor = gridBagConstraints.HORIZONTAL;
                                panel.add(menu);
                                menu.addItem("Home");
                                //frame.add(accountMenu);
                                //accountMenu.add(home);
                                //frame.add(accountTab);
                                //accountTab.add("Home", label);
                                label.setText("Welcome: " + passedUsername.substring(0,1).toUpperCase() + passedUsername.substring(1));
                                frame.revalidate();
                                frame.repaint();
                            }
                        
                    }
                }
            }
        });
    }
}
