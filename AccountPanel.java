import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class AccountPanel {

    public static String accountName;
    public static String accountPassword;
    public static boolean nameHasBeenRead = false;
    public static List<String> accountNames = new ArrayList<>();
    public static JTextField textInput = new JTextField(10);
    public static JPanel panel = new JPanel(new GridLayout(0, 3));
    public static Random random = new Random();
    public static boolean startupNotComplete = true;
    public static boolean accountCreation = false;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Account Info");
        frame.setSize(500, 125);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Enter Username: ");
        JLabel warningLabel = new JLabel("INVALID INPUT");
        JLabel suggestionLabel = new JLabel("Enter a username.");
        JButton enterButton = new JButton("Continue");
        JButton createAccountButton = new JButton("Create Account");
        textInput.setSize(100, 10);

        panel.add(createAccountButton);

        //panel.add(label);
        //panel.add(textInput);
        //panel.add(enterButton);
        frame.getContentPane().add(BorderLayout.NORTH, panel);
        frame.setVisible(true);

        FileIO("Nothing");

        textInput.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateLabel();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateLabel();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateLabel();
            }

            // Method to update the label with the current text in the text field
            private void updateLabel() {
                System.out.println(accountNames.toString());
                System.out.println(textInput.getText());
                if ((accountNames.contains(textInput.getText().trim())) || accountNames.contains(textInput.getText().trim().split(",\\s*"))) {
                    int randomNum = random.nextInt(10) + 1;
                    suggestionLabel.setText("<html>'" + textInput.getText() + "'" + " is taken. <br>Try: " + textInput.getText() + randomNum);
                } else if (!(textInput.getText().isEmpty())) {
                    suggestionLabel.setText("'" + textInput.getText() + "'" + " is available.");
                } else if (textInput.getText().isEmpty()) {
                    suggestionLabel.setText("Enter a username.");
                }
            }
        });

        createAccountButton.addActionListener((ActionEvent a) -> {
            if (a.getSource() == createAccountButton) {
                accountCreation = true;
                nameHasBeenRead = false;
                label.setText("Enter Username: ");
                textInput.setText("");
                panel.remove(label);
                panel.remove(createAccountButton);
                panel.add(label);
                panel.add(textInput);
                panel.add(enterButton);
                panel.add(suggestionLabel);
                frame.revalidate();
                frame.repaint();
            }
        });

        enterButton.addActionListener((ActionEvent e) -> {
            if (e.getSource() == enterButton) {
                System.out.println(textInput.getText());

                if (textInput.getText().isEmpty() || (accountNames.contains(textInput.getText())) && (nameHasBeenRead == false)) {
                    frame.getContentPane().add(BorderLayout.SOUTH, warningLabel);
                    frame.revalidate();
                } else if ((nameHasBeenRead == false) && !(accountNames.contains(textInput.getText()))) {
                    accountNames.add(textInput.getText());
                    FileIO(accountNames.toString().replace("[", "").replace("]", ""));
                    panel.remove(suggestionLabel);
                    panel.remove(warningLabel);
                    nameHasBeenRead = true;
                    accountName = textInput.getText();
                    label.setText("Enter Password: ");
                    textInput.setText("");
                    frame.getContentPane().remove(warningLabel);
                    frame.revalidate();
                    frame.repaint(0);
                } else if ((nameHasBeenRead == true) && !(textInput.getText().isEmpty())) {
                    accountPassword = textInput.getText();
                    textInput.setText("");
                    label.setText("<html>" + "Account Created!" + "<br>" + "Username: " + accountName + "<br>" + "Password: " + accountPassword);
                    createAccountButton.setText("Create New Account");
                    panel.remove(textInput);
                    panel.remove(enterButton);
                    panel.remove(suggestionLabel);
                    panel.add(createAccountButton);
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });
    }

    @SuppressWarnings({"ConvertToTryWithResources", "CallToPrintStackTrace"})
    private static void FileIO(String input) {
        if (accountCreation == true) {
            try {
                new FileWriter("AccountUsernames.txt", false).close();
                BufferedWriter writer = new BufferedWriter(new FileWriter("AccountUsernames.txt", true)); // Append mode
                writer.write(input.replace(", ", "\n").replace("[", "").replace("]", ""));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        if (startupNotComplete == true) {
            startupNotComplete = false;
            try {
                BufferedReader reader = new BufferedReader(new FileReader("AccountUsernames.txt"));
                String line;
                while ((line = reader.readLine()) != null) {
                    accountNames.add(line.replace(", ", "\n").replace("[", "").replace("]", ""));
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}