package Services;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class FileService {
  static JFileChooser fileChooser = new JFileChooser();

  public static File importSongFile(String songName) throws Exception {
    List<String> formats = Arrays.asList("mp3", "wav");
    File selectedFile = chooseFile(formats);
    File destination = makeDirectory("src/Songs/" + (AuthService.isLoggedIn() ? AuthService.getUser().getId() : "test"));
    String fileName = songName != null ? songName + "." + getExtension(selectedFile.getName()) : selectedFile.getName();
    File savedFile = new File(destination + "/" + fileName);
    Files.copy(selectedFile.toPath(), savedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    System.out.println(destination);
    return savedFile;
  }

  public static File chooseFile(List<String> formats) {
    FileFilter filter = new FileFilter() {
      @Override
      public String getDescription() {
        return String.join(", ", formats.stream().map(f -> "*." + f).toList());
      }
      @Override
      public boolean accept(File f) {
        if (f.isDirectory()) return true;
        else return validateFormat(f, formats);
      }
    };
    fileChooser.setFileFilter(filter);

    int response = fileChooser.showOpenDialog(null);
    if (response == JFileChooser.APPROVE_OPTION) {
      File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
      return file;
    }
    return null;
  }

  public static boolean validateFormat(File f, List<String> formats) {
    String filename = f.getName().toLowerCase();
    for (String format : formats) {
      if (filename.endsWith(format)) return true;
    }
    return false;
  }

  public static File makeDirectory(String pathname) {
    File destination = new File(pathname);
    destination.mkdirs();
    return destination;
  }

  public static String getExtension(String fileName) {
    String extension = "";
    int i = fileName.lastIndexOf('.');
    if (i > 0) {
      extension = fileName.substring(i+1);
    }
    return extension;
  }

}
