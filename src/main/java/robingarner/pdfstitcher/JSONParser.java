package robingarner.pdfstitcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONParser implements ProjectFileParser {

  private File inputFile;

  private final InputStream stream;

  public JSONParser(File input) throws FileNotFoundException {
    this(new FileInputStream(input));
    this.inputFile = input;
  }

  public JSONParser(InputStream stream) throws FileNotFoundException {
    this.stream = stream;
  }


  @Override
  public ProjectFile parse() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();

    return objectMapper.readValue(stream, ProjectFile.class)
        .setProjectFile(inputFile);
  }


}
