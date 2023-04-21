import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class OpenWeatherApiClient {

    private static final String apiKey = "3233ac91292b4c006204060a119a223d";
    private static final String endpointURL = "http://api.openweathermap.org/data/2.5/weather";

    public static String getWeatherData(double lat, double lon) {
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
}
