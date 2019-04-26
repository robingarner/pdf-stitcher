package robingarner.pdfstitcher;

import java.io.IOException;

import javax.inject.Inject;

/**
 * Do the main work of the PdfStitcher.
 *
 * Requires field injection by the IOC container
 */
public class PdfStitcher implements Runnable {

  @Inject private CommandLine cmdline;

  @Override
  public void run() {
    try (Stitcher stitcher = new Stitcher(cmdline)) {
      stitcher.build().save();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
