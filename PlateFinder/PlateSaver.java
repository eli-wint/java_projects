/* Read Files
* Save data
* Read states based off of "State : 0" and "State : 1" format
 */

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class PlateSaver {

    private static JFrame frame = new JFrame("PlateSaver");

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
    private static JPanel panel = new JPanel();
    private static JScrollPane scrollPane = new JScrollPane(panel);
    private static final Map<String, JCheckBox> stateCheckboxes = new HashMap<>();
    private static JButton saveButton = new JButton("Save Data");
    private static String entry;

    private static void initializeStateCheckboxes() {
        String[] statesArray = {
            "alabama", "alaska", "arizona", "arkansas", "california", "colorado", "connecticut", "delaware",
            "florida", "georgia", "hawaii", "idaho", "illinois", "indiana", "iowa", "kansas", "kentucky",
            "louisiana", "maine", "maryland", "massachusetts", "michigan", "minnesota", "mississippi",
            "missouri", "montana", "nebraska", "nevada", "new hampshire", "new jersey", "new mexico",
            "new york", "north carolina", "north dakota", "ohio", "oklahoma", "oregon", "pennsylvania",
            "rhode island", "south carolina", "south dakota", "tennessee", "texas", "utah", "vermont",
            "virginia", "washington", "west virginia", "wisconsin", "wyoming"
        };

        for (String state : statesArray) {
            JCheckBox cb = new JCheckBox(toTitleCase(state));
            stateCheckboxes.put(state, cb);
        }
    }

    private static String toTitleCase(String input) {
        String[] parts = input.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            if (sb.length() > 0) {
                sb.append(" ");
            }
            sb.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        states = FileIO.ReadFile("data.txt", false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        frame.setSize(500, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        textInput.setSize(100, 10);

        panel.add(plateMapButton);
        panel.add(indexButton);

        frame.getContentPane().add(BorderLayout.CENTER, scrollPane);
        frame.setVisible(true);

        initializeStateCheckboxes();

        for (String state : stateCheckboxes.keySet()) {
            entry = state + " : 1";
            JCheckBox cb = stateCheckboxes.get(state);
            cb.setSelected(states.contains(entry));
        }

        plateMapButton.addActionListener((ActionEvent a) -> {
            if (a.getSource() == plateMapButton) {
                System.out.println(states);
                panel.remove(plateMapButton);
                panel.remove(indexButton);
                panel.add(continueButton);
                panel.add(saveButton);
                continueButton.setText("Main Menu");
                for (JCheckBox cb : stateCheckboxes.values()) {
                    panel.add(cb);
                }
                frame.repaint();
                frame.revalidate();
            }
        });

        continueButton.addActionListener((ActionEvent b) -> {
            if (b.getSource() == continueButton) {
                for (JCheckBox cb : stateCheckboxes.values()) {
                    panel.remove(cb);
                }
                panel.remove(continueButton);
                panel.add(plateMapButton);
                panel.add(indexButton);
                panel.remove(saveButton);
                frame.repaint();
                frame.revalidate();
            }
        });

        saveButton.addActionListener((ActionEvent c) -> {
            if (c.getSource() == saveButton) {
                List<String> updatedStates = new ArrayList<>();

                for (Map.Entry<String, JCheckBox> entry : stateCheckboxes.entrySet()) {
                    String state = entry.getKey();
                    JCheckBox cb = entry.getValue();
                    String stateLine = state + " : " + (cb.isSelected() ? "1" : "0");
                    updatedStates.add(stateLine);
                }

                FileIO.FileProcessor(updatedStates, "data.txt");
                System.out.println("Saved states: " + updatedStates);
                System.out.println("done");
            }
        });

    }
}
