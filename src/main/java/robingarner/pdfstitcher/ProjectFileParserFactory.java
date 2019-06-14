package robingarner.pdfstitcher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ProjectFileParserFactory {

  public static ProjectFileParser getParser(File projectFile) throws FileNotFoundException, IOException {
    if (projectFile.getName().toLowerCase().endsWith(".json")) {
      return new JSONParser(projectFile);
    }
    throw new Error("Don't know how to parse "+projectFile.getName());
  }

  public static ProjectFile parse(File projectFile) throws FileNotFoundException, IOException {
    return getParser(projectFile).parse();
  }
}
