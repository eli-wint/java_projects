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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PlateSaver {

    private static JFrame frame = new JFrame("Plate Mapper");

    private static ImageIcon plateMapIcon = new ImageIcon("Plate Map.jpg");
    private static ImageIcon plateIndexIcon = new ImageIcon("Map Index.jpg");

    private static List<String> states = new ArrayList<>();
    private static JTextField textInput = new JTextField(10);
    private static JLabel label = new JLabel("Enter Username: ");
    private static JLabel warningLabel = new JLabel("INVALID INPUT");
    private static JLabel suggestionLabel = new JLabel("Enter a username.");
    private static JButton continueButton = new JButton("Continue");
    private static JButton plateMapButton = new JButton(plateMapIcon);
    private static JButton indexButton = new JButton(plateIndexIcon);
    private static JComboBox<String> menu = new JComboBox<>();
    private static GridBagConstraints gBagConst = new GridBagConstraints();
    //private static JPanel panel = new JPanel();
    private static final Map<String, JCheckBox> stateCheckboxes = new HashMap<>();
    private static JButton saveButton = new JButton("Save Data");
    private static String entry;
    private static JButton selectAllButton = new JButton("Select All");
    private static JButton disselectAllButton = new JButton("Deselect All");

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
            cb.setFont(new Font("Arial", Font.PLAIN, 9));
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

        indexButton.setBorderPainted(false);
        plateMapButton.setBorderPainted(false);
        indexButton.setContentAreaFilled(false);
        plateMapButton.setContentAreaFilled(false);

        changeBackground("Plate Mapper.jpg");

        states = FileIO.ReadFile("data.txt", false);
        panel.setLayout(null);

        frame.setSize(948, 548);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        plateMapButton.setFont(new Font("Arial", Font.PLAIN, 32));
        indexButton.setFont(new Font("Arial", Font.PLAIN, 32));

        //textInput.setSize(100, 10);
        plateMapButton.setBounds(263, 200, 200, 50);
        indexButton.setBounds(485, 200, 200, 50);
        continueButton.setBounds(0, 0, 110, 25);
        saveButton.setBounds(111, 0, 110, 25);
        selectAllButton.setBounds(222, 0, 110, 25);
        disselectAllButton.setBounds(332, 0, 110, 25);

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
                Map<String, int[]> statePositions = new HashMap<>();

                statePositions.put("washington", new int[]{84, 28});
                statePositions.put("oregon", new int[]{62, 88});
                statePositions.put("california", new int[]{27, 200});
                statePositions.put("montana", new int[]{243, 58});
                statePositions.put("idaho", new int[]{165, 107});
                statePositions.put("nevada", new int[]{102, 172});
                statePositions.put("utah", new int[]{194, 196});
                statePositions.put("arizona", new int[]{160, 275});
                statePositions.put("wyoming", new int[]{256, 129});
                statePositions.put("colorado", new int[]{274, 209});
                statePositions.put("new mexico", new int[]{249, 284});
                statePositions.put("north dakota", new int[]{370, 64});
                statePositions.put("south dakota", new int[]{370, 115});
                statePositions.put("nebraska", new int[]{376, 168});
                statePositions.put("kansas", new int[]{396, 223});
                statePositions.put("oklahoma", new int[]{411, 276});
                statePositions.put("texas", new int[]{391, 347});
                statePositions.put("minnesota", new int[]{466, 67});
                statePositions.put("iowa", new int[]{484, 158});
                statePositions.put("missouri", new int[]{508, 221});
                statePositions.put("arkansas", new int[]{510, 280});
                statePositions.put("louisiana", new int[]{525, 360});
                statePositions.put("wisconsin", new int[]{526, 105});
                statePositions.put("illinois", new int[]{562, 192});
                statePositions.put("kentucky", new int[]{616, 236});
                statePositions.put("tennessee", new int[]{587, 264});
                statePositions.put("mississippi", new int[]{564, 320});
                statePositions.put("michigan", new int[]{625, 120});
                statePositions.put("indiana", new int[]{617, 192});
                statePositions.put("alabama", new int[]{615, 304});
                statePositions.put("ohio", new int[]{670, 175});
                statePositions.put("georgia",  new int[]{677, 317});
                statePositions.put("florida", new int[]{720, 375});
                statePositions.put("new york", new int[]{770, 107});
                statePositions.put("pennsylvania", new int[]{730, 160});
                statePositions.put("west virginia", new int[]{712, 202});
                statePositions.put("virginia", new int[]{744, 222});
                statePositions.put("north carolina", new int[]{724, 254});
                statePositions.put("south carolina", new int[]{720, 282});
                statePositions.put("maryland", new int[]{790, 192});
                statePositions.put("vermont", new int[]{826, 82});
                statePositions.put("massachusetts", new int[]{838, 118});
                statePositions.put("rhode island", new int[]{856, 127});
                statePositions.put("connecticut", new int[]{835, 136});
                statePositions.put("new jersey", new int[]{815, 160});
                statePositions.put("delaware", new int[]{810, 182});
                statePositions.put("maine", new int[]{860, 50});
                statePositions.put("alaska", new int[]{100, 380});
                statePositions.put("hawaii", new int[]{310, 443});

                System.out.println(states);
                panel.remove(plateMapButton);
                panel.remove(indexButton);
                panel.add(continueButton);
                panel.add(saveButton);
                panel.add(selectAllButton);
                panel.add(disselectAllButton);

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

                changeBackground("Plate Mapper.jpg");

                for (JCheckBox cb : stateCheckboxes.values()) {
                    panel.remove(cb);
                }
                
                panel.add(plateMapButton);
                panel.add(indexButton);
                panel.remove(selectAllButton);
                panel.remove(disselectAllButton);
                panel.remove(saveButton);
                panel.remove(continueButton);
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

        selectAllButton.addActionListener((ActionEvent d) -> {
            if (d.getSource() == selectAllButton) {
                for (Map.Entry<String, JCheckBox> entry : stateCheckboxes.entrySet()) {
                    JCheckBox cb = entry.getValue();
                    cb.setSelected(true);
                }
                frame.repaint();
                frame.revalidate();
            }
        });

        disselectAllButton.addActionListener((ActionEvent e) -> {
            if (e.getSource() == disselectAllButton) {
                for (Map.Entry<String, JCheckBox> entry : stateCheckboxes.entrySet()) {
                    JCheckBox cb = entry.getValue();
                    cb.setSelected(false);
                }
            }
        });

        indexButton.addActionListener((ActionEvent f) -> {
            if (f.getSource() == indexButton) {
                panel.remove(indexButton);
                panel.remove(plateMapButton);
                panel.add(continueButton);
                continueButton.setText("Back");
                frame.repaint();
                frame.revalidate();
                changeBackground("PlateIndex_err.jpg");
            }
        });

    }
}
