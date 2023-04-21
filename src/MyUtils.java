import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyUtils {
    public static List<String> FileFinder(String folderPath) {
        try {
            // create process builder for find command
//            ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c", "find " + folderPath + " \\( -name '*.heif' -o -name '*.jpg' -o -name '*.JPG' -o -name '*.mp4' \\)");
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", "find " + folderPath + " \\( -name '*.heif' -o -name '*.jpg' -o -name '*.JPG' -o -name '*.mp4' \\)");
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                processBuilder.command("cmd", "/c", "dir \"" + folderPath.replace("/", "\\") + "\" /b /s | findstr /i /r \".*\\.heif$ .*\\.jpg$ .*\\.mp4$\"");
            }

            // start process and read output
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            List<String> files = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                // extract filename from path
//                String[] folderSegments = line.split("/");
//                files.add(folderSegments[folderSegments.length-1]);
                files.add(line);
            }
            return files;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    public static String GPSParser(String coordinates) {
        String[] parts = coordinates.split(":")[1].trim().split("\\s+");

        double degrees = Double.parseDouble(parts[0]);
        double minutes = Double.parseDouble(parts[2].replaceAll("[^\\d.]", ""));
        double seconds = Double.parseDouble(parts[3].replaceAll("[^\\d.]", ""));
        double coords = degrees + minutes / 60 + seconds / 3600;
        String direction = parts[4];

        if(Objects.equals(direction, "N") || Objects.equals(direction, "E")){
            return  String.valueOf(coords);
        } else if (Objects.equals(direction, "S") || Objects.equals(direction, "W")) {
            return String.valueOf(-coords);
        }
        return "There's a mistake";
    }
    public static String dateToSeconds(String date){
        String splitDate = date.split(":", 2)[1];
        String[] fullDate = splitDate.substring(1).split("\\D+");
        int[] parsedDate = new int[6];
        for (int i = 0; i < 6; i++) {
            parsedDate[i] = Integer.parseInt(fullDate[i]);
        }
//                    "Based on code by ChatGPT in a Stack Overflow answer."
        long seconds = ((parsedDate[0] * 365L + parsedDate[0] / 4L) * 24L * 60L * 60L) // Year
                + ((parsedDate[1] - 1L) * 31L * 24L * 60L * 60L) // Month
                + ((parsedDate[2] - 1L) * 24L * 60L * 60L) // Day
                + (parsedDate[3] * 60L * 60L) // Hour
                + (parsedDate[4] * 60L) // Minute
                + parsedDate[5]; // Seconds
//                    "Based on code by ChatGPT in a Stack Overflow answer."

        return Long.toString(seconds);
    }
}

