import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String absolutePath = MenuCaller.openFileChooser();
        String directoryName = absolutePath + "/HopeYourFolderIsOkay";
        Multimedia[] myFiles = FileGetter.files(absolutePath);
        MyApi.mapQuestApi(myFiles[0].getLocation(),myFiles[myFiles.length-1].getLocation(),absolutePath);
        FileManager.folderRemover(directoryName);
        FileManager.folderCreator(directoryName);

        myFiles[myFiles.length-1].getAllData();
        for (Multimedia type : myFiles) {
            if (type.getImage() == null) {
                System.out.println(" The value is null\n\n");
            } else if (type.getImage()) {
                String newFilePath = FileManager.toMp4(type.getPath());
                FFMPEGCommands.photoToVideo(type.getPath(), newFilePath);
                type.setPath(newFilePath);
            } else {
                System.out.println(" The value is false in\n\n");
            }
            type.getAllData();
        }
    }
}

