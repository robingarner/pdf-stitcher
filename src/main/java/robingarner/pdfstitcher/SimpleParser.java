package robingarner.pdfstitcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SimpleParser implements ProjectFileParser {

  private final ProjectFile project = new ProjectFile();

  private final File input;


  public SimpleParser(File input) throws IOException {
    this.input = input;
  }

  /**
   * Parse the input file name from a line of input.  Input files are either
   * relative or absolute.
   * @param fields Array of parts of the input line
   * @param index Field index within 'fields'
   *
   * @return The parsed input file, guaranteed to exist.
   * @throws FileNotFoundException if the parsed file does not exist
   */
   private File parseFile(String[] fields, int index) throws FileNotFoundException {
    String fileName = fields[index].trim();
    File file = new File(fileName);
    return file;
  }

  /**
   * Parse the alignment field of an input line.  If absent, defaults to AUTO
   * alignment.
   * @param fields Array of parts of the input line
   * @param index Field index within 'fields'
   * @return The resulting PageAlign value
   */
  private PageAlign parseAlign(String[] fields, int index) {
    if (fields.length > index) {
      return PageAlign.valueOf(fields[index].trim().toUpperCase());
    }
    return PageAlign.AUTO;
  }

  /**
   * Parse a line of the input, mutating the instance fields.
   *
   * line ::= # comment | filename [ ; alignment ]
   * @param line The input line to parse
   *
   * @throws IOException
   */
  private void parseLine(String line) throws IOException {
    if (line.trim().length() == 0 || line.trim().charAt(0) == '#') {
      return;
    }
    String[] fields = line.trim().split("\\s*;\\s*");
    // Ignore empty lines
    if (fields.length == 0) {
      return;
    }
    project.appendInputFile(new InputFile(parseFile(fields, 0), parseAlign(fields, 1)));
  }

  @Override
  public ProjectFile parse() throws IOException {
    project.setProjectFile(input);
    try (BufferedReader rd = new BufferedReader(new FileReader(input))) {
      for (String line = rd.readLine(); line != null; line = rd.readLine()) {
        parseLine(line);
      }
    }
    return project;
  }

}
