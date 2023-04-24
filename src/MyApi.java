import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyApi {



    public static String getWeatherData(double lat, double lon) {
        final String apiKey = "3233ac91292b4c006204060a119a223d";
        final String endpointURL = "http://api.openweathermap.org/data/2.5/weather";
        List<String> commands = new ArrayList<>();
        commands.add("curl");
        commands.add("-s");
        commands.add("-G");
        commands.add("-d");
        commands.add("lat=" + lat);
        commands.add("-d");
        commands.add("lon=" + lon);
        commands.add("-d");
        commands.add("appid=" + apiKey);
        commands.add(endpointURL);

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(commands);
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            List<String> responseBuilder = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                responseBuilder.add(line);
            }
                   return responseBuilder.get(0);

        } catch (IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
    public static String getDescription(String json) {
        String pattern = "\"description\":\"([^\"]+)\"";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(json);
        if (m.find()) {
            return m.group(1);
        }
        return null;
    }
    public static void mapQuestApi(String[] firstLocation,String[] lastLocation,String folder){
        String key = "tCLZumjMcxTSrHDpuiLdofmtvhTSgjxZ";
        String url = "https://www.mapquestapi.com/staticmap/v5/map?key=" + key + "&locations=" + firstLocation[0] + "," + firstLocation[1] + "||" + lastLocation[0] + "," + lastLocation[1] + "&size=1100,500@2x";
        String[] command = {
                "curl",
                "-o",
                folder + "/MapQuest.jpg",
                url
        };
        ProcessBuilder processBuilder = new ProcessBuilder(Arrays.asList(command));
        processBuilder.inheritIO();
        try {
            Process process = processBuilder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        FFMPEGCommands.photoToVideo(folder, FileManager.toMp4(folder));


    }
}
