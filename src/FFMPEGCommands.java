import java.io.IOException;

public class FFMPEGCommands {
    public static void photoToVideo(String input, String output) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("ffmpeg", "-y", "-loop", "1", "-i", input, "-t", "2", "-c:v", "libx264", "-pix_fmt", "yuv420p", output);
            Process process = processBuilder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
