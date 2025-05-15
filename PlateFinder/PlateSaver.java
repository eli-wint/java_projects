/* Read Files
* Save data
* Read states based off of "State : 0" and "State : 1" format
 */

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PlateSaver {

    private static JFrame frame = new JFrame("PlateMapper");

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
    //private static JPanel panel = new JPanel();
    private static final Map<String, JCheckBox> stateCheckboxes = new HashMap<>();
    private static JButton saveButton = new JButton("Save Data");
    private static String entry;
    private static JButton selectAllButton = new JButton("Select All");
    private static JButton disselectAllButton = new JButton("Disselect All");

    private static Image img;

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
            cb.setOpaque(false);
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

    private static JPanel panel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (img != null) {
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        }
    };

    private static void changeBackground(String imagePath) {
        try {
            // Load the new image
            img = ImageIO.read(new File(imagePath)); // Load image from file path
            panel.repaint(); // Repaint the panel to apply the new background
        } catch (IOException e) {
            e.printStackTrace(); // Handle error loading image
        }
    }

    public static void main(String[] args) {
        changeBackground("PlateMapperBG.jpg");

        states = FileIO.ReadFile("data.txt", false);
        panel.setLayout(null);

        frame.setSize(948, 548);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        plateMapButton.setFont(new Font("Arial", Font.PLAIN, 32));
        indexButton.setFont(new Font("Arial", Font.PLAIN, 32));

        //textInput.setSize(100, 10);
        plateMapButton.setBounds(273, 100, 200, 50);
        indexButton.setBounds(475, 100, 200, 50);
        continueButton.setBounds(0, 0, 100, 25);
        saveButton.setBounds(0, 30, 100, 25);

        panel.add(plateMapButton);
        panel.add(indexButton);

        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setVisible(true);

        initializeStateCheckboxes();

        for (String state : stateCheckboxes.keySet()) {
            entry = state + " : 1";
            JCheckBox cb = stateCheckboxes.get(state);
            cb.setSelected(states.contains(entry));
        }

        plateMapButton.addActionListener((ActionEvent a) -> {
            if (a.getSource() == plateMapButton) {
                continueButton.setBounds(0, 0, 100, 25);
                saveButton.setBounds(100, 0, 100, 25);

                Map<String, int[]> statePositions = new HashMap<>();

                statePositions.put("washington", new int[]{64, 28});
                statePositions.put("oregon", new int[]{52, 88});
                statePositions.put("california", new int[]{27, 200});
                statePositions.put("montana", new int[]{243, 58});
                statePositions.put("idaho", new int[]{155, 103});
                statePositions.put("nevada", new int[]{92, 172});
                statePositions.put("utah", new int[]{188, 200});
                statePositions.put("arizona", new int[]{160, 275});
                statePositions.put("wyoming", new int[]{256, 129});
                statePositions.put("colorado", new int[]{274, 209});
                statePositions.put("new mexico", new int[]{249, 284});
                statePositions.put("north dakota", new int[]{360, 64});
                statePositions.put("south dakota", new int[]{360, 115});
                statePositions.put("nebraska", new int[]{376, 168});
                statePositions.put("kansas", new int[]{396, 223});
                statePositions.put("oklahoma", new int[]{411, 276});
                statePositions.put("texas", new int[]{391, 347});
                statePositions.put("minnesota", new int[]{466, 67});
                statePositions.put("iowa", new int[]{484, 158});

                System.out.println(states);
                panel.remove(plateMapButton);
                panel.remove(indexButton);
                panel.add(continueButton);
                panel.add(saveButton);

                changeBackground("us2.jpg");

                continueButton.setText("Main Menu");
                for (Map.Entry<String, JCheckBox> entry : stateCheckboxes.entrySet()) {
                    String state = entry.getKey();
                    JCheckBox cb = entry.getValue();

                    // Set position if available
                    if (statePositions.containsKey(state)) {
                        int[] pos = statePositions.get(state);
                        cb.setBounds(pos[0], pos[1], 120, 20); // width & height can be adjusted
                    }

                    panel.add(cb);
                }
                frame.repaint();
                frame.revalidate();
            }
        });

        continueButton.addActionListener((ActionEvent b) -> {
            if (b.getSource() == continueButton) {

                plateMapButton.setFont(new Font("Arial", Font.PLAIN, 32));
                indexButton.setFont(new Font("Arial", Font.PLAIN, 32));

                changeBackground("PlateMapperBG.jpg");

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
