import javax.swing.*;
import java.io.File;

public class MenuCaller {
    public static String openFileChooser() {
        String absolutePath = null;
        JFileChooser fileChooser = new JFileChooser();

        // Set the file chooser to only allow selection of directories
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        // Show the file chooser dialog and get the result
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            // Get the selected directory and its absolute path
            File selectedDirectory = fileChooser.getSelectedFile();
            absolutePath = selectedDirectory.getAbsolutePath();

            // Print the absolute path to the console
            System.out.println("Selected directory: " + absolutePath);
        }
        return  absolutePath;
    }
}
