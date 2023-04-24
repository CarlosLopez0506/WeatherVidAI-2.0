import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {

        System.out.println(OpenWeather.getWeatherData(40.5358, 20.203));
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
                    MyUtils.MetaHandler(myFiles[fileCounter],line);
                    if (myFiles[fileCounter].location[0] != null && myFiles[fileCounter].location[1] != null){
                        myFiles[fileCounter].setWeather(OpenWeather.GetDescription(OpenWeather.getWeatherData(Double.parseDouble(myFiles[fileCounter].location[0]), Double.parseDouble(myFiles[fileCounter].location[1]))));
                    }
                }
                MyUtils.FileTypeDetector(myFiles[fileCounter]);
                myFiles[fileCounter].getAllData();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            fileCounter += 1;
        }
    }
}