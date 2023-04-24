import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
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
        String directoryName = absolutePath + "/HopeYourFolderIsOkay";
        System.out.println(directoryName);
        ProcessBuilder builder = new ProcessBuilder("rm", "-rf", directoryName);

        try {
            // Start the ProcessBuilder
            Process p = builder.start();

            // Wait for the process to complete
            p.waitFor();

            // Check the exit code to see if the directory was removed successfully
            int exitCode = p.exitValue();
            if (exitCode == 0) {
                // The directory was removed successfully
                System.out.println("Directory removed: " + directoryName);
            } else {
                // There was an error removing the directory
                System.out.println("Error removing directory: " + directoryName);
            }

        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("InterruptedException: " + e.getMessage());
        }

        // Create a ProcessBuilder to execute the "mkdir" command to create the directory
        builder = new ProcessBuilder("mkdir", "-p", directoryName);

        try {
            // Start the ProcessBuilder
            Process p = builder.start();

            // Wait for the process to complete
            p.waitFor();

            // Check the exit code to see if the directory was created successfully
            int exitCode = p.exitValue();
            if (exitCode == 0) {
                // The directory was created successfully
                System.out.println("Directory created: " + directoryName);
            } else {
                // There was an error creating the directory
                System.out.println("Error creating directory: " + directoryName);
            }

        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("InterruptedException: " + e.getMessage());
        }

        Multimedia[] myFiles = FileGetter.files(absolutePath);
        for (Multimedia type : myFiles) {
            if (type.getImage() == null) {
                System.out.println(" The value is null\n\n");
            } else if (type.getImage()) {
                String[] parts = type.getPath().split("/");

                // Get the file name from the last part of the path
                String fileName = parts[parts.length - 1];

                // Set the folder name to add before the file name
                String folderName = "HopeYourFolderIsOkay";

                // Use a regular expression to replace the file extension with .mp4
                Pattern pattern = Pattern.compile("\\.[^.]+$");
                Matcher matcher = pattern.matcher(fileName);
                String newFileName = matcher.replaceAll(".mp4");

                // Use a regular expression to replace the file name with the folder name and new file name
                pattern = Pattern.compile(fileName);
                matcher = pattern.matcher(type.getPath());
                String newFilePath = matcher.replaceFirst(folderName + "/" + newFileName);

                // Print the new file path
                System.out.println(newFilePath);
                try {
                    ProcessBuilder processBuilder = new ProcessBuilder("ffmpeg", "-y", "-loop", "1", "-i", type.getPath(), "-t", "2", "-c:v", "libx264", "-pix_fmt", "yuv420p", newFilePath);
                    Process process = processBuilder.start();
                    process.waitFor();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
                type.setPath(newFilePath);
            } else {
                System.out.println(" The value is false in\n\n");
            }
            type.getAllData();
        }
    }
}

