import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyUtils {
    public static List<String> FileFinder(String folderPath) {
        try {
            // create process builder for find command
//            ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c", "find " + folderPath + " \\( -name '*.heif' -o -name '*.jpg' -o -name '*.JPG' -o -name '*.mp4' \\)");
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", "find " + folderPath + " \\( -name '*.heic' -o -name '*.jpg' -o -name '*.png' -o -name '*.mp4' -o -name '*.mov' \\)");
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                processBuilder = new ProcessBuilder("cmd.exe", "/c", "dir /b " + folderPath + "\\*.heic " + folderPath + "\\*.jpg " + folderPath + "\\*.png " + folderPath + "\\*.mp4 " + folderPath + "\\*.mov");
            }

            // start process and read output
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            List<String> files = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
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
    public static long dateToSeconds(String date){
        String splitDate = date.split(":", 2)[1];
        String[] fullDate = splitDate.substring(1).split("\\D+");
        int[] parsedDate = new int[6];
        for (int i = 0; i < 6; i++) {
            parsedDate[i] = Integer.parseInt(fullDate[i]);
        }
//                    "Based on code by ChatGPT in a Stack Overflow answer."
        return ((parsedDate[0] * 365L + parsedDate[0] / 4L) * 24L * 60L * 60L) // Year
                + ((parsedDate[1] - 1L) * 31L * 24L * 60L * 60L) // Month
                + ((parsedDate[2] - 1L) * 24L * 60L * 60L) // Day
                + (parsedDate[3] * 60L * 60L) // Hour
                + (parsedDate[4] * 60L) // Minute
                + parsedDate[5];
        //             "Based on code by ChatGPT in a Stack Overflow answer."
    }
    public static void MetaHandler(Multimedia file, String line ){
        List<Pattern> pattern = new ArrayList<>();
        pattern.add(Pattern.compile("GPS Latitude", Pattern.CASE_INSENSITIVE));
        pattern.add(Pattern.compile("GPS Longitude", Pattern.CASE_INSENSITIVE));
        pattern.add(Pattern.compile("Create Date", Pattern.CASE_INSENSITIVE));

        Matcher matcher;
        matcher = pattern.get(0).matcher(line);
        if (matcher.find()) {
            file.location[0] = MyUtils.GPSParser(line);
        }

        matcher = pattern.get(1).matcher(line);
        if (matcher.find()) {
            file.location[1] = MyUtils.GPSParser(line);
        }

        matcher = pattern.get(2).matcher(line);
        if (matcher.find()) {
            file.date = MyUtils.dateToSeconds(line);
        }
    }
    public static void FileTypeDetector(Multimedia file){
        String extension = file.path.substring(file.getPath().lastIndexOf(".") + 1).toLowerCase();
         switch (extension) {
            case "mp4", "mov" -> file.setImage(false);
            case "heic", "jpg", "png" -> file.setImage(true);
            default -> file.setImage(null);
        };
    }
}

