/* Read Files
* Save data
* Read states based off of "State : 0" and "State : 1" format
 */

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PlateSaver {

    private static JFrame frame = new JFrame("Account Info");
    private static boolean hasWashington = false;
    private static boolean hasOregon = false;
    private static List<String> states = new ArrayList();
    private static JTextField textInput = new JTextField(10);
    private static JLabel label = new JLabel("Enter Username: ");
    private static JLabel warningLabel = new JLabel("INVALID INPUT");
    private static JLabel suggestionLabel = new JLabel("Enter a username.");
    private static JButton continueButton = new JButton("Continue");
    private static JButton plateMapButton = new JButton("Plate Map");
    private static JButton indexButton = new JButton("Plate Index");
    private static JComboBox<String> menu = new JComboBox<>();
    private static GridBagConstraints gBagConst = new GridBagConstraints();
    public static JPanel panel = new JPanel(new GridLayout(0, 3));

    public static void main(String[] args) {
        states = FileIO.ReadFile("data.txt", false);

        frame.setSize(500, 125);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textInput.setSize(100, 10);

        panel.setLayout(new GridBagLayout());

        gBagConst.gridx = 0;
        gBagConst.gridy = 0;
        gBagConst.weighty = 50;
        gBagConst.fill = gBagConst.VERTICAL;
        panel.add(plateMapButton, gBagConst);
        gBagConst.gridy = 1;
        panel.add(indexButton, gBagConst);

        //panel.add(label);
        //panel.add(textInput);
        //panel.add(enterButton);
        frame.getContentPane().add(BorderLayout.NORTH, panel);
        frame.setVisible(true);

        plateMapButton.addActionListener((ActionEvent b) -> {
            if (b.getSource() == plateMapButton) {
                System.out.println(states);
            }
        });

    }
}
