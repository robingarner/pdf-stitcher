package robingarner.pdfstitcher;

import java.io.File;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Representation of an input file, parsed from the project file.
 *
 * Includes Jackson annotations so that we can parse it directly.
 */
public class InputFile {

  /** The input file itself. */
  private File file;

  /** The alignment requirements for this file */
  private PageAlign align = PageAlign.AUTO;

  /** Range of pages from the source */
  private PageRange range = PageRange.ALL;

  /** Include or exclude this file altogether. */
  @JsonProperty("include")
  private boolean include = true;

  @JsonProperty("toc")
  private String tocEntry = null;

  public InputFile() {

  }

  /**
   * Public constructor.
   * @param file The input file
   */
  public InputFile(File file) {
    this.file = file;
  }

  /**
   * Public constructor.
   * @param file The input file
   * @param align Alignment requirements
   * @deprecated Use the one-arg constructor and the fluent setter
   */
  @Deprecated
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

  public InputFile align(PageAlign align) {
    this.align = align;
    return this;
  }

  public InputFile setToc(String value) {
    this.tocEntry = value;
    return this;
  }

  public String getToc() {
    return tocEntry == null ? file.getName() : tocEntry;
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

  public InputFile range(PageRange range) {
    this.range = range;
    return this;
  }

  public boolean isIncluded() {
    return include;
  }

  public InputFile include(boolean include) {
    this.include = include;
    return this;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((align == null) ? 0 : align.hashCode());
    result = prime * result + ((file == null) ? 0 : file.hashCode());
    result = prime * result + (include ? 1231 : 1237);
    result = prime * result + ((range == null) ? 0 : range.hashCode());
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
    if (include != other.include)
      return false;
    if (range == null) {
      if (other.range != null)
        return false;
    } else if (!range.equals(other.range))
      return false;
    if (tocEntry == null) {
      if (other.tocEntry != null)
        return false;
    } else if (!tocEntry.equals(other.tocEntry))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "InputFile [file=" + file + ", align=" + align + "]";
  }


}
