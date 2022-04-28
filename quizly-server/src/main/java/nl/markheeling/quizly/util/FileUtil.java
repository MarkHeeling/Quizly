package nl.markheeling.quizly.util;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class FileUtil {

  private FileUtil() {
    // restrict instantiation
  }

  public static final String folderPath =  "quizly-client/public/uploads/";
  public static final Path filePath = Paths.get(folderPath);

}
