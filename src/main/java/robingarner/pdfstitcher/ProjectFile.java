package robingarner.pdfstitcher;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProjectFile {

  private File projectFile;

  private File baseDir = null;

  private List<InputFile> inputs = new ArrayList<>();

  private boolean toc = false;

  public ProjectFile() {

  }

  public ProjectFile(InputFile inputs) {
    this.inputs = asList(inputs);
  }

  public ProjectFile(List<InputFile> inputs) {
    this.inputs = inputs;
  }

  public ProjectFile setToc(boolean value) {
    this.toc = value;
    return this;
  }

  public boolean getToc() {
    return toc;
  }

  public ProjectFile setInputs(List<InputFile> inputFiles) {
    this.inputs = inputFiles;
    return this;
  }

  public List<InputFile> getInputs() {
    return inputs;
  }

  public List<InputFile> getVisibleInputs() {
    return inputs.stream().filter(InputFile::isIncluded).collect(toList());
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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((inputs == null) ? 0 : inputs.hashCode());
    result = prime * result + (toc ? 1231 : 1237);
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ProjectFile other = (ProjectFile) obj;
    if (inputs == null) {
      if (other.inputs != null)
        return false;
    } else if (!inputs.equals(other.inputs))
      return false;
    if (toc != other.toc)
      return false;
    return true;
  }

}
