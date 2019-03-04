package robingarner.pdfstitcher;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import robingarner.pdfstitcher.InputFile.PageAlign;

/**
 * Container for the mutable state required to stitch together the
 * input documentation.
 *
 * Intended interface:
 *   Stitcher s = new Stitcher(cmdline);
 *   stitcher.concatenate();
 *   stitcher.save();
 *   stitcher.close();
 */
public class Stitcher implements Closeable {

  private final CommandLine cmdline;

  /**
   * We save the input documents as we add them, and close them all after writing
   * the output file.  Their resources are disposed
   * of when they are closed, so if we don't do this we end up with a blank output.
   */
  private final List<PDDocument> inDocs = new ArrayList<>();

  private int pageNo = 1;

  private final PDDocument outDoc = new PDDocument();

  public Stitcher(CommandLine cmdline) {
    this.cmdline = cmdline;
  }

  public Stitcher concatenate() throws IOException {
    ProjectFile project = getParser().parse();
    for (InputFile inputFile : project.getInputs()) {
      append(project, inputFile);
    }
    return this;
  }

  ProjectFileParser getParser() throws FileNotFoundException, IOException {
    File projectFile = cmdline.getProjectFile();
    if (projectFile.getName().toLowerCase().endsWith(".json")) {
      return new JSONParser(projectFile);
    }
    return new SimpleParser(projectFile);
  }

  void append(ProjectFile project, InputFile inputFile) throws IOException {
    File file = inputFile.getFile();
    if (!file.isAbsolute()) {
      file = new File(project.getBaseDir(), file.getPath()).getCanonicalFile();
    }
    if (!file.exists()) {
      throw new FileNotFoundException(file.getAbsolutePath()+" does not exist");
    }


    PDDocument inDoc = PDDocument.load(file);
    inDocs.add(inDoc);
    addAlignmentPages(inputFile.getAlign(), pages(inputFile, inDoc));
    message("Adding %s at page #%d", inputFile.getFile().getCanonicalPath(), pageNo);

    int docPage = 1;
    for (PDPage page : inDoc.getPages()) {
      if (inputFile.getRange().test(docPage)) {
        outDoc.addPage(page);
        pageNo++;
      }
      docPage++;
    }
  }

  int pages(InputFile input, PDDocument doc) {
    return input.getRange().pages(doc.getNumberOfPages());
  }

  void addAlignmentPages(PageAlign align, int nextDocLength) {
    while (align.isRequired(pageNo, nextDocLength)) {
      message("Adding blank alignment page #%d", pageNo);
      outDoc.addPage(new PDPage());
      pageNo++;
    }
  }

  public Stitcher save() throws IOException {
    message("Writing  %s", cmdline.getOutFile().getCanonicalPath());
    outDoc.save(cmdline.getOutFile());
    return this;
  }

  @Override
  public void close() throws IOException {
    outDoc.close();
    inDocs.stream().forEach(d -> { try { d.close(); } catch (IOException e) {} });
  }

  private void message(String format, Object...args) {
    if (cmdline.isVerbose()) System.out.printf(format+"%n", args);
  }

}
