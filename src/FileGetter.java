import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileGetter {
    public static Multimedia[] files(String path) {

        int fileCounter = 0;
        Multimedia[] myFiles = new Multimedia[MyUtils.FileFinder(path).size()];
//        myFiles[0].setDate("123","12");
        for (String fileName : MyUtils.FileFinder(path)) {
            myFiles[fileCounter] = new Multimedia(fileName);
            ProcessBuilder processBuilder = new ProcessBuilder("./exiftool/exiftool", "-createDate", "-gpslatitude", "-gpslongitude", fileName);
            try {
                Process process = processBuilder.start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    MyUtils.MetaHandler(myFiles[fileCounter],line);
                    if (myFiles[fileCounter].location[0] != null && myFiles[fileCounter].location[1] != null){
                        myFiles[fileCounter].setWeather(OpenWeather.GetDescription(OpenWeather.getWeatherData(Double.parseDouble(myFiles[fileCounter].location[0]), Double.parseDouble(myFiles[fileCounter].location[1]))));
                    }
                }
                MyUtils.FileTypeDetector(myFiles[fileCounter]);
                if(myFiles[fileCounter].getDate() == null){
                    myFiles[fileCounter].setDate(0L);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            fileCounter += 1;
        }
        int n = myFiles.length;
        Multimedia temp;
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n-i-1; j++) {
                if (myFiles[j].getDate() > myFiles[j+1].getDate()) {
                    // swap files[j+1] and files[j]
                    temp = myFiles[j];
                    myFiles[j] = myFiles[j+1];
                    myFiles[j+1] = temp;
                }
            }
        }
        return myFiles;
    }
}