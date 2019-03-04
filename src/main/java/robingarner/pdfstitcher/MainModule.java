package robingarner.pdfstitcher;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionHandlerFilter;

import com.google.inject.AbstractModule;

/**
 * Guice module for the PdfStitcher program
 */
public class MainModule extends AbstractModule {

  private final CommandLine cmdline;

  public MainModule(Class<?> mainClass, String[] args) {
    cmdline = new CommandLine();
    CmdLineParser parser = new CmdLineParser(cmdline);
    try {
      parser.parseArgument(args);
    } catch (CmdLineException e) {
      System.out.println(e.getMessage());
      printUsage(mainClass, parser);
      System.exit(1);
    }
  }


  @Override
  protected void configure() {
    bind(CommandLine.class).toInstance(cmdline);
    bind(PdfStitcher.class);
  }


  private static void printUsage(Class<?> mainClass, CmdLineParser parser) {
    System.out.printf("Usage: java %s %s%n", mainClass.getCanonicalName(),
        parser.printExample(OptionHandlerFilter.ALL, null));
    parser.printUsage(System.out);
    System.exit(1);
  }

}
