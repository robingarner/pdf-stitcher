package robingarner.pdfstitcher;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProjectFile {

  private File projectFile;

  private File baseDir = null;

  private List<InputFile> inputs = new ArrayList<>();

  public ProjectFile setInputs(List<InputFile> inputFiles) {
    this.inputs = inputFiles;
    return this;
  }

  public List<InputFile> getInputs() {
    return inputs;
  }

  public void appendInputFile(InputFile file) {
    inputs.add(file);
  }

  public File getBaseDir() {
    return baseDir;
  }

  public ProjectFile setBaseDir(File baseDir) {
    this.baseDir = baseDir;
    return this;
  }

  public File getProjectFile() {
    return projectFile;
  }

  public ProjectFile setProjectFile(File projectFile) {
    this.projectFile = projectFile;
    if (baseDir == null) {
      this.baseDir = projectFile == null ? new File(".") : projectFile.getAbsoluteFile().getParentFile();
    }
    return this;
  }
}
