//  This function reads the file and writes to the file, FileProcessor(text, file); and 
// FileReader(file, isReadingUsernames); and returns a List<String> of everything in the file
// (preferably formatted with "text : text")
// NOTE: the return REMOVES all ":" which means the text will look like "text  text" rather than "text text"
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileIO {

    public static boolean startupNotComplete = true;
    public static boolean accountCreation;
    public static List<String> accountNames;
    public static List<String> savedPlates = new ArrayList<>();

    @SuppressWarnings({"ConvertToTryWithResources", "CallToPrintStackTrace"})
    static void FileProcessor(String input, String fileUsed) {
        if (accountCreation == true) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileUsed, true)); // Append mode
                writer.write(input.replace(", ", "\n").replace("[", "").replace("]", ""));
                writer.newLine();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (startupNotComplete == true) {
            startupNotComplete = false;
            try {
                BufferedReader reader = new BufferedReader(new FileReader(fileUsed));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts.length > 0) {
                        String username = parts[0].trim().toLowerCase();
                        accountNames.add(username);
                    }
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static List<String> ReadFile(String fileName, boolean readFirstWord) {
        if (readFirstWord) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts.length > 0) {
                        String username = parts[0].trim().toLowerCase().replace("[", "").replace("]", "").replace(", ", ":");
                        savedPlates.add(username);
                    }
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        } else {
            savedPlates.clear();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts.length > 0) {
                        String username = (parts[0] + parts[1]).trim().toLowerCase().replace("[", "").replace("]", "").replace("  ", " : ");
                        savedPlates.add(username);
                    }
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return savedPlates;
    }
}
