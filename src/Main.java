import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {

        System.out.println(OpenWeatherApiClient.getWeatherData(40.5358, 20.203));
        int fileCounter = 0;
        Multimedia[] multimediaArray;
        Multimedia[] myFiles = new Multimedia[MyUtils.FileFinder("/home/clopez543/Downloads").size()];
//        myFiles[0].setDate("123","12");
        for (String fileName : MyUtils.FileFinder("/home/clopez543/Downloads")) {
            System.out.println("/////////////////////" + fileCounter);
            myFiles[fileCounter] = new Multimedia(fileName);
            ProcessBuilder processBuilder = new ProcessBuilder("./exiftool/exiftool", "-createDate", "-gpslatitude", "-gpslongitude", fileName);
            try {
                Process process = processBuilder.start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
//                System.out.println("File #: " + fileCounter + "\n");
                while ((line = reader.readLine()) != null) {
                    List<Pattern> pattern = new ArrayList<>();
                    pattern.add(Pattern.compile("GPS Latitude", Pattern.CASE_INSENSITIVE));
                    pattern.add(Pattern.compile("GPS Longitude", Pattern.CASE_INSENSITIVE));
                    pattern.add(Pattern.compile("Create Date", Pattern.CASE_INSENSITIVE));

                    Matcher matcher;
                    matcher = pattern.get(0).matcher(line);
                    if (matcher.find()) {
                        myFiles[fileCounter].location[0] = MyUtils.GPSParser(line);
                    }

                    matcher = pattern.get(1).matcher(line);
                    if (matcher.find()) {
                        myFiles[fileCounter].location[1] = MyUtils.GPSParser(line);
                    }

                    matcher = pattern.get(2).matcher(line);
                    if (matcher.find()) {
                    }
//                    OpenWeatherApiClient.getWeatherData(MyUtils.GPSParser(line), MyUtils.G);

                }


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            fileCounter += 1;
        }
    }
}