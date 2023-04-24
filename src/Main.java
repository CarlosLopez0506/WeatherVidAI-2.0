public class Main {
    public static void main(String[] args) {
        String absolutePath = MenuCaller.openFileChooser();
        String directoryName = absolutePath + "/HopeYourFolderIsOkay";
        System.out.println(directoryName);
        FileManager.folderRemover(directoryName);
        FileManager.folderCreator(directoryName);

        Multimedia[] myFiles = FileGetter.files(absolutePath);
        for (Multimedia type : myFiles) {
            if (type.getImage() == null) {
                System.out.println(" The value is null\n\n");
            } else if (type.getImage()) {
                String newFilePath = FileManager.changePath(type);
                FFMPEGCommands.photoToVideo(type.getPath(), newFilePath);
                type.setPath(newFilePath);
            } else {
                System.out.println(" The value is false in\n\n");
            }
            type.getAllData();
        }
    }
}

