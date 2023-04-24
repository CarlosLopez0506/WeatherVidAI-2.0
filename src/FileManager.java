import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileManager {
    public static void folderRemover(String directoryName) {
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

    }

    public static void folderCreator(String directoryName) {

        // Create a ProcessBuilder to execute the "mkdir" command to create the directory
        ProcessBuilder builder = new ProcessBuilder("mkdir", "-p", directoryName);

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

    }
    public static String toMp4(String path){
        String[] parts = path.split("/");
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
        matcher = pattern.matcher(path);
        return matcher.replaceFirst(folderName + "/" + newFileName);
    }

}
