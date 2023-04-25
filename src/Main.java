import java.util.Arrays;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String openaiKey = System.getenv("OPENAI_KEY");
        System.out.println(openaiKey);

//        String absolutePath = MenuCaller.openFileChooser();
//        String directoryName = absolutePath + "/HopeYourFolderIsOkay";
//        FileManager.folderRemover(directoryName);
//        FileManager.folderCreator(directoryName);
//        Multimedia[] myFiles = FileGetter.files(absolutePath);
//        MyApi.mapQuestApi(myFiles[0].getLocation(),myFiles[myFiles.length-1].getLocation(),absolutePath);
//
//        myFiles[myFiles.length-1].getAllData();
//        for (Multimedia type : myFiles) {
//            if (type.getImage() == null) {
//                System.out.println("The file extension wasn't identified so it will not to be in the video\n\n");
//            } else if (type.getImage()) {
//                String newFilePath = FileManager.toMp4(type.getPath());
//                FFMPEGCommands.photoToVideo(type.getPath(), newFilePath);
//                type.setPath(newFilePath);
//            } else {
//                System.out.println("This is not a video\n\n");
//            }
//            type.getAllData();
//        }
    }
}

