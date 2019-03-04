package robingarner.pdfstitcher;

import java.io.File;
import java.util.function.BiPredicate;

/**
 * Representation of an input file, parsed from the project file.
 *
 * Includes Jaackson annotations so that we can parse it directly.
 */
public class InputFile {

  /**
   * Various alignment options that can be applied to an input file.
   */
  public enum PageAlign {
    ODD((n, p) -> !isOdd(n)),
    EVEN((n, p) -> isOdd(n)),
    AUTO((n, p) -> !isOdd(p) ? isOdd(n) : false),
    NONE((n, p) -> false);

    private PageAlign(BiPredicate<Integer, Integer> pred) {
      this.pred = pred;
    }

    private final BiPredicate<Integer, Integer> pred;

    /**
     * Do we need to insert an alignment page, given that the next page in
     * the output is 'current' and the document to be merged is 'pages' pages long ?
     * @param current Current page number
     * @param length Document length (in pages)
     * @return {@code true} if an alignment page is required.
     */
    public boolean isRequired(int current, int length) {
      return pred.test(current, length);
    }
  };

  /** The input file itself. */
  private File file;

  /** The alignment requirements for this file */
  private PageAlign align = PageAlign.AUTO;

  private PageRange range = new PageRange("1-");

  public InputFile() {

  }

  /**
   * Public constructor.
   * @param file The input file
   * @param align Alignment requirements
   */
  public InputFile(File file, PageAlign align) {
    this.file = file;
    this.align = align;
  }

  /**
   * @return The input file
   */
  public File getFile() {
    return file;
  }

  /** @return the alignment */
  public PageAlign getAlign() {
    return align;
  }

  /**
   * @param n The number to test
   * @return {@code true} if n is odd
   */
  static final boolean isOdd(int n) {
    return (n & 1) == 1;
  }

  public PageRange getRange() {
    return range;
  }

  public InputFile setRange(PageRange range) {
    this.range = range;
    return this;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((align == null) ? 0 : align.hashCode());
    result = prime * result + ((file == null) ? 0 : file.hashCode());
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
    InputFile other = (InputFile) obj;
    if (align != other.align)
      return false;
    if (file == null) {
      if (other.file != null)
        return false;
    } else if (!file.equals(other.file))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "InputFile [file=" + file + ", align=" + align + "]";
  }


}
