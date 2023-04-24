import java.util.Arrays;

public class Multimedia {
    String path;
    Long date;
    String[] location = new String[2];
    String weather;
    Boolean isImage;

    public Multimedia(String path) {
        setPath(path);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String[] getLocation() {
        return location;
    }

    public void setLocation(String[] location) {
        this.location = location;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public Boolean getImage() {
        return isImage;
    }

    public void setImage(Boolean image) {
        isImage = image;
    }
    public void getAllData(){
        System.out.printf("Date: %s\nPath: %s\nIs Image: %s\nLocation: %s\nWeather: %s\n\n",
                date, path, isImage, Arrays.toString(location), weather);
    }

}

