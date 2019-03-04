package robingarner.pdfstitcher;

import java.io.File;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

public class CommandLine {

  @Argument(metaVar="project-file", index=0, required=true)
  private String projectFile;

  @Argument(metaVar="output-file", index=1)
  private String outFile;

  @Option(name="--verbose", aliases="-v", usage="Print progress information")
  private boolean verbose = false;

  public File getProjectFile() {
    return new File(projectFile);
  }

  public File getOutFile() {
    String baseName = projectFile.endsWith(".json") ?
        projectFile.substring(0, projectFile.length()-5) :
          projectFile;
    return new File(outFile == null ? baseName + ".pdf" : outFile);
  }

  public boolean isVerbose() {
    return verbose;
  }


}
