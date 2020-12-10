package robingarner.pdfstitcher;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.spi.OptionHandler;
import org.kohsuke.args4j.spi.Parameters;
import org.kohsuke.args4j.spi.Setter;

/**
 * Boolean {@link OptionHandler} that allows values to be specified as
 * --<option> and negated with --no<option>.
 *
 * Very crude
 * <ul><li>you must explicitly add the --no version to the alternative
 * names in the option definition.
 * <li>Options whose natural names start with "no" will be treated as though
 *   they are false if specified, but can be negated (made true) with "--nono..."
 * <ul>
 */
public class NegatableBooleanOptionHandler extends OptionHandler<Boolean> {

    public NegatableBooleanOptionHandler(CmdLineParser parser,
            OptionDef option, Setter<? super Boolean> setter) {
        super(parser, option, setter);
    }

    @Override
    public int parseArguments(Parameters params) throws CmdLineException {
      String baseName = params.getParameter(-1).replaceFirst("-*", "");
      if (baseName.startsWith("no") && !baseName.startsWith("nono")) {
        setter.addValue(false);
      } else {
        setter.addValue(true);
      }
      return 0;
    }

    @Override
    public String getDefaultMetaVariable() {
        return null;
    }
}
